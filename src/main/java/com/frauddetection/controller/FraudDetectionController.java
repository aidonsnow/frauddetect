package com.frauddetection.controller;

import com.frauddetection.model.Transaction;
import com.frauddetection.service.AlertService;
import com.frauddetection.service.FraudDetectionService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/fraud")
public class FraudDetectionController {

    private final FraudDetectionService fraudDetectionService;
    private final AlertService alertService;

    public FraudDetectionController(FraudDetectionService fraudDetectionService, AlertService alertService) {
        this.fraudDetectionService = fraudDetectionService;
        this.alertService = alertService;
    }

    @PostMapping("/check")
    public String checkFraud(@RequestBody Transaction transaction) {
        String result = fraudDetectionService.detectFraud(transaction);
        if (result.startsWith("Fraudulent")) {
            alertService.sendAlert(transaction, result);
        }
        return result;
    }
}
