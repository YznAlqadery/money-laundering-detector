package com.yzn.aml.detector.service;

import com.yzn.aml.detector.enums.PaymentFormat;
import com.yzn.aml.detector.model.Transaction;
import com.yzn.aml.detector.repository.TransactionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class ImportCSVToDB implements CommandLineRunner {

    private final TransactionRepository transactionRepository;

    public ImportCSVToDB(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        String filePath = "C:\\Users\\yazan\\Desktop\\CSV_DataSets\\HI-Medium_Trans.csv";

        if (transactionRepository.count() > 0) {
           // System.out.println("Data already imported. Skipping import.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            reader.readLine(); // skip column names
            String line;
            List<Transaction> batch = new ArrayList<>();
            int batchSize = 5000;

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
            //int totalImported = 0;

            while ((line = reader.readLine()) != null) {
                String[] columns = line.split(",", -1);

                Transaction transaction = new Transaction();
                transaction.setTimestamp(LocalDateTime.parse(columns[0], formatter));
                transaction.setFromBank(columns[1]);
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

                if (batch.size() >= batchSize) {
                    transactionRepository.saveAll(batch);
                    //totalImported += batch.size();
                    batch.clear();
                    //System.out.println("Imported so far: " + totalImported);
                }
            }

            if (!batch.isEmpty()) {
                transactionRepository.saveAll(batch);
                //totalImported += batch.size();
            }

            //System.out.println("Import finished. Total imported: " + totalImported);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}



