package com.frauddetection.mq;

import com.frauddetection.model.Transaction;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.pubsub.v1.Publisher;
import com.google.pubsub.v1.ProjectTopicName;
import com.google.pubsub.v1.PubsubMessage;
import com.google.protobuf.ByteString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
public class GCPPubSubService implements MQService {

    @Value("${gcp.project-id}")
    private String projectId;

    @Value("${gcp.pubsub.topic-id}")
    private String topicId;

    private final GoogleCredentials googleCredentials;

    public GCPPubSubService(GoogleCredentials googleCredentials) {
        this.googleCredentials = googleCredentials;
    }

    @Override
    public void publishMessage(Transaction transaction) {
        try {
            ProjectTopicName topicName = ProjectTopicName.of(projectId, topicId);

            // 使用 GoogleCredentials 初始化 Publisher
            Publisher publisher = Publisher.newBuilder(topicName)
                    .setCredentialsProvider(() -> googleCredentials)
                    .build();

            ByteString data = ByteString.copyFrom(transaction.getTransactionId().getBytes());
            PubsubMessage message = PubsubMessage.newBuilder()
                    .setData(data)
                    .putAttributes("accountId", transaction.getAccountId())
                    .putAttributes("amount", transaction.getAmount().toString())
                    .build();

            publisher.publish(message).get();
            System.out.println("Message published: " + transaction.getTransactionId());
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException("Failed to publish message: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize Publisher: " + e.getMessage(), e);
        }
    }

    @Override
    public void subscribeMessage() {
        // 订阅逻辑由监听器服务实现
    }
}
