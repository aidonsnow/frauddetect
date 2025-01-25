package com.frauddetection.controller;

import com.frauddetection.model.Transaction;
import com.frauddetection.service.FraudDetectionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class FraudDetectionControllerTest {

    private MockMvc mockMvc;

    @Mock
    private FraudDetectionService fraudDetectionService;

    @InjectMocks
    private FraudDetectionController fraudDetectionController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // 初始化 Mockito
        mockMvc = MockMvcBuilders.standaloneSetup(fraudDetectionController).build();
    }

    @Test
    void testCheckFraud_FraudulentTransaction() throws Exception {
        // Mock 服务行为
        when(fraudDetectionService.detectFraud(any(Transaction.class))).thenReturn(true);

        // 执行测试
        mockMvc.perform(post("/fraud/check")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"transactionId\":\"txn123\",\"accountId\":\"acc456\",\"amount\":15000.00}")) // 确保 JSON 格式一致
                .andExpect(status().isOk())
                .andExpect(content().string("Fraudulent Transaction"));
    }

    @Test
    void testCheckFraud_LegitimateTransaction() throws Exception {
        // Mock 服务行为
        when(fraudDetectionService.detectFraud(any(Transaction.class))).thenReturn(false);

        // 执行测试
        mockMvc.perform(post("/fraud/check")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"transactionId\":\"txn456\",\"accountId\":\"acc789\",\"amount\":5000.00}")) // 确保 JSON 格式一致
                .andExpect(status().isOk())
                .andExpect(content().string("Legitimate Transaction"));
    }
}
