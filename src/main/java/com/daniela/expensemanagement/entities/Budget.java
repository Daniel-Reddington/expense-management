package com.daniela.expensemanagement.entities;

import com.daniela.expensemanagement.enumeration.Currency;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Budget {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long budgetId;
    @Column(unique = true)
    private String category;
    private Double amount;
    @Enumerated(EnumType.STRING)
    private Currency currency;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @ManyToOne
    private UserAccount userAccount;
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Income> incomes = new HashSet<>();
    @OneToMany(mappedBy = "budget")
    private List<Expense> expenses;
}
