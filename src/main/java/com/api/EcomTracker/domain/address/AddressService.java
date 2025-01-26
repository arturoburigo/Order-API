package com.api.EcomTracker.domain.address;

import com.api.EcomTracker.domain.users.Users;
import com.api.EcomTracker.errors.ErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
public class AddressService {
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private AddressRepository addressRepository;

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> registerAddress(AddressData addressData) {
        try {
            // Obtain authenticated user
            Users user = (Users) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (user == null) {
                return ResponseEntity.badRequest().body(new ErrorResponse("User not authenticated", null));
            }
            user = entityManager.merge(user);
            // Check if address already exists
            if (addressRepository.existsById(user.getId())) {
                return ResponseEntity.badRequest().body(new ErrorResponse("Address already exists", null));
            }

            // Create and save address
            Address address = new Address(addressData, user);
            addressRepository.save(address);

            return ResponseEntity.ok("Address registered successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest()
                 .body(new ErrorResponse("Address Register failed", e.getMessage()));
        }
    }

    @Transactional(readOnly = true)
    public ResponseEntity<?> getAddress() {
        try {
            Users user = (Users) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (user == null) {
                return ResponseEntity.badRequest().body(new ErrorResponse("User not authenticated", null));
            }

            Address address = addressRepository.findById(user.getId())
                 .orElseThrow(() -> new IllegalArgumentException("Address not found for the user"));

            return ResponseEntity.ok(address);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                 .body(new ErrorResponse("Failed to retrieve address", e.getMessage()));
        }
    }
}