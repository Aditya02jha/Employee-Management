package com.employee.management.dto;

import com.employee.management.entity.Country;
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
    private Long countryId;
    private String zipCode;
    private Boolean isCurrent;
}
