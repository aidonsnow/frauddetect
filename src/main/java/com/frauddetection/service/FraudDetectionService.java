package com.frauddetection.service;

import com.frauddetection.rules.Rule;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FraudDetectionService {

    private final List<Rule> rules;

    public FraudDetectionService(List<Rule> rules) {
        this.rules = rules;
    }

    public String detectFraud(com.frauddetection.model.Transaction transaction) {
        for (Rule rule : rules) {
            if (rule.matches(transaction)) {
                return "Fraudulent Transaction Detected by Rule: " + rule.getName();
            }
        }
        return "Transaction is Legitimate";
    }
}
