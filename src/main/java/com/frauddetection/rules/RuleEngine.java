package com.frauddetection.rules;

import com.frauddetection.model.Transaction;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class RuleEngine {
    private final List<Rule> rules = new ArrayList<>();

    public void registerRule(Rule rule) {
        rules.add(rule);
        rules.sort(Comparator.comparingInt(Rule::getPriority)); // 按优先级排序
    }

    public String executeRules(Transaction transaction) {
        for (Rule rule : rules) {
            if (rule.matches(transaction)) {
                return rule.getName(); // 返回匹配规则的名称
            }
        }
        return null; // 无规则匹配
    }
}
