package com.employee.management.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateEmployeeResponseDto {
    private Long id;
    private String name;
    private String email;
    private Date dateOfBirth;
    private String managerName;
    private String countryName;
    private List<String> departmentNames;
    private List<AddressDto> addresses;
}
