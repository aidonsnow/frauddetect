package com.frauddetection.service;

import com.frauddetection.mq.MQService;
import com.frauddetection.model.Transaction;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class AlertService {

    private final MQService mqService;

    public AlertService(@Qualifier("fraudAlertPublisher") MQService mqService) {
        this.mqService = mqService;
    }

    public void sendAlert(Transaction transaction, String message) {
        System.out.println("Alert: " + message);
        mqService.publishMessage(transaction);
    }
}
