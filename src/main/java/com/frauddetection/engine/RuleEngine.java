package com.frauddetection.engine;

import com.frauddetection.model.Transaction;

public class RuleEngine {

    public boolean isFraudulent(Transaction transaction) {
        // 单笔交易超过指定金额
        if (transaction.getAmount() > 10000) {
            return true;
        }
        // 来自已标记为“可疑”的账户的交易
        if ("suspiciousAccount1".equals(transaction.getAccountId())) {
            return true;
        }
        return false;
    }
}