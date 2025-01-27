package com.frauddetection.mq;

import com.frauddetection.model.Transaction;
import com.google.auth.oauth2.GoogleCredentials;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.io.FileInputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
class FraudAlertPublisherTest {

    private FraudAlertPublisher fraudAlertPublisher;

    @Value("${gcp.service-account-key-path}")
    private String serviceAccountKeyPath;  // 使用文件路径而不是 Base64 字符串

    @Value("${gcp.project-id}")
    private String projectId;

    @Value("${gcp.pubsub.alert-topic-id}")
    private String alertTopicId;

    @BeforeEach
    void setUp() throws Exception {
        // 使用文件路径加载服务账号密钥
        GoogleCredentials googleCredentials;
        try {
            googleCredentials = GoogleCredentials.fromStream(new FileInputStream(serviceAccountKeyPath));
        } catch (IOException e) {
            throw new RuntimeException("Failed to load service account key from file: " + serviceAccountKeyPath, e);
        }

        // 初始化 FraudAlertPublisher
        fraudAlertPublisher = new FraudAlertPublisher(googleCredentials);

        // 设置测试环境所需的 Spring @Value 注入字段
        ReflectionTestUtils.setField(fraudAlertPublisher, "projectId", projectId);
        ReflectionTestUtils.setField(fraudAlertPublisher, "alertTopicId", alertTopicId);
    }

    @Test
    void testPublishMessage() {
        // 创建测试用的交易数据
        Transaction transaction = new Transaction();
        transaction.setTransactionId("txn123");
        transaction.setAccountId("acc456");
        transaction.setAmount(BigDecimal.valueOf(15000));

        // 验证发布不会抛出异常
        assertDoesNotThrow(() -> fraudAlertPublisher.publish("FraudAlerts", transaction.getTransactionId()));
        System.out.println("Test for publishMessage passed.");
    }
}
