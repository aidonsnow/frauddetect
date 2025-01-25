package com.frauddetection.mq;

import com.frauddetection.model.Transaction;
import com.google.auth.oauth2.GoogleCredentials;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
class GCPPubSubServiceTest {

    @Autowired
    private GCPPubSubService gcpPubSubService;

    @Test
    void testPublishMessage() {
        Transaction transaction = new Transaction();
        transaction.setTransactionId("txn123");
        transaction.setAccountId("acc456");
        transaction.setAmount(BigDecimal.valueOf(15000));

        // 验证发布不会抛出异常
        assertDoesNotThrow(() -> gcpPubSubService.publishMessage(transaction));
    }
}
