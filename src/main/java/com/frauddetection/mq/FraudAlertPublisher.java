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
 * Fraud alert publisher using GCP Pub/Sub.
 */
@Service("fraudAlertPublisher")
public class FraudAlertPublisher implements MQPublisher {

    @Value("${gcp.project-id}")
    private String projectId;

    @Value("${gcp.pubsub.alert-topic-id}")
    private String alertTopicId;

    private final GoogleCredentials googleCredentials;

    public FraudAlertPublisher(GoogleCredentials googleCredentials) {
        this.googleCredentials = googleCredentials;
    }

    /**
     * Publishes a fraud alert message to the GCP Pub/Sub topic.
     *
     * @param topic   the topic to publish to
     * @param message the message to publish
     */
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

            // Publish the message
            String messageId = publisher.publish(pubsubMessage).get();
            System.out.println("Published message to topic " + topic + " with ID: " + messageId);

            // Shutdown Publisher to release resources
            publisher.shutdown();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException("Failed to publish message: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize Publisher: " + e.getMessage(), e);
        }
    }

    /**
     * Publishes a fraud alert with transaction details.
     *
     * @param transaction the transaction to publish
     */
    public void publishTransaction(Transaction transaction) {
        try {
            ProjectTopicName topicName = ProjectTopicName.of(projectId, alertTopicId);

            // Initialize Publisher with GoogleCredentials
            Publisher publisher = Publisher.newBuilder(topicName)
                    .setCredentialsProvider(() -> googleCredentials)
                    .build();

            ByteString data = ByteString.copyFromUtf8(transaction.getTransactionId());
            PubsubMessage message = PubsubMessage.newBuilder()
                    .setData(data)
                    .putAttributes("accountId", transaction.getAccountId())
                    .putAttributes("amount", transaction.getAmount().toString())
                    .build();

            // Publish the transaction details
            String messageId = publisher.publish(message).get();
            System.out.println("Published transaction message with ID: " + messageId);

            // Shutdown Publisher to release resources
            publisher.shutdown();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException("Failed to publish transaction: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize Publisher: " + e.getMessage(), e);
        }
    }
}
