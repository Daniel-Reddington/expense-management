package com.daniela.expensemanagement.entities;

import ch.qos.logback.classic.pattern.LineSeparatorConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String email;
    private String phoneNumber;
    private String username;
    private String password;
    private LocalDateTime createdAt;
    @OneToMany(fetch = FetchType.EAGER)
    private List<Income> incomes;
    @OneToMany(fetch = FetchType.EAGER)
    private List<Budget> budgets;
    @OneToMany(fetch = FetchType.EAGER)
    private List<Expense> expenses;

}
