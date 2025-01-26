package com.api.EcomTracker.domain.order;

import com.api.EcomTracker.domain.address.Address;
import com.api.EcomTracker.domain.users.Users;
import lombok.Getter;

@Getter
public class UserInfoDTO {
   private final Long id;
   private final String name;
   private final String email;
   private final String role;
   private final Address address;

   public UserInfoDTO(Users user, Address address){
      this.id = user.getId();
      this.name = user.getUsername();
      this.email = user.getEmail();
      this.role = user.getRole().getName().name();
      this.address = address;
   }
}
