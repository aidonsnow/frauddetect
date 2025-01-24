package com.frauddetection.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AwsSqsConfig {

    // 从 application.properties 中读取 AWS 访问密钥
    @Value("${cloud.aws.credentials.accessKey}")
    private String accessKey;

    // 从 application.properties 中读取 AWS 秘密访问密钥
    @Value("${cloud.aws.credentials.secretKey}")
    private String secretKey;

    // 从 application.properties 中读取 AWS 区域
    @Value("${cloud.aws.region.static}")
    private String region;

    // 定义 AmazonSQS Bean
    @Bean
    public AmazonSQS amazonSQS() {
        // 创建基本的 AWS 凭证对象
        BasicAWSCredentials awsCreds = new BasicAWSCredentials(accessKey, secretKey);
        // 使用凭证和区域信息构建 AmazonSQS 客户端
        return AmazonSQSClientBuilder.standard()
                .withRegion(region)
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                .build();
    }
}