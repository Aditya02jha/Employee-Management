package com.employee.management.controllers;

import com.employee.management.entity.Employee;
import com.employee.management.services.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController

@RequestMapping("/api/employee")
@AllArgsConstructor
public class EmployeeController {


    private EmployeeService employeeService;

    @PutMapping("/{id}")
    public ResponseEntity<Boolean> updateEmp(@PathVariable Long id , @RequestBody Employee employee){
        Boolean is_updated = employeeService.updateEmp(id , employee);
        return ResponseEntity.ok(is_updated);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Employee> > getEmpById(@PathVariable Long id) {
        Optional<Employee> employee = employeeService.getbyId(id);
        return ResponseEntity.ok(employee);
    }

    @GetMapping("/employees")
    public ResponseEntity<List<Employee>> getAllEmpList(){
        List<Employee> employeeList = employeeService.getAllEmp();
        return ResponseEntity.ok(employeeList);
    }


    @PostMapping("/")
    public String insertEmployee(@RequestBody Employee employee){
        try {
            employeeService.insertEmployee(employee, employee.getAddressList());
            return ("Employee Added Successfully! ");
        }catch (RuntimeException e){
            return e.getMessage();
        }
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

}
