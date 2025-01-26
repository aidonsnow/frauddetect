package com.frauddetection.rules;

import com.frauddetection.model.Transaction;

import java.math.BigDecimal;

public class ThresholdRule implements Rule {
    private final BigDecimal threshold;
    private int priority = 0; // 默认优先级

    public ThresholdRule(BigDecimal threshold) {
        this.threshold = threshold;
    }

    @Override
    public boolean matches(Transaction transaction) {
        return transaction.getAmount().compareTo(threshold) > 0;
    }

    @Override
    public String getName() {
        return "ThresholdRule";
    }

    @Override
    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
