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
public class CreateEmployeeRequestDto {

    private String name;
    private String email;
    private Date dateOfBirth;
    private Long managerId;
    private Long countryId;
    private List <AddressDto> addresses;
    private List<Long> departmentIds;

}
