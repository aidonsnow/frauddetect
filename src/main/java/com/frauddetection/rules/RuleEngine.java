package com.frauddetection.rules;

import com.frauddetection.model.Transaction;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Component // 使其成为 Spring 管理的 Bean
public class RuleEngine {
    private final List<Rule> rules = new ArrayList<>();

    public void registerRule(Rule rule) {
        rules.add(rule);
        rules.sort(Comparator.comparingInt(Rule::getPriority)); // 按优先级排序
    }

    public List<Rule> getRules() {
        return rules;
    }

    public String executeRules(Transaction transaction) {
        for (Rule rule : rules) {
            if (rule.matches(transaction)) {
                return rule.getName();
            }
        }
        return null; // 没有匹配的规则
    }
}
