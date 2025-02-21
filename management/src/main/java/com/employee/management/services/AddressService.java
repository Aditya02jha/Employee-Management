package com.employee.management.services;

import com.employee.management.entity.Address;
import com.employee.management.repo.AddressRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {
    @Autowired
    private AddressRepo addressRepo;

    //create

    //Read
    public List<Address> getAllAddressByEmployeeId(Long employee_id){
        return addressRepo.findByEmployeeId(employee_id);
    }
    //update
    @Transactional
    public String UpdateCurrentAddress(Address address , Long employee_id) throws RuntimeException{
        if(addressRepo.findByEmployeeId(employee_id).isEmpty()){
            throw new RuntimeException("No Current Address found for this Employee Id");
        }
        else{
            //find the current address and update the changes.
             int is_updated =  addressRepo.updateAddressFromEmployeeId(address.getAddressLine(),address.getCity(),address.getState(),address.getCountry(),address.getZipCode(),employee_id);
            return "Employee with id "+employee_id+" Updated Successfully";
        }
    }
    //delete

}
