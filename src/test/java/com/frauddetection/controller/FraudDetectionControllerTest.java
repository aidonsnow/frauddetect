package com.frauddetection.controller;

import com.frauddetection.rules.Rule;
import com.frauddetection.rules.ThresholdRule;
import com.frauddetection.rules.WhitelistRule;
import com.frauddetection.service.RuleLoaderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class FraudDetectionControllerTest {

    private MockMvc mockMvc;

    private FraudDetectionController fraudDetectionController;

    @BeforeEach
    void setUp() {
        // 手动实例化 RuleLoaderService
        RuleLoaderService ruleLoaderService = new RuleLoaderService() {
            @Override
            public List<Rule> loadRules() {
                return List.of(
                        new WhitelistRule(Set.of("acc123", "acc456")), // 优先级高
                        new ThresholdRule(BigDecimal.valueOf(10000)) // 优先级低
                );
            }
        };

        // 手动实例化 FraudDetectionController
        fraudDetectionController = new FraudDetectionController(ruleLoaderService);

        // 初始化 MockMvc
        mockMvc = MockMvcBuilders.standaloneSetup(fraudDetectionController).build();
    }

    @Test
    void testCheckFraud_FraudulentTransaction() throws Exception {
        mockMvc.perform(post("/fraud/check")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"transactionId\":\"txn1\",\"accountId\":\"acc123\",\"amount\":5000.00}")) // 修改金额，确保优先匹配 WhitelistRule
                .andExpect(status().isOk())
                .andExpect(content().string("Fraudulent Transaction (Rule: WhitelistRule)"));
    }

    @Test
    void testCheckFraud_LegitimateTransaction() throws Exception {
        mockMvc.perform(post("/fraud/check")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"transactionId\":\"txn2\",\"accountId\":\"acc789\",\"amount\":5000.00}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Legitimate Transaction"));
    }
}
