package com.yzn.aml.detector.repository;

import com.yzn.aml.detector.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    List<Transaction> findByIdGreaterThan(Integer lastProcessedId);
}
