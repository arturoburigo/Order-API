package com.api.EcomTracker.controller;

import com.api.EcomTracker.domain.address.AddressData;
import com.api.EcomTracker.domain.address.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/address")
public class AddressController {
   @Autowired
   private AddressService addressService;

   @PostMapping("/register")
   public ResponseEntity<?> registerAddress(@RequestBody AddressData addressData) {
      return addressService.registerAddress(addressData);
   }
}
