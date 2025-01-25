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
     * @throws IOException 如果加载凭据失败
     */
    @Bean
    public GoogleCredentials googleCredentials() throws IOException {
        // 解码 Base64 内容并加载为 GoogleCredentials
        byte[] decodedKey = Base64.getDecoder().decode(serviceAccountKeyBase64);
        return GoogleCredentials.fromStream(new ByteArrayInputStream(decodedKey));
    }



}
