package com.frauddetection.mq;

import org.springframework.stereotype.Service;


@Service("fraudAlertPublisher")
public class FraudAlertPublisher implements MQService {
    @Override
    public void publishMessage(com.frauddetection.model.Transaction transaction) {
        System.out.println("Publishing alert for transaction: " + transaction.getTransactionId());
    }

    @Override
    public void subscribeMessage() {
        System.out.println("Subscribing to alert messages.");
    }
}
