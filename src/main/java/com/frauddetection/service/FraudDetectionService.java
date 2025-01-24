package com.frauddetection.service;

import com.frauddetection.engine.RuleEngine;
import com.frauddetection.model.Transaction;
import org.springframework.stereotype.Service;

@Service
public class FraudDetectionService {

    private final RuleEngine ruleEngine;
    private final AlertService alertService;
    private final MessageQueueService messageQueueService;

    public FraudDetectionService(RuleEngine ruleEngine, AlertService alertService, MessageQueueService messageQueueService) {
        this.ruleEngine = ruleEngine;
        this.alertService = alertService;
        this.messageQueueService = messageQueueService;
    }

    public void startDetection() {
        Transaction transaction = messageQueueService.receiveTransactionFromQueue();
        while (transaction != null) {
            if (ruleEngine.isFraudulent(transaction)) {
                alertService.sendAlert(transaction);
            }
            transaction = messageQueueService.receiveTransactionFromQueue();
        }
    }
}