package com.daniela.expensemanagement.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long borrowId;
    private String borrowerName;
    private Double totalAmount;
    private LocalDateTime borrowDate;
    private LocalDateTime returnDate;
    @ManyToOne
    @ToString.Exclude
    private UserAccount userAccount;
}
