package com.frauddetection.controller;

import com.frauddetection.model.Transaction;
import com.frauddetection.rules.Rule;
import com.frauddetection.rules.ThresholdRule;
import com.frauddetection.service.AlertService;
import com.frauddetection.service.FraudDetectionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class FraudDetectionControllerTest {

    private FraudDetectionController fraudDetectionController;
    private AlertService mockAlertService;

    @BeforeEach
    void setUp() {
        // 动态加载规则
        Rule thresholdRule = new ThresholdRule(new BigDecimal("10000"));
        FraudDetectionService fraudDetectionService = new FraudDetectionService(List.of(thresholdRule));

        // 模拟 AlertService
        mockAlertService = Mockito.mock(AlertService.class);

        fraudDetectionController = new FraudDetectionController(fraudDetectionService, mockAlertService);
    }

    @Test
    void testCheckFraud_FraudulentTransaction() {
        // 模拟高金额交易（超过阈值）
        Transaction transaction = new Transaction();
        transaction.setTransactionId("12345");
        transaction.setAmount(new BigDecimal("15000"));

        String result = fraudDetectionController.checkFraud(transaction);

        // 验证返回值
        assertEquals("Fraudulent Transaction Detected by Rule: ThresholdRule", result);

        // 验证是否触发警报服务
        verify(mockAlertService, times(1)).sendAlert(transaction, result);
    }

    @Test
    void testCheckFraud_NonFraudulentTransaction() {
        // 模拟低金额交易（未超过阈值）
        Transaction transaction = new Transaction();
        transaction.setTransactionId("12346");
        transaction.setAmount(new BigDecimal("5000"));

        String result = fraudDetectionController.checkFraud(transaction);

        // 验证返回值
        assertEquals("Transaction is Legitimate", result);

        // 验证未触发警报服务
        verify(mockAlertService, never()).sendAlert(any(), anyString());
    }

    @Test
    void testCheckFraud_BoundaryTransaction() {
        // 模拟边界值交易（刚好等于阈值）
        Transaction transaction = new Transaction();
        transaction.setTransactionId("12347");
        transaction.setAmount(new BigDecimal("10000"));

        String result = fraudDetectionController.checkFraud(transaction);

        // 验证返回值
        assertEquals("Transaction is Legitimate", result);

        // 验证未触发警报服务
        verify(mockAlertService, never()).sendAlert(any(), anyString());
    }

   /* @Test
    void testCheckFraud_NullTransaction() {
        // 模拟空交易对象
        Transaction transaction = null;

        try {
            fraudDetectionController.checkFraud(transaction);
        } catch (IllegalArgumentException e) {
            assertEquals("Transaction cannot be null", e.getMessage());
        }

        // 验证未触发任何警报服务
        verifyNoInteractions(mockAlertService);
    }*/
}
