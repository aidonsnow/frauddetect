package com.frauddetection.service;

import com.frauddetection.model.Transaction;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class FraudDetectionService {

    // 检测交易是否存在欺诈
    public boolean detectFraud(Transaction transaction) {
        if (transaction.getAmount() == null) {
            return false;
        }
        // 简单规则：如果交易金额大于 10000，则标记为欺诈
        return transaction.getAmount().compareTo(new BigDecimal("10000")) > 0;
    }
}
