package com.frauddetection.service;

import com.frauddetection.model.Transaction;
import com.frauddetection.rules.Rule;
import com.frauddetection.rules.RuleEngine;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FraudDetectionService {
    private final RuleEngine ruleEngine = new RuleEngine();

    public FraudDetectionService(List<Rule> rules) {
        rules.forEach(ruleEngine::registerRule);
    }

    public String processTransaction(Transaction transaction) {
        return ruleEngine.executeRules(transaction); // 返回匹配的规则名称
    }
}
