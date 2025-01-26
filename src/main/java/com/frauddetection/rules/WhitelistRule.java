package com.frauddetection.rules;

import com.frauddetection.model.Transaction;

import java.util.Set;

public class WhitelistRule implements Rule {
    private final Set<String> whitelistedAccounts;
    private int priority = 0; // 默认优先级

    public WhitelistRule(Set<String> whitelistedAccounts) {
        if (whitelistedAccounts == null || whitelistedAccounts.isEmpty()) {
            throw new IllegalArgumentException("Whitelist cannot be null or empty");
        }
        this.whitelistedAccounts = whitelistedAccounts;
    }

    @Override
    public boolean matches(Transaction transaction) {
        if (transaction == null || transaction.getAccountId() == null) {
            return false; // 如果交易或账户ID为空，则不匹配
        }
        return whitelistedAccounts.contains(transaction.getAccountId());
    }

    @Override
    public String getName() {
        return "WhitelistRule";
    }

    @Override
    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
