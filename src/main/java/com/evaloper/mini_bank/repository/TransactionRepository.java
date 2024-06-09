package com.evaloper.mini_bank.repository;

import com.evaloper.mini_bank.domain.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
