package com.frauddetection.rules;

import com.frauddetection.model.Transaction;

import java.math.BigDecimal;

public class ThresholdRule implements Rule {
    private final BigDecimal threshold;

    public ThresholdRule(BigDecimal threshold) {
        this.threshold = threshold;
    }

    @Override
    public boolean matches(Transaction transaction) {
        return transaction.getAmount() != null && transaction.getAmount().compareTo(threshold) > 0;
    }

    @Override
    public String getName() {
        return "ThresholdRule";
    }

    @Override
    public int getPriority() {
        return 1;
    }
}
