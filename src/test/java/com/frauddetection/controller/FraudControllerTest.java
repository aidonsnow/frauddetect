package com.frauddetection.controller;

import com.frauddetection.model.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FraudControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private Transaction transaction;

    @BeforeEach
    public void setup() {
        // 初始化测试数据
        transaction = new Transaction();
        transaction.setAccountId("acc456");
        transaction.setTransactionId("txn123");
        transaction.setTimestamp("2025-01-24T10:00:00");
    }

    @Test
    public void testDetectFraud_FraudulentTransaction() {
        // 设置交易金额大于1000
        transaction.setAmount(1500);

        // 发起 POST 请求
        ResponseEntity<String> response = restTemplate.postForEntity("/api/fraud-detection/detect", transaction, String.class);

        // 验证响应内容
        assertEquals("Fraudulent transaction detected!", response.getBody());
    }

    @Test
    public void testDetectFraud_SafeTransaction() {
        // 设置交易金额小于1000
        transaction.setAmount(500);

        // 发起 POST 请求
        ResponseEntity<String> response = restTemplate.postForEntity("/api/fraud-detection/detect", transaction, String.class);

        // 验证响应内容
        assertEquals("Transaction is safe.", response.getBody());
    }
}
