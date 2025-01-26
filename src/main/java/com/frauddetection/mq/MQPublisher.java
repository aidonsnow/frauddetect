package com.frauddetection.mq;

/**
 * Interface for message publishing.
 */
public interface MQPublisher {
    void publish(String topic, String message);
}
