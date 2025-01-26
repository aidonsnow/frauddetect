package com.frauddetection.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.pubsub.v1.Publisher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;

@Configuration
public class GCPPubSubConfig {

    @Value("${gcp.service-account-key-base64}")
    private String serviceAccountKeyBase64;

    @Value("${gcp.pubsub.alert-topic-id}")
    private String alertTopicId;

    /**
     * 配置 GoogleCredentials，用于 GCP 服务认证
     *
     * @return GoogleCredentials 实例
     * @throws RuntimeException 如果加载凭据失败
     */
    @Bean
    public GoogleCredentials googleCredentials() {
        try {
            // 解码 Base64 内容并加载为 GoogleCredentials
            byte[] decodedKey = Base64.getDecoder().decode(serviceAccountKeyBase64);
            return GoogleCredentials.fromStream(new ByteArrayInputStream(decodedKey));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid Base64 encoded key for Google service account.", e);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load GoogleCredentials from provided key.", e);
        }
    }

    /**
     * 示例：配置 Pub/Sub Publisher（如果需要单例 Publisher 实例）
     *
     * @return Publisher 实例
     */
    @Bean
    public Publisher alertPublisher(GoogleCredentials googleCredentials) {
        try {
            return Publisher.newBuilder(alertTopicId)
                    .setCredentialsProvider(() -> googleCredentials)
                    .build();
        } catch (IOException e) {
            throw new RuntimeException("Failed to initialize Pub/Sub Publisher for alertTopicId: " + alertTopicId, e);
        }
    }
}
