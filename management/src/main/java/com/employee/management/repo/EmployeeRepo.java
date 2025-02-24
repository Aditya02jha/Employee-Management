package com.employee.management.repo;

import com.employee.management.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee, Long> {

    String GET_EMPLOYEE_BY_DOB = "SELECT * FROM employees WHERE date_of_birth = :date_of_birth";

    @Query(value = GET_EMPLOYEE_BY_DOB, nativeQuery = true)
    List<Employee> getByDateOfBirth(@Param("date_of_birth") Date date);
}
