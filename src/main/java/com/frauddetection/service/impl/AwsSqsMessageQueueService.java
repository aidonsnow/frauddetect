package com.frauddetection.service.impl;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.amazonaws.services.sqs.model.Message;
import com.frauddetection.model.Transaction;
import com.frauddetection.service.MessageQueueService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AwsSqsMessageQueueService implements MessageQueueService {

    private static final Logger logger = LoggerFactory.getLogger(AwsSqsMessageQueueService.class);
    private final AmazonSQS amazonSQS;
    private final String queueUrl;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public AwsSqsMessageQueueService(AmazonSQS amazonSQS, @Value("${aws.sqs.queue.url}") String queueUrl) {
        this.amazonSQS = amazonSQS;
        this.queueUrl = queueUrl;
    }

    @Override
    public void sendTransactionToQueue(Transaction transaction) {
        try {
            // 使用 Jackson 的 ObjectMapper 将 Transaction 对象转换为 JSON 字符串
            String messageBody = objectMapper.writeValueAsString(transaction);
            SendMessageRequest sendMessageRequest = new SendMessageRequest()
                    .withQueueUrl(queueUrl)
                    .withMessageBody(messageBody);
            amazonSQS.sendMessage(sendMessageRequest);
            logger.info("Transaction sent to SQS queue: {}", transaction.getTransactionId());
        } catch (Exception e) {
            logger.error("Failed to send transaction to SQS queue: {}", e.getMessage(), e);
        }
    }

    @Override
    public Transaction receiveTransactionFromQueue() {
        ReceiveMessageRequest receiveMessageRequest = new ReceiveMessageRequest()
                .withQueueUrl(queueUrl)
                .withMaxNumberOfMessages(1);
        List<Message> messages = amazonSQS.receiveMessage(receiveMessageRequest).getMessages();
        if (!messages.isEmpty()) {
            Message message = messages.get(0);
            try {
                // 使用 Jackson 的 ObjectMapper 将 JSON 字符串转换为 Transaction 对象
                Transaction transaction = objectMapper.readValue(message.getBody(), Transaction.class);
                amazonSQS.deleteMessage(queueUrl, message.getReceiptHandle());
                logger.info("Transaction received from SQS queue: {}", transaction.getTransactionId());
                return transaction;
            } catch (Exception e) {
                logger.error("Failed to parse transaction from SQS queue: {}", e.getMessage(), e);
            }
        }
        return null;
    }
}