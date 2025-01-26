package com.api.EcomTracker.domain.address;

import com.api.EcomTracker.domain.users.Users;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Table(name = "address")
@Entity(name = "address")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Address {
  @Id
  @Column(name = "user_id")
  private Long id;

  @OneToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH})
  @MapsId
  @JoinColumn(name = "user_id")
  @JsonIgnore
  private Users user;

  @Column(nullable = true)
  private String street;

  @Column(nullable = false)
  private String number;

  @Column(nullable = true)
  private String neighborhood;

  @Column(nullable = true)
  private String city;

  @Column(nullable = true)
  private String state;

  @Column(nullable = true)
  private String zipcode;

  @Column(nullable = false)
  private String complement;

  public Address(AddressData addressData, Users user) {
    this.user = user;
    this.street = addressData.getStreet();
    this.number = addressData.getNumber();
    this.neighborhood = addressData.getNeighborhood();
    this.city = addressData.getCity();
    this.state = addressData.getState();
    this.zipcode = addressData.getZipcode();
    this.complement = addressData.getComplement();
  }
}