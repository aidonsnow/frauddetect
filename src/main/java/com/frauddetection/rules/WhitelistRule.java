package com.frauddetection.rules;

import com.frauddetection.model.Transaction;

import java.util.Set;

public class WhitelistRule implements Rule {
    private final Set<String> whitelistAccounts;

    public WhitelistRule(Set<String> whitelistAccounts) {
        this.whitelistAccounts = whitelistAccounts;
    }

    @Override
    public boolean matches(Transaction transaction) {
        return transaction.getAccountId() != null && whitelistAccounts.contains(transaction.getAccountId());
    }

    @Override
    public String getName() {
        return "WhitelistRule";
    }

    @Override
    public int getPriority() {
        return 2;
    }
}
