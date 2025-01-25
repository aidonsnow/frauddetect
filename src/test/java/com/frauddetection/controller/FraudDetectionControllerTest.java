package com.frauddetection.controller;

import com.frauddetection.model.Transaction;
import com.frauddetection.rules.Rule;
import com.frauddetection.service.FraudDetectionService;
import com.frauddetection.service.RuleLoaderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class FraudDetectionControllerTest {

    private MockMvc mockMvc;

    @Mock
    private RuleLoaderService ruleLoaderService;

    private FraudDetectionController fraudDetectionController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // 模拟 RuleLoaderService 的行为
        when(ruleLoaderService.loadRules()).thenReturn(List.of());

        // 手动实例化 FraudDetectionController
        fraudDetectionController = new FraudDetectionController(ruleLoaderService);

        // 构建 MockMvc
        mockMvc = MockMvcBuilders.standaloneSetup(fraudDetectionController).build();
    }

    @Test
    void testCheckFraud_FraudulentTransaction() throws Exception {
        when(ruleLoaderService.loadRules()).thenReturn(List.of()); // Mock 规则加载

        mockMvc.perform(post("/fraud/check")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"transactionId\":\"txn1\",\"accountId\":\"acc123\",\"amount\":15000.00}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Legitimate Transaction")); // 无规则匹配
    }
}
