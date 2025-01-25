package com.frauddetection.mq;

import com.frauddetection.model.Transaction;

public interface MQService {
    void publishMessage(Transaction transaction);  // 发布消息
    void subscribeMessage();  // 订阅消息
}
