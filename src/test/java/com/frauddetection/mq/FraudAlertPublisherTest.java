package com.frauddetection.mq;

import com.frauddetection.model.Transaction;
import com.google.auth.oauth2.GoogleCredentials;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
class FraudAlertPublisherTest {

    private FraudAlertPublisher fraudAlertPublisher;

    @Value("${gcp.service-account-key-base64}")
    private String base64EncodedKey;

    @Value("${gcp.project-id}")
    private String projectId;

    @Value("${gcp.pubsub.alert-topic-id}")
    private String alertTopicId;

    @BeforeEach
    void setUp() throws Exception {
        // 从配置文件中解码 Base64 编码的密钥
        byte[] decodedKey = Base64.getDecoder().decode(base64EncodedKey);
        GoogleCredentials googleCredentials = GoogleCredentials.fromStream(new java.io.ByteArrayInputStream(decodedKey));

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
