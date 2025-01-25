package com.frauddetection.service;

import com.frauddetection.rules.Rule;
import com.frauddetection.rules.ThresholdRule;
import com.frauddetection.model.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FraudDetectionServiceTest {

    private FraudDetectionService fraudDetectionService;

    @BeforeEach
    void setUp() {
        // 初始化规则列表
        Rule thresholdRule = new ThresholdRule(new BigDecimal("10000"));
        fraudDetectionService = new FraudDetectionService(List.of(thresholdRule));
    }

    @Test
    void testDetectFraud() {
        Transaction transaction = new Transaction();
        transaction.setTransactionId("12345");
        transaction.setAmount(new BigDecimal("15000"));

        String result = fraudDetectionService.detectFraud(transaction);
        assertEquals("Fraudulent Transaction Detected by Rule: ThresholdRule", result);
    }

    @Test
    void testDetectLegitimateTransaction() {
        Transaction transaction = new Transaction();
        transaction.setTransactionId("67890");
        transaction.setAmount(new BigDecimal("5000"));

        String result = fraudDetectionService.detectFraud(transaction);
        assertEquals("Transaction is Legitimate", result);
    }
}
