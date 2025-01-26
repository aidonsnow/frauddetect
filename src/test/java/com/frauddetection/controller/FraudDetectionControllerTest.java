package com.frauddetection.controller;

import com.frauddetection.model.Transaction;
import com.frauddetection.service.AlertService;
import com.frauddetection.service.FraudDetectionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class FraudDetectionControllerTest {

    private MockMvc mockMvc;
    private FraudDetectionService mockFraudDetectionService;
    private AlertService mockAlertService;

    @BeforeEach
    void setUp() {
        // 模拟 FraudDetectionService 和 AlertService
        mockFraudDetectionService = mock(FraudDetectionService.class);
        mockAlertService = mock(AlertService.class);

        // 初始化控制器并注入模拟对象
        FraudDetectionController controller = new FraudDetectionController(mockFraudDetectionService, mockAlertService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void testCheckFraud_ValidTransaction() throws Exception {
        // 模拟 FraudDetectionService 的行为
        when(mockFraudDetectionService.detectFraud(any(Transaction.class))).thenReturn("Transaction is safe");

        // 执行请求并验证结果
        mockMvc.perform(post("/fraud/check")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"transactionId\":\"txn1\",\"accountId\":\"acc123\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Transaction is safe"));

        // 验证服务调用
        verify(mockFraudDetectionService, times(1)).detectFraud(any(Transaction.class));
    }
/*
    @Test
    void testCheckFraud_ServiceError() throws Exception {
        // 模拟服务层抛出异常
        when(mockFraudDetectionService.detectFraud(any(Transaction.class)))
                .thenThrow(new RuntimeException("Internal Server Error"));

        // 执行请求并验证结果
        mockMvc.perform(post("/fraud/check")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"transactionId\":\"txn1\",\"accountId\":\"acc123\"}"))
                .andExpect(status().isInternalServerError());
    }*/
}
