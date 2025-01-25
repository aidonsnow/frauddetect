package com.frauddetection.service;

import com.frauddetection.model.Transaction;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FraudDetectionServiceTest {

    private final FraudDetectionService fraudDetectionService = new FraudDetectionService();

    @Test
    void testDetectFraud_AmountAboveThreshold() {
        // 创建测试数据，金额大于 10000
        Transaction transaction = new Transaction();
        transaction.setTransactionId("txn123");
        transaction.setAccountId("acc456");
        transaction.setAmount(BigDecimal.valueOf(15000)); // 使用 BigDecimal.valueOf

        // 验证返回结果
        assertTrue(fraudDetectionService.detectFraud(transaction));
    }

    @Test
    void testDetectFraud_AmountBelowThreshold() {
        // 创建测试数据，金额小于 10000
        Transaction transaction = new Transaction();
        transaction.setTransactionId("txn456");
        transaction.setAccountId("acc789");
        transaction.setAmount(BigDecimal.valueOf(5000)); // 使用 BigDecimal.valueOf

        // 验证返回结果
        assertFalse(fraudDetectionService.detectFraud(transaction));
    }

    @Test
    void testDetectFraud_AmountNull() {
        // 创建测试数据，金额为空
        Transaction transaction = new Transaction();
        transaction.setTransactionId("txn789");
        transaction.setAccountId("acc123");
        transaction.setAmount(null);

        // 验证返回结果
        assertFalse(fraudDetectionService.detectFraud(transaction));
    }
}
