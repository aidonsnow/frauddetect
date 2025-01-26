
package com.frauddetection.service;

import com.frauddetection.model.Transaction;
import com.frauddetection.rules.ThresholdRule;
import com.frauddetection.rules.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FraudDetectionServiceTest {

    private FraudDetectionService fraudDetectionService;

    @BeforeEach
    void setUp() {
        Rule rule = new ThresholdRule(new BigDecimal("10000"));
        fraudDetectionService = new FraudDetectionService(List.of(rule));
    }

    @Test
    void testDetectFraud_FraudulentTransaction() {
        Transaction transaction = new Transaction();
        transaction.setTransactionId("12345");
        transaction.setAmount(new BigDecimal("15000"));

        String result = fraudDetectionService.detectFraud(transaction);

        assertEquals("Fraudulent Transaction Detected by Rule: ThresholdRule", result);
    }

    @Test
    void testDetectFraud_NonFraudulentTransaction() {
        Transaction transaction = new Transaction();
        transaction.setTransactionId("12346");
        transaction.setAmount(new BigDecimal("5000"));

        String result = fraudDetectionService.detectFraud(transaction);

        assertEquals("Transaction is Legitimate", result);
    }
}
