package com.frauddetection.service;

import com.frauddetection.mq.MQPublisher;
import com.frauddetection.model.Transaction;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AlertService {

    private final MQPublisher mqPublisher;

    @Value("${gcp.pubsub.alert-topic-id}")
    private String alertTopicId; // 从配置文件加载

    public AlertService(@Qualifier("fraudAlertPublisher") MQPublisher mqPublisher) {
        this.mqPublisher = mqPublisher;
    }

    /**
     * 发送普通告警消息
     *
     * @param transaction 交易对象
     */
    public void sendAlert(Transaction transaction) {
        mqPublisher.publish(alertTopicId, transaction.getTransactionId());
    }

    /**
     * 发送包含附加信息的告警消息
     *
     * @param transaction 交易对象
     * @param metadata    附加信息
     */
    public void sendAlert(Transaction transaction, String metadata) {
        String message = transaction.getTransactionId() + " | Metadata: " + metadata;
        mqPublisher.publish(alertTopicId, message);
    }
}
