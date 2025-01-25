package com.frauddetection.controller;

import com.frauddetection.model.Transaction;
import com.frauddetection.service.FraudDetectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/fraud")
public class FraudDetectionController {

    @Autowired
    private FraudDetectionService fraudDetectionService;

    // 接收交易并进行欺诈检测
    @PostMapping("/check")
    public String checkFraud(@RequestBody Transaction transaction) {
        boolean isFraud = fraudDetectionService.detectFraud(transaction); // 调用 detectFraud 方法
        return isFraud ? "Fraudulent Transaction" : "Legitimate Transaction";
    }
}
