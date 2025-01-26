package com.api.EcomTracker.domain.address;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
@NoArgsConstructor
public class AddressData {
    @NotBlank(message = "Street is required")
    private String street;

    @NotBlank(message = "Number is required")
    private String number;

    @NotBlank(message = "Neighborhood is required")
    private String neighborhood;

    @NotBlank(message = "City is required")
    private String city;

    @NotBlank(message = "State is required")
    private String state;

    @NotBlank(message = "Zipcode is required")
    private String zipcode;

    @NotBlank(message = "Complement is required")
    private String complement;
}