package com.employee.management.controllers;


import com.employee.management.dto.CreateEmployeeRequestDto;
import com.employee.management.dto.CreateEmployeeResponseDto;
import com.employee.management.services.AdminService;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api/admin")
public class AdminController {

    private static Logger LOGGER = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    private AdminService adminService;

    @PostMapping("/addEmployee")
    public ResponseEntity<CreateEmployeeResponseDto> createEmployee(@RequestBody CreateEmployeeRequestDto createEmployeeRequestDto){
        CreateEmployeeResponseDto createEmployeeResponseDto = adminService.createEmployee(createEmployeeRequestDto);
        return ResponseEntity.ok(createEmployeeResponseDto);
    }
    //translator request. -- > translates api req -->create dto -->
    //crete emp req  to --> create emp dto
//    response dto <-- (to) crete emp dto

    //use mapper instead of set.

}
