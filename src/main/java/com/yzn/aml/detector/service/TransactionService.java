package com.yzn.aml.detector.service;

import com.yzn.aml.detector.enums.PaymentFormat;
import com.yzn.aml.detector.model.Transaction;
import com.yzn.aml.detector.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;


    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }


    public Page<Transaction> getTransactions(int pageNumber, int pageSize){
        Pageable pageable = PageRequest.of(pageNumber,pageSize);
        return transactionRepository.findAll(pageable);
    }

    public Transaction getTransactionById(Integer id){
        return transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No Transaction with this " + id +" found"));
    }

    public Transaction createTransaction(Transaction transaction){
        return transactionRepository.save(transaction);
    }

    public Transaction updateTransaction(Transaction updatedTransaction, Integer id){
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No Transaction with " + id + " found."));

        transaction.setTimestamp(updatedTransaction.getTimestamp() != null ? updatedTransaction.getTimestamp(): transaction.getTimestamp());
        transaction.setFromBank(updatedTransaction.getFromBank() != null ? updatedTransaction.getFromBank(): transaction.getFromBank() );
        transaction.setFromAccount(updatedTransaction.getFromAccount() != null ? updatedTransaction.getFromAccount(): transaction.getFromAccount() );
        transaction.setToBank(updatedTransaction.getToBank() != null ? updatedTransaction.getToBank(): transaction.getToBank() );
        transaction.setToAccount(updatedTransaction.getToAccount() != null ? updatedTransaction.getToAccount(): transaction.getToAccount() );
        transaction.setAmountReceived(updatedTransaction.getAmountReceived() != null ? updatedTransaction.getAmountReceived(): transaction.getAmountReceived() );
        transaction.setReceivingCurrency(updatedTransaction.getReceivingCurrency() != null ? updatedTransaction.getReceivingCurrency(): transaction.getReceivingCurrency() );
        transaction.setAmountPaid(updatedTransaction.getAmountPaid() != null ? updatedTransaction.getAmountPaid(): transaction.getAmountPaid() );
        transaction.setPaymentCurrency(updatedTransaction.getPaymentCurrency() != null ? updatedTransaction.getPaymentCurrency(): transaction.getPaymentCurrency() );
        transaction.setPaymentFormat(updatedTransaction.getPaymentFormat() != null ? updatedTransaction.getPaymentFormat(): transaction.getPaymentFormat() );
        transaction.setLaundering(updatedTransaction.getLaundering() != null ? updatedTransaction.getLaundering(): transaction.getLaundering() );

        transactionRepository.save(transaction);
        return transaction;
    }

    public void deleteTransaction(Integer id){
        transactionRepository.deleteById(id);
    }
}
