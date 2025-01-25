package com.frauddetection.service;

import com.frauddetection.model.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FraudDetectionServiceTest {

    private FraudDetectionService fraudDetectionService;

    @BeforeEach
    public void setup() {
        // 在每个测试方法之前执行，初始化 FraudDetectionService
        fraudDetectionService = new FraudDetectionService();
    }

    @Test
    public void testDetectFraud_AmountGreaterThan1000() {
        // 创建一个金额大于1000的交易对象
        Transaction transaction = new Transaction();
        transaction.setAmount(1500);

        // 进行欺诈检测
        boolean result = fraudDetectionService.detectFraud(transaction);

        // 验证结果
        assertTrue(result, "Transaction with amount greater than 1000 should be flagged as fraud.");
    }

    @Test
    public void testDetectFraud_AmountLessThanOrEqualTo1000() {
        // 创建一个金额小于或等于1000的交易对象
        Transaction transaction = new Transaction();
        transaction.setAmount(500);

        // 进行欺诈检测
        boolean result = fraudDetectionService.detectFraud(transaction);

        // 验证结果
        assertFalse(result, "Transaction with amount less than or equal to 1000 should not be flagged as fraud.");
    }
}
