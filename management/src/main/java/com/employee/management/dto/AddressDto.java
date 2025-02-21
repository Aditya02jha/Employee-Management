package com.employee.management.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddressDto {
    private String addressLine;
    private String city;
    private String state;
    private String country;
    private String zipCode;
    private Boolean isCurrent;
}
