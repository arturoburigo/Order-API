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

            Products product = productsRepository.findById(orderRequest.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            if (product.getAvailableStock() < orderRequest.getQuantity()) {
                return ResponseEntity.badRequest()
                        .body(new ErrorResponse("Insufficient stock",
                                String.format("Available quantity: %d", product.getQuantity())));
            }

            BigDecimal totalPrice = product.getPrice().multiply(BigDecimal.valueOf(orderRequest.getQuantity()));

            product.reserveStock(orderRequest.getQuantity());
            productsRepository.save(product);

            Reservations reservation = new Reservations(
                    product,
                    user,
                    orderRequest.getQuantity(),
                    ReservationStatus.RESERVED
            );
            reservationRepository.save(reservation);

            OrderDTO order = new OrderDTO(
                    UUID.randomUUID().toString(),
                    product,
                    user,
                    totalPrice,
                    orderRequest.getQuantity(),
                    OrderStatusDTO.PENDING
            );

            String messageBody = String.format(
                    "Order ID: %s, Product ID: %d, User ID: %d, Total Price: %s, Quantity: %d, Status: %s, Created At: %s",
                    order.getId(),
                    order.getProductId(),
                    order.getUserId(),
                    order.getAmount(),
                    order.getQuantity(),
                    order.getStatus(),
                    order.getCreatedAt()
            );
            sqsMessageSender.sendMessage(messageBody);

            return ResponseEntity.ok(order);

        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse("Order creation failed", e.getMessage()));
        }
    }
}