package com.frauddetection;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import com.frauddetection.service.FraudDetectionService;

@SpringBootApplication
@ComponentScan(basePackages = "com.frauddetection")
public class FraudDetectionApplication {

    public static void main(String[] args) {
        SpringApplication.run(FraudDetectionApplication.class, args);
    }

    @Bean
    CommandLineRunner init(FraudDetectionService fraudDetectionService) {
        return args -> {
            fraudDetectionService.startDetection();
        };
    }
}