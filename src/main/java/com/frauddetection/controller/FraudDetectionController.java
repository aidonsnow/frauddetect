package com.frauddetection.controller;

import com.frauddetection.model.Transaction;
import com.frauddetection.service.FraudDetectionService;
import com.frauddetection.service.RuleLoaderService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/fraud")
public class FraudDetectionController {

    private final FraudDetectionService fraudDetectionService;

    public FraudDetectionController(RuleLoaderService ruleLoaderService) {
        this.fraudDetectionService = new FraudDetectionService(ruleLoaderService.loadRules());
    }

    @PostMapping("/check")
    public String checkFraud(@RequestBody Transaction transaction) {
        String matchedRule = fraudDetectionService.processTransaction(transaction);
        if (matchedRule != null) {
            return "Fraudulent Transaction (Rule: " + matchedRule + ")";
        }
        return "Legitimate Transaction";
    }
}
