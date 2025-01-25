package com.frauddetection.service;

import com.frauddetection.mq.MQService;
import com.frauddetection.model.Transaction;
import com.frauddetection.rules.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class FraudDetectionServiceTest {

    private FraudDetectionService fraudDetectionService;
    private MQService mockMqService;

    @BeforeEach
    void setUp() {
        // 使用 Mockito 模拟 Rule
        Rule mockRule = Mockito.mock(Rule.class);
        when(mockRule.matches(any(Transaction.class))).thenReturn(true);
        when(mockRule.getName()).thenReturn("MockRule");

        mockMqService = Mockito.mock(MQService.class);

        // 初始化 FraudDetectionService
        fraudDetectionService = new FraudDetectionService(List.of(mockRule), mockMqService);
    }

    @Test
    void testDetectFraud_FraudulentTransaction() {
        Transaction transaction = new Transaction();
        transaction.setTransactionId("12345");
        transaction.setAmount(new BigDecimal("15000"));

        // 调用检测方法
        String result = fraudDetectionService.detectFraud(transaction);

        // 验证结果和消息发布
        assertEquals("Fraudulent Transaction Detected by Rule: MockRule", result);
        verify(mockMqService, times(1)).publishMessage(transaction);
    }

    @Test
    void testDetectFraud_LegitimateTransaction() {
        // 模拟 Rule 返回 false
        Rule mockRule = Mockito.mock(Rule.class);
        when(mockRule.matches(any(Transaction.class))).thenReturn(false);

        // 更新 FraudDetectionService
        fraudDetectionService = new FraudDetectionService(List.of(mockRule), mockMqService);

        Transaction transaction = new Transaction();
        transaction.setTransactionId("12345");
        transaction.setAmount(new BigDecimal("5000"));

        // 调用检测方法
        String result = fraudDetectionService.detectFraud(transaction);

        // 验证结果和消息未发布
        assertEquals("Transaction is Legitimate", result);
        verify(mockMqService, never()).publishMessage(transaction);
    }
}
