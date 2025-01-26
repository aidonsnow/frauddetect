package com.frauddetection.service;

import com.frauddetection.model.Transaction;
import com.frauddetection.rules.RuleEngine;
import org.springframework.stereotype.Service;

@Service
public class FraudDetectionService {
    private final RuleEngine ruleEngine;

    public FraudDetectionService(RuleLoaderService ruleLoaderService) {
        this.ruleEngine = ruleLoaderService.getRuleEngine();
    }

    public String detectFraud(Transaction transaction) {
       /* if (ruleEngine.getRules().isEmpty()) {
            return "empty rules";
        }*/
        String result = ruleEngine.executeRules(transaction);
        if (result != null) {
            return "Fraudulent Transaction Detected by Rule: " + result;
        }
        return "Transaction is Legitimate";
    }
}
