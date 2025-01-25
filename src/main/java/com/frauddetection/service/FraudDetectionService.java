package com.frauddetection.service;

import com.frauddetection.model.Transaction;
import org.springframework.stereotype.Service;

@Service
public class FraudDetectionService {

    // 简单的规则：交易金额超过1000为欺诈
    public boolean detectFraud(Transaction transaction) {
        if (transaction.getAmount() > 1000) {
            return true; // 假设金额超过1000是欺诈
        }
        return false;
    }
}
