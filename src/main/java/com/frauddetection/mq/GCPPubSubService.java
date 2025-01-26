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

/**
 * GCP Pub/Sub implementation of MQPublisher and MQSubscriber.
 */
@Service
public class GCPPubSubService implements MQPublisher, MQSubscriber {

    @Value("${gcp.project-id}")
    private String projectId;

    @Value("${gcp.pubsub.topic-id}")
    private String topicId;

    private final GoogleCredentials googleCredentials;

    public GCPPubSubService(GoogleCredentials googleCredentials) {
        this.googleCredentials = googleCredentials;
    }

    @Override
    public void publish(String topic, String message) {
        try {
            ProjectTopicName topicName = ProjectTopicName.of(projectId, topic);

            // Initialize Publisher with GoogleCredentials
            Publisher publisher = Publisher.newBuilder(topicName)
                    .setCredentialsProvider(() -> googleCredentials)
                    .build();

            ByteString data = ByteString.copyFrom(message.getBytes());
            PubsubMessage pubsubMessage = PubsubMessage.newBuilder()
                    .setData(data)
                    .build();

            publisher.publish(pubsubMessage).get();
            System.out.println("Message published to topic " + topic + ": " + message);

            // Shutdown Publisher to release resources
            publisher.shutdown();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException("Failed to publish message: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize Publisher: " + e.getMessage(), e);
        }
    }

    @Override
    public void subscribe(String topic) {
        // Subscription logic to be implemented as needed
        System.out.println("Subscribing to GCP Pub/Sub topic: " + topic);
    }

    /**
     * Helper method to publish a Transaction object with attributes.
     * Retained from the original implementation for backward compatibility.
     */
    public void publishTransaction(Transaction transaction) {
        try {
            ProjectTopicName topicName = ProjectTopicName.of(projectId, topicId);

            // Initialize Publisher with GoogleCredentials
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
            System.out.println("Transaction published: " + transaction.getTransactionId());

            // Shutdown Publisher to release resources
            publisher.shutdown();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException("Failed to publish transaction: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize Publisher: " + e.getMessage(), e);
        }
    }
}
