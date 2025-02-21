package com.employee.management.controllers;

import com.employee.management.entity.Address;
import com.employee.management.entity.Employee;
import com.employee.management.services.AddressService;
import com.employee.management.services.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController

@RequestMapping("/api/employee")
@AllArgsConstructor
public class EmployeeController {


    private EmployeeService employeeService;
    private AddressService addressService;

    @PutMapping("/{id}")
    public ResponseEntity<Boolean> updateEmp(@PathVariable Long id , @RequestBody Employee employee){
        Boolean is_updated = employeeService.updateEmp(id , employee);
        return ResponseEntity.ok(is_updated);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmpById(@PathVariable Long id) {
        Employee employee = employeeService.getbyId(id);
        return ResponseEntity.ok(employee);
    }

    @GetMapping("/employees")
    public ResponseEntity<List<Employee>> getAllEmpList(){
        List<Employee> employeeList = employeeService.getAllEmp();
        return ResponseEntity.ok(employeeList);
    }

    @DeleteMapping("/{id}")
    public String deleteEmployeeById(@PathVariable Long id){
        try{
            employeeService.deleteEmployeeById(id);
            return ("Employee with id "+id+" deleted successfully. ");
        }catch (RuntimeException e){
            return e.getMessage();
        }
    }

    @GetMapping("/{id}/address")
    public ResponseEntity<List<Address>> getAllAddressByEmployeeId(@PathVariable Long id){
        List<Address> AddressList = addressService.getAllAddressByEmployeeId(id);
        return ResponseEntity.ok(AddressList);
    }


}
