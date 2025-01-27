package com.frauddetection.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;

public class Transaction {
    private String transactionId;
    private String accountId;
    private BigDecimal amount;

    // Getters and Setters
    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    // 使用 Jackson 序列化对象为 JSON 字符串
    @Override
    public String toString() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // 将对象转换为 JSON 字符串
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "{}";  // 如果转换失败，返回一个空的 JSON 对象
        }
    }
}
