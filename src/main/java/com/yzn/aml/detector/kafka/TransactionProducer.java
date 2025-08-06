package com.yzn.aml.detector.kafka;


import com.yzn.aml.detector.model.Transaction;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class TransactionProducer {

    private final KafkaTemplate<String, Transaction> kafkaTemplate;


    public TransactionProducer(KafkaTemplate<String, Transaction> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishMessage(Transaction transaction){
        kafkaTemplate.send("transaction-topic",transaction);
    }
}
