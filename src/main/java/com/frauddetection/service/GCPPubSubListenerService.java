package com.frauddetection.service;

import com.google.cloud.pubsub.v1.Subscriber;
import com.google.cloud.pubsub.v1.MessageReceiver;
import com.google.pubsub.v1.ProjectSubscriptionName;
import com.google.pubsub.v1.PubsubMessage;
import com.google.cloud.pubsub.v1.AckReplyConsumer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class GCPPubSubListenerService {

    @Value("${gcp.project-id}")
    private String projectId;

    @Value("${gcp.pubsub.subscription-id}")
    private String subscriptionId;

    public void startListening() {
        ProjectSubscriptionName subscriptionName = ProjectSubscriptionName.of(projectId, subscriptionId);

        // 使用 MessageReceiver 处理消息
        MessageReceiver receiver = (PubsubMessage message, AckReplyConsumer consumer) -> {
            try {
                System.out.println("Received message: " + message.getData().toStringUtf8());
                consumer.ack(); // 确认消息
            } catch (Exception e) {
                consumer.nack(); // 处理失败
            }
        };

        Subscriber subscriber = Subscriber.newBuilder(subscriptionName, receiver).build();
        subscriber.startAsync().awaitRunning();
    }
}
