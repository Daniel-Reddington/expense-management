package com.daniela.expensemanagement.entities;

import com.daniela.expensemanagement.enumeration.Currency;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long loanId;
    private String borrowerName;
    private Double totalAmount;
    @Enumerated(EnumType.STRING)
    private Currency currency;
    private LocalDateTime borrowDate;
    private LocalDate previewDate;
    private Boolean isReturn;
    private LocalDate returnDate;
    @ManyToOne
    @ToString.Exclude
    private UserAccount userAccount;
    @ManyToOne(fetch = FetchType.EAGER)
    private Income income;
}
