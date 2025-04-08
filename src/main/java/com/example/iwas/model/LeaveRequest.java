package com.example.iwas.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Entity
@Getter
@Setter
public class LeaveRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer leaveId;

    @ManyToOne
    @JoinColumn(name = "emp_id", nullable = false) // 🔹 Corrected column name
    private Employee employee;

    private LocalDate startDate;
    private LocalDate endDate;
    
    @Column(length = 500)
    private String reason;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LeaveStatus status = LeaveStatus.PENDING; // ✅ Ensuring valid default

    public enum LeaveStatus {
        PENDING, APPROVED, REJECTED
    }
}
