package com.frauddetection.controller;

import com.frauddetection.model.Transaction;
import com.frauddetection.rules.Rule;
import com.frauddetection.service.FraudDetectionService;
import com.frauddetection.service.RuleLoaderService;
import com.frauddetection.mq.MQService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fraud")
public class FraudDetectionController {

    private final FraudDetectionService fraudDetectionService;

    public FraudDetectionController(RuleLoaderService ruleLoaderService, @Qualifier("fraudAlertPublisher") MQService mqService) {
        List<Rule> rules = ruleLoaderService.loadRules();
        this.fraudDetectionService = new FraudDetectionService(rules, mqService);
    }

    @PostMapping("/check")
    public String checkFraud(@RequestBody Transaction transaction) {
        return fraudDetectionService.detectFraud(transaction);
    }
}
