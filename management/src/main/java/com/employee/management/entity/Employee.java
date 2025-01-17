package com.employee.management.entity;

import com.employee.management.repo.AddressRepo;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private Date date_of_birth;

    @ManyToOne
    @JoinColumn(name = "manager_id", nullable = true)
    private Employee manager;

    @ManyToOne
    @JoinColumn(name = "country_id", nullable = false)
    private Country country;
    // One employee can have many addresses

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Address> addressList = new ArrayList<>();

    @OneToMany(mappedBy = "manager")
    private List<Employee> subordinates = new ArrayList<>();

    @OneToMany(mappedBy = "employee",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<ManagerHistory> managerHistoryList = new ArrayList<>();

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<EmployeeDepartment> employeeDepartmentList = new ArrayList<>();

    public void addAddress(Address address){
        addressList.add(address);
        address.setEmployee(this);
    }
    public void deleteAddress(Address address){
        addressList.remove(address);
        address.setEmployee(null);
    }
}
