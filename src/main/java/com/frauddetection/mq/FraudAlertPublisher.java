package com.frauddetection.mq;

import com.frauddetection.model.Transaction;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.pubsub.v1.Publisher;
import com.google.protobuf.ByteString;
import com.google.pubsub.v1.ProjectTopicName;
import com.google.pubsub.v1.PubsubMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service("fraudAlertPublisher")
public class FraudAlertPublisher implements MQService {

    @Value("${gcp.project-id}")
    private String projectId;

    @Value("${gcp.pubsub.alert-topic-id}")
    private String alertTopicId;

    private final GoogleCredentials googleCredentials;

    public FraudAlertPublisher(GoogleCredentials googleCredentials) {
        this.googleCredentials = googleCredentials;
    }

    @Override
    public void publishMessage(Transaction transaction) {
        try {
            // 构建 GCP Pub/Sub 的主题名称
            ProjectTopicName topicName = ProjectTopicName.of(projectId, alertTopicId);

            // 初始化 Publisher
            Publisher publisher = Publisher.newBuilder(topicName)
                    .setCredentialsProvider(() -> googleCredentials)
                    .build();

            // 构建 Pub/Sub 消息
            ByteString data = ByteString.copyFromUtf8(transaction.getTransactionId());
            PubsubMessage message = PubsubMessage.newBuilder()
                    .setData(data)
                    .putAttributes("accountId", transaction.getAccountId())
                    .putAttributes("amount", transaction.getAmount().toString())
                    .build();

            // 发布消息
            String messageId = publisher.publish(message).get();
            System.out.println("Published message with ID: " + messageId);

            // 关闭 Publisher
            publisher.shutdown();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException("Failed to publish message: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize Publisher: " + e.getMessage(), e);
        }
    }

    @Override
    public void subscribeMessage() {
        // 留空，订阅逻辑可在监听器中实现
    }
}
