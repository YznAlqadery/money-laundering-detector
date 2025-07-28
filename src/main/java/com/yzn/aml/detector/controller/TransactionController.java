package com.yzn.aml.detector.controller;


import com.yzn.aml.detector.model.Transaction;
import com.yzn.aml.detector.service.TransactionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public List<Transaction> getTransactions() throws FileNotFoundException {
        String filePath = "C:\\Users\\yazan\\Desktop\\CSV_DataSets\\HI-Medium_Trans.csv";
        transactionService.importCSVToDB(filePath);

        return transactionService.getTransactions();
    }
}
