package com.frauddetection.config;

import com.google.auth.oauth2.GoogleCredentials;
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

    @Bean
    public GoogleCredentials googleCredentials() throws IOException {
        // 解码 Base64 内容并加载为 GoogleCredentials
        byte[] decodedKey = Base64.getDecoder().decode(serviceAccountKeyBase64);
        return GoogleCredentials.fromStream(new ByteArrayInputStream(decodedKey));
    }
}
