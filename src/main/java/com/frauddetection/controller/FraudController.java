package com.frauddetection.controller;

import com.frauddetection.model.Transaction;
import com.frauddetection.service.FraudDetectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/fraud-detection")
public class FraudController {

    @Autowired
    private FraudDetectionService fraudDetectionService;

    // 接受外部交易请求
    @PostMapping("/detect")
    public String detectFraud(@RequestBody Transaction transaction) {
        boolean isFraud = fraudDetectionService.detectFraud(transaction);
        if (isFraud) {
            return "Fraudulent transaction detected!";
        }
        return "Transaction is safe.";
    }
}
