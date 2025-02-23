package com.employee.management.services;

import com.employee.management.entity.Employee;
import com.employee.management.repo.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ScheduleService {
    @Autowired
    private EmailService emailService;
    @Autowired
    private EmployeeRepo employeeRepo;

    public void birthdayWishMail(){
        //select name email from employees table and call email service for all the list of employees.
        List<Employee> employeeList = employeeRepo.getByDateOfBirth(new Date());

    }

}
