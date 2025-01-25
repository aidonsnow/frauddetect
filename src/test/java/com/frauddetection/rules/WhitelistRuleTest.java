package com.frauddetection.rules;

import com.frauddetection.model.Transaction;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class WhitelistRuleTest {

    @Test
    void testWhitelistRuleMatches() {
        WhitelistRule rule = new WhitelistRule(Set.of("acc123", "acc456"));

        Transaction transaction = new Transaction();
        transaction.setTransactionId("txn1");
        transaction.setAccountId("acc123");

        assertTrue(rule.matches(transaction));
    }

    @Test
    void testWhitelistRuleDoesNotMatch() {
        WhitelistRule rule = new WhitelistRule(Set.of("acc123", "acc456"));

        Transaction transaction = new Transaction();
        transaction.setTransactionId("txn2");
        transaction.setAccountId("acc789");

        assertFalse(rule.matches(transaction));
    }

    @Test
    void testWhitelistRulePriority() {
        WhitelistRule rule = new WhitelistRule(Set.of("acc123", "acc456"));
        assertEquals(2, rule.getPriority());
    }
}
