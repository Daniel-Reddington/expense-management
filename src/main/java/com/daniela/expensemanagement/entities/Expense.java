package com.daniela.expensemanagement.entities;

import com.daniela.expensemanagement.enumeration.Currency;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long expenseId;
    private String motif;
    private Double price;
    private Integer quantity;
    @Enumerated(EnumType.STRING)
    private Currency currency;
    private LocalDate expenseDate;

    @ManyToOne
    private UserAccount userAccount;
    @ManyToOne
    private Budget budget;

}
