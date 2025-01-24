package com.frauddetection.service;

import com.frauddetection.model.Transaction;

public interface MessageQueueService {
    void sendTransactionToQueue(Transaction transaction);
    Transaction receiveTransactionFromQueue();
}