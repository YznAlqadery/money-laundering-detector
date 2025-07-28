package com.yzn.aml.detector.service;

import com.yzn.aml.detector.model.PaymentFormat;
import com.yzn.aml.detector.model.Transaction;
import com.yzn.aml.detector.repository.TransactionRepository;
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

    public void importCSVToDB(String path) throws FileNotFoundException {
        if (transactionRepository.count() > 0) {
            System.out.println("Transactions already exist in DB — skipping import.");
            return;
        }

        try(BufferedReader reader = new BufferedReader(new FileReader(path))){

            reader.readLine(); // skip the column names
            String line;
            List<Transaction> batch = new ArrayList<>();
            int batchSize = 5000;

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");


            while ((line = reader.readLine()) != null){
                String columns[] = line.split(",",-1);

                Transaction transaction = new Transaction();
                transaction.setTimestamp(LocalDateTime.parse(columns[0],formatter));
                transaction.setFromBank(columns[1]);
                System.out.println(columns[0]);
                transaction.setFromAccount(columns[2]);
                transaction.setToBank(columns[3]);
                transaction.setToAccount(columns[4]);
                transaction.setAmountReceived(new BigDecimal(columns[5]));
                transaction.setReceivingCurrency(columns[6]);
                transaction.setAmountPaid(new BigDecimal(columns[7]));
                transaction.setPaymentCurrency(columns[8]);
                transaction.setPaymentFormat(
                        PaymentFormat.valueOf(columns[9].toUpperCase().replace(" ", "_"))
                );

                transaction.setLaundering("1".equals(columns[10].trim()));


                batch.add(transaction);

                if(batch.size() >= batchSize){
                    transactionRepository.saveAll(batch);
                    batch.clear();
                }


            }
            if(!batch.isEmpty()){
                transactionRepository.saveAll(batch);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Transaction> getTransactions(){
        return transactionRepository.findAll();
    }
}
