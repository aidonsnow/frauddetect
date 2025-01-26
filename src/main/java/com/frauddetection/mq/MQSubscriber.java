package com.frauddetection.mq;

/**
 * Interface for message subscribing.
 */
public interface MQSubscriber {
    void subscribe(String topic);
}
