package com.frauddetection.mq;

import com.frauddetection.model.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

class FraudAlertPublisherTest {

    private FraudAlertPublisher fraudAlertPublisher;

    @BeforeEach
    void setUp() {
        fraudAlertPublisher = new FraudAlertPublisher();
    }

    @Test
    void testPublishMessage() {
        Transaction transaction = new Transaction();
        transaction.setTransactionId("12345");

        // 调用 publishMessage 并验证不会抛出异常
        assertDoesNotThrow(() -> fraudAlertPublisher.publishMessage(transaction));
    }
}
