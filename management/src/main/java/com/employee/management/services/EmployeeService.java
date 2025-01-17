package com.employee.management.services;

import com.employee.management.entity.Address;
import com.employee.management.entity.Employee;
import com.employee.management.repo.EmployeeRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class EmployeeService {

    private EmployeeRepo employeeRepo;

    public Optional<Employee> getbyId(Long id) {
        return employeeRepo.findById(id);
    }

    public boolean updateEmp(Long id, Employee employee) {
        Optional<Employee> existingEmployee = employeeRepo.findById(id);
        if (existingEmployee.isPresent()) {
            Employee emp = existingEmployee.get();
            //update the employee;
            emp.setName(employee.getName());
            emp.setEmail(employee.getEmail());

            employeeRepo.save(emp);
            return true;
        }
        return false;
    }

    public List<Employee> getAllEmp() {
        return employeeRepo.findAll();
    }

    public void deleteEmployeeById(Long id) throws RuntimeException {
        if (employeeRepo.existsById(id)) {
            employeeRepo.deleteById(id);
        } else {
            throw new RuntimeException("Employee not found with id: " + id);
        }
    }

    public void insertEmployee(Employee employee, List<Address> addressList){
        //first we are setting the list of address
        for(Address address : addressList) {
            employee.addAddress(address);
        }
        //then we are saving the address into employee
        employeeRepo.save(employee);
    }
}
