package com.employee.management.services;


import com.employee.management.dto.AddressDto;
import com.employee.management.dto.CreateEmployeeRequestDto;
import com.employee.management.dto.CreateEmployeeResponseDto;
import com.employee.management.dto.ResponseAddressDto;
import com.employee.management.entity.*;
import com.employee.management.repo.AddressRepo;
import com.employee.management.repo.CountryRepo;
import com.employee.management.repo.DepartmentRepo;
import com.employee.management.repo.EmployeeRepo;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.Null;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdminService {

    @Autowired
    private EmployeeRepo employeeRepo;

    @Autowired
    private CountryRepo countryRepo;

    @Autowired
    private AddressRepo addressRepo;

    @Autowired
    private DepartmentRepo departmentRepo;

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminService.class);
    @Transactional
    public CreateEmployeeResponseDto createEmployee(CreateEmployeeRequestDto request) {
        Employee employee = new Employee();
        employee.setName(request.getName());
        employee.setEmail(request.getEmail());
        employee.setDate_of_birth(request.getDateOfBirth());

        //set Manager
        if (request.getManagerId() != null) {
            Optional<Employee> manager = employeeRepo.findById(request.getManagerId());
//            manager.ifPresent(employee1 -> employee.setManager(employee1));
            manager.ifPresent(employee::setManager);
        }
        //set country
        Country country = countryRepo.findById(request.getCountryId())
                .orElseThrow(() -> new RuntimeException("Country not found"));

        employee.setCountry(country);
        //convert req to address

        List<Address> addresses = request.getAddresses().stream().map(addressDto -> {
            Address address = new Address();
            Country empAddress = countryRepo.findById(addressDto.getCountryId())
                    .orElseThrow(() -> new RuntimeException("Country not found"));
            address.setAddressLine(addressDto.getAddressLine());
            address.setState(addressDto.getState());
            address.setCity(addressDto.getCity());
            address.setZipCode(addressDto.getZipCode());
            address.setCountry(empAddress);
            address.setIsCurrent(addressDto.getIsCurrent());
            address.setEmployee(employee);
            return address;
        }).collect(Collectors.toList());
        employee.setAddressList(addresses);

        //Convert and set Departments
        List<EmployeeDepartment> employeeDepartments = request.getDepartmentIds().stream().map(departmentId ->
        {
            Department department = departmentRepo.findById(departmentId)
                    .orElseThrow(() -> new RuntimeException("Department Does Not exist"));
            return new EmployeeDepartment(null, employee, department, true);
        }).collect(Collectors.toList());
        employee.setEmployeeDepartmentList(employeeDepartments);

        //save employee
        Employee savedEmployee = employeeRepo.save(employee);
        return mapToResponseDto(savedEmployee);
    }

    private CreateEmployeeResponseDto mapToResponseDto(Employee employee) {
        return new CreateEmployeeResponseDto(
                employee.getId(),
                employee.getName(),
                employee.getEmail(),
                employee.getDate_of_birth(),
                employee.getManager() != null ? employee.getManager().getName() : null,
                employee.getCountry().getName(),
                employee.getEmployeeDepartmentList().stream()
                        .map(empDept -> empDept.getDepartment().getName())
                        .collect(Collectors.toList()),
                employee.getAddressList().stream()
                        .map(address -> new ResponseAddressDto(
                                address.getAddressLine(),
                                address.getCity(),
                                address.getState(),
                                address.getCountry().getName(),
                                address.getZipCode(),
                                address.getIsCurrent()))
                        .collect(Collectors.toList())
        );
    }

}
