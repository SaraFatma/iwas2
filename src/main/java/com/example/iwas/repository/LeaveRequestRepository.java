package com.example.iwas.repository;

import com.example.iwas.model.LeaveRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Integer> {
    
    List<LeaveRequest> findByEmployeeEmpId(Integer empId); // ✅ Matches field name

    List<LeaveRequest> findByStatus(LeaveRequest.LeaveStatus status);

    //long countByStatus(String status);
    long countByStatus(LeaveRequest.LeaveStatus status);
}
