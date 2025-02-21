package com.employee.management.repo;

import com.employee.management.entity.Address;
import com.employee.management.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface AddressRepo extends JpaRepository<Address, Long> {

    String GET_ADDRESS_FROM_EMPLOYEE_ID = "SELECT * FROM addresses WHERE employee_id = :employee_id";

    String UPDATE_ADDRESS_FROM_EMPLOYEE_ID = "UPDATE addresses " +
            "SET address_line = :addressLine, " +  // Column names should match DB column names
            "city = :city, " +
            "state = :state, " +
            "country_id = :country, " +
            "zip_code = :zipCode, " +
            "is_current = TRUE " +
            "WHERE employee_id = :employee_id AND is_current = TRUE";

    @Query(value = GET_ADDRESS_FROM_EMPLOYEE_ID, nativeQuery = true)
    List<Address> findByEmployeeId(@Param("employee_id") Long employeeId);

    @Modifying
    @Transactional
    @Query(value = UPDATE_ADDRESS_FROM_EMPLOYEE_ID, nativeQuery = true)
    int updateAddressFromEmployeeId(@Param("addressLine") String addressLine,
                                    @Param("city") String city,
                                    @Param("state") String state,
                                    @Param("country") Country country,
                                    @Param("zipCode") String zipCode,
                                    @Param("employee_id") Long employeeId);

    @Modifying
    @Transactional
    @Query("UPDATE Address a SET a.isCurrent = false WHERE a.employee.id = :employeeId AND a.isCurrent = true")
    void unsetPreviousCurrentAddress(@Param("employeeId") Long employeeId);
}
