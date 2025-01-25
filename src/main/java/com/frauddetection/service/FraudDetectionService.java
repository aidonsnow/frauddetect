package com.frauddetection.service;

import com.frauddetection.mq.MQService;
import com.frauddetection.rules.Rule;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class FraudDetectionService {

    private final List<Rule> rules;
    private final MQService mqService;

    public FraudDetectionService(List<Rule> rules, @Qualifier("fraudAlertPublisher") MQService mqService) {
        this.rules = rules;
        this.mqService = mqService;
    }

    public String detectFraud(com.frauddetection.model.Transaction transaction) {
        for (Rule rule : rules) {
            if (rule.matches(transaction)) {
                String message = "Fraudulent Transaction Detected by Rule: " + rule.getName();
                mqService.publishMessage(transaction);
                return message;
            }
        }
        return "Transaction is Legitimate";
    }
}
