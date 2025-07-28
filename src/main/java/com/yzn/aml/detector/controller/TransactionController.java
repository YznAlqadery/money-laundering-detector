package com.yzn.aml.detector.controller;


import com.yzn.aml.detector.model.Transaction;
import com.yzn.aml.detector.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("")
    public ResponseEntity<List<Transaction>> getTransactions() throws FileNotFoundException {
        String filePath = "C:\\Users\\yazan\\Desktop\\CSV_DataSets\\HI-Medium_Trans.csv";
        transactionService.importCSVToDB(filePath);

        return ResponseEntity.ok(transactionService.getTransactions());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable Integer id){
        return ResponseEntity.ok(transactionService.getTransactionById(id));
    }

    @PostMapping("")
    public ResponseEntity<Transaction> createTransaction(@RequestBody Transaction transaction){
        return ResponseEntity.ok(transactionService.createTransaction(transaction));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Transaction> updateTransaction (@RequestBody Transaction transaction, @PathVariable Integer id){
        return ResponseEntity.ok(transactionService.updateTransaction(transaction,id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Integer id) {
        transactionService.deleteTransaction(id);
        return ResponseEntity.noContent().build();
    }
}
