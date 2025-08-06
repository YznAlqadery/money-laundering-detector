package com.yzn.aml.detector.scheduler;


import com.yzn.aml.detector.kafka.TransactionProducer;
import com.yzn.aml.detector.model.Transaction;
import com.yzn.aml.detector.repository.TransactionRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TransactionScheduler {

    private final TransactionRepository transactionRepository;
    private final TransactionProducer transactionProducer;
    private Integer lastProcessedId = 0;

    public TransactionScheduler(TransactionRepository transactionRepository, TransactionProducer transactionProducer) {
        this.transactionRepository = transactionRepository;
        this.transactionProducer = transactionProducer;
    }

    @Scheduled(cron = "0 */1 * * * *")
    public void publishTransactionToKafka(){

        List<Transaction> transactions = transactionRepository.findByIdGreaterThan(lastProcessedId);
        for (Transaction transaction : transactions) {
            transactionProducer.publishMessage(transaction);
            lastProcessedId = transaction.getId(); // update the pointer
        }


    }
}
