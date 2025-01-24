package com.frauddetection.service;

import com.frauddetection.model.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AlertService {

    private static final Logger logger = LoggerFactory.getLogger(AlertService.class);

    public void sendAlert(Transaction transaction) {
        // 实际实现中发送告警，如通过AWS SNS
        logger.warn("Sending alert for fraudulent transaction: {}", transaction);
    }
}