package com.frauddetection.rules;

import com.frauddetection.model.Transaction;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RuleEngineTest {

    @Test
    void testRuleEngineExecutesMatchingRule() {
        RuleEngine ruleEngine = new RuleEngine();
        ruleEngine.registerRule(new ThresholdRule(BigDecimal.valueOf(10000)));
        ruleEngine.registerRule(new WhitelistRule(Set.of("acc123")));

        // 测试匹配白名单规则
        Transaction transaction1 = new Transaction();
        transaction1.setTransactionId("txn1");
        transaction1.setAccountId("acc123");
        transaction1.setAmount(BigDecimal.valueOf(5000));
        assertEquals("WhitelistRule", ruleEngine.executeRules(transaction1));

        // 测试匹配金额规则
        Transaction transaction2 = new Transaction();
        transaction2.setTransactionId("txn2");
        transaction2.setAccountId("acc789");
        transaction2.setAmount(BigDecimal.valueOf(15000));
        assertEquals("ThresholdRule", ruleEngine.executeRules(transaction2));

        // 测试无匹配规则
        Transaction transaction3 = new Transaction();
        transaction3.setTransactionId("txn3");
        transaction3.setAccountId("acc789");
        transaction3.setAmount(BigDecimal.valueOf(5000));
        assertEquals(null, ruleEngine.executeRules(transaction3));
    }
}
