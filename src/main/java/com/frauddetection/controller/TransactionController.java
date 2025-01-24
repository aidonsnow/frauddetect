package com.frauddetection.controller;

import com.frauddetection.model.Transaction;
import com.frauddetection.service.FraudDetectionService;
import com.frauddetection.service.MessageQueueService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TransactionController {

    private final MessageQueueService messageQueueService;
    private final FraudDetectionService fraudDetectionService;

    public TransactionController(MessageQueueService messageQueueService, FraudDetectionService fraudDetectionService) {
        this.messageQueueService = messageQueueService;
        this.fraudDetectionService = fraudDetectionService;
    }

    @PostMapping(value = "/transactions", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> handleTransaction(@RequestBody Transaction transaction) {
        try {
            messageQueueService.sendTransactionToQueue(transaction);
            fraudDetectionService.startDetection();
            return new ResponseEntity<>("Transaction sent to queue and detection started", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to process transaction: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}