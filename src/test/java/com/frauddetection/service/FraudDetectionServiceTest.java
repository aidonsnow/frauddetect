package com.frauddetection.service;

import com.frauddetection.model.Transaction;
import com.frauddetection.rules.RuleEngine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FraudDetectionServiceTest {

    private FraudDetectionService fraudDetectionService;

    @BeforeEach
    void setUp() {
        // 模拟 RuleLoaderService
        RuleLoaderService mockRuleLoaderService = mock(RuleLoaderService.class);

        // 设置模拟行为
        RuleEngine mockRuleEngine = new RuleEngine();
        when(mockRuleLoaderService.getRuleEngine()).thenReturn(mockRuleEngine);

        // 初始化 FraudDetectionService
        fraudDetectionService = new FraudDetectionService(mockRuleLoaderService);
    }
/*
    @Test
    void testDetectFraud() {
        Transaction transaction = new Transaction();
        transaction.setTransactionId("txn1");
        transaction.setAccountId("acc123");

        String result = fraudDetectionService.detectFraud(transaction);
        assertEquals("empty rules", result, "当规则为空时，应返回 'empty rules'");
    }*/
}
