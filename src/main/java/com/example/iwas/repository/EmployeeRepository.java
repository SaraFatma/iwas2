package com.example.iwas.repository;

import com.example.iwas.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
   Optional<Employee> findByEmpEmail(String empEmail);

    //12
    @Modifying
    @Query("UPDATE Employee e SET e.empAvailability = :availability WHERE e.empId = :empId")
    void updateAvailability(@Param("empId") int empId, @Param("availability") boolean availability);

    long count();
}
