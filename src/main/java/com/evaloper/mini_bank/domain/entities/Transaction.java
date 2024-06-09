package com.evaloper.mini_bank.domain.entities;
import com.evaloper.mini_bank.domain.enums.TransactionStatus;
import com.evaloper.mini_bank.domain.enums.TransactionType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "transaction")
public class Transaction{
    @Id
    // uuid will allow us generate unique random characters, is used by most banks
    @GeneratedValue(strategy = GenerationType.UUID)
    private String transactionId;
    private TransactionType transactionType;
    private String accountNumber;
    private BigDecimal amount;
    private TransactionStatus status;
    @CreationTimestamp
    private LocalDate createdAt;
    @UpdateTimestamp
    private LocalDate modifiedAt;
}