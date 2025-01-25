package com.frauddetection.controller;

import com.frauddetection.model.Transaction;
import com.frauddetection.service.RuleLoaderService;
import com.frauddetection.mq.MQService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FraudDetectionControllerTest {

    private FraudDetectionController fraudDetectionController;

    @BeforeEach
    void setUp() {
        // 模拟 RuleLoaderService
        RuleLoaderService mockRuleLoaderService = Mockito.mock(RuleLoaderService.class);

        // 模拟 MQService
        MQService mockMqService = Mockito.mock(MQService.class);

        // 初始化 FraudDetectionController
        fraudDetectionController = new FraudDetectionController(mockRuleLoaderService, mockMqService);
    }

    @Test
    void testCheckFraud() {
        // 构造交易数据
        Transaction transaction = new Transaction();
        transaction.setTransactionId("12345");
        transaction.setAmount(new java.math.BigDecimal("1000"));

        // 调用控制器方法
        String result = fraudDetectionController.checkFraud(transaction);

        // 验证返回结果
        assertEquals("Transaction is Legitimate", result);
    }
}
