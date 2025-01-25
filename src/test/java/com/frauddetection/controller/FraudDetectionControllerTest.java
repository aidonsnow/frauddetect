package com.frauddetection.controller;

import com.frauddetection.model.Transaction;
import com.frauddetection.service.AlertService;
import com.frauddetection.service.FraudDetectionService;
import com.frauddetection.rules.ThresholdRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FraudDetectionControllerTest {

    private FraudDetectionController fraudDetectionController;
    private AlertService mockAlertService;

    @BeforeEach
    void setUp() {
        // 初始化规则列表
        FraudDetectionService fraudDetectionService = new FraudDetectionService(
                List.of(new ThresholdRule(new BigDecimal("10000")))
        );

        // 模拟 AlertService
        mockAlertService = Mockito.mock(AlertService.class);

        fraudDetectionController = new FraudDetectionController(fraudDetectionService, mockAlertService);
    }

    @Test
    void testCheckFraud() {
        Transaction transaction = new Transaction();
        transaction.setTransactionId("12345");
        transaction.setAmount(new BigDecimal("15000"));

        String result = fraudDetectionController.checkFraud(transaction);
        assertEquals("Fraudulent Transaction Detected by Rule: ThresholdRule", result);
    }
}
