package com.frauddetection.rules;

import com.frauddetection.model.Transaction;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ThresholdRuleTest {

    @Test
    void testThresholdRuleMatches() {
        ThresholdRule rule = new ThresholdRule(BigDecimal.valueOf(10000));

        Transaction transaction = new Transaction();
        transaction.setTransactionId("txn1");
        transaction.setAccountId("acc123");
        transaction.setAmount(BigDecimal.valueOf(15000));

        assertTrue(rule.matches(transaction));
    }

    @Test
    void testThresholdRuleDoesNotMatch() {
        ThresholdRule rule = new ThresholdRule(BigDecimal.valueOf(10000));

        Transaction transaction = new Transaction();
        transaction.setTransactionId("txn2");
        transaction.setAccountId("acc456");
        transaction.setAmount(BigDecimal.valueOf(5000));

        assertFalse(rule.matches(transaction));
    }

    @Test
    void testThresholdRulePriority() {
        ThresholdRule rule = new ThresholdRule(BigDecimal.valueOf(10000));
        assertEquals(1, rule.getPriority());
    }
}
