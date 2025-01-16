package com.api.EcomTracker.domain.order;

import com.api.EcomTracker.config.SqsMessageSender;
import com.api.EcomTracker.domain.products.Products;
import com.api.EcomTracker.domain.products.ProductsRepository;
import com.api.EcomTracker.domain.reservations.ReservationRepository;
import com.api.EcomTracker.domain.reservations.ReservationStatus;
import com.api.EcomTracker.domain.reservations.Reservations;
import com.api.EcomTracker.domain.users.Users;
import com.api.EcomTracker.errors.ErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {

    @Autowired
    private ProductsRepository productsRepository;

    @Autowired
    private SqsMessageSender sqsMessageSender;

    @Autowired
    private ReservationRepository reservationRepository;

    @Transactional
    public ResponseEntity<?> createOrder(OrderRequestDTO orderRequest) {
        try {
            Users user = (Users) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            List<ProductResponseDTO> productResponseList = new ArrayList<>();
            BigDecimal totalPrice = BigDecimal.ZERO;
            int totalQuantity = 0;

            // Processa cada produto no pedido
            for (ProductOrderDTO productOrderDTO : orderRequest.getProducts()) {
                Products product = productsRepository.findById(productOrderDTO.getProductId())
                        .orElseThrow(() -> new RuntimeException("Product not found"));

                // Verifica se há estoque suficiente
                if (product.getAvailableStock() < productOrderDTO.getQuantity()) {
                    return ResponseEntity.badRequest()
                            .body(new ErrorResponse("Insufficient stock",
                                    String.format("Available quantity for product %d: %d",
                                            product.getId(), product.getAvailableStock())));
                }

                // Atualiza o total do pedido e a quantidade
                BigDecimal productTotal = product.getPrice().multiply(BigDecimal.valueOf(productOrderDTO.getQuantity()));
                totalPrice = totalPrice.add(productTotal);
                totalQuantity += productOrderDTO.getQuantity();

                // Reserva o estoque
                product.reserveStock(productOrderDTO.getQuantity());
                productsRepository.save(product);

                // Cria a reserva
                Reservations reservation = new Reservations(
                        product,
                        user,
                        productOrderDTO.getQuantity(),
                        ReservationStatus.RESERVED
                );
                reservationRepository.save(reservation);

                // Adiciona o produto à lista de resposta
                productResponseList.add(new ProductResponseDTO(
                        product.getId(),
                        product.getName(),
                        product.getPrice()
                ));
            }

            // Cria o DTO de resposta
            OrderResponseDTO orderResponse = new OrderResponseDTO();
            orderResponse.setId(UUID.randomUUID().toString());
            orderResponse.setProducts(productResponseList);
            orderResponse.setUserId(user.getId());
            orderResponse.setAmount(totalPrice);
            orderResponse.setQuantity(totalQuantity);
            orderResponse.setStatus(OrderStatusDTO.PENDING);
            orderResponse.setCreatedAt(LocalDateTime.now());

            // Formata o messageBody com os detalhes do pedido
            StringBuilder messageBody = new StringBuilder();
            messageBody.append("{\n")
                    .append("  \"id\": \"").append(orderResponse.getId()).append("\",\n")
                    .append("  \"products\": [\n");

            // Itera sobre os produtos e formata a lista
            for (int i = 0; i < productResponseList.size(); i++) {
                ProductResponseDTO productResponse = productResponseList.get(i);
                messageBody.append("    {\n")
                        .append("      \"productId\": \"").append(productResponse.getProductId()).append("\",\n")
                        .append("      \"product_name\": \"").append(productResponse.getProductName()).append("\",\n")
                        .append("      \"value_each\": ").append(productResponse.getValueEach()).append("\n");

                if (i < productResponseList.size() - 1) {
                    messageBody.append("    },\n");
                } else {
                    messageBody.append("    }\n");
                }
            }

            messageBody.append("  ],\n")
                    .append("  \"userId\": \"").append(orderResponse.getUserId()).append("\",\n")
                    .append("  \"amount\": ").append(orderResponse.getAmount()).append(",\n")
                    .append("  \"quantity\": ").append(orderResponse.getQuantity()).append(",\n")
                    .append("  \"status\": \"").append(orderResponse.getStatus()).append("\",\n")
                    .append("  \"createdAt\": \"").append(orderResponse.getCreatedAt()).append("\"\n")
                    .append("}");

            // Envia a mensagem para o SQS
            sqsMessageSender.sendMessage(messageBody.toString());

            // Retorna a resposta com o pedido
            return ResponseEntity.ok(orderResponse);

        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse("Order creation failed", e.getMessage()));
        }
    }
}
