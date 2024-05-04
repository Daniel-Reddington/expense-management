package com.daniela.expensemanagement.entities;

import com.daniela.expensemanagement.enumeration.Currency;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = {
        @Index(name = "unique_source_user", columnList = "source, user_account_id", unique = true)
})
public class Income {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long incomeId;
    private String source;
    private Double amount;
    @Enumerated(EnumType.STRING)
    private Currency currency;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    @ManyToOne
    @ToString.Exclude
    private UserAccount userAccount;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(joinColumns = @JoinColumn(name = "income_id"),
            inverseJoinColumns = @JoinColumn(name = "budget_id"))
    @ToString.Exclude
    private Set<Budget> budgets = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "income")
    private Set<Loan> loans;


    @PrePersist
    @PreUpdate
    public void beforeSaveOrUpdate(){
        source = source != null ? source.toUpperCase() : null;
    }

}
