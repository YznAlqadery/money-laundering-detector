package com.yzn.aml.detector.controller;


import com.yzn.aml.detector.model.Transaction;
import com.yzn.aml.detector.service.TransactionService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("")
    public ResponseEntity<Page<Transaction>> getTransactions(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "20") Integer size )  {

        return ResponseEntity.ok(transactionService.getTransactions(page,size));
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
