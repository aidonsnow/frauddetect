package com.frauddetection.service;

import com.frauddetection.rules.Rule;
import com.frauddetection.rules.RuleEngine;
import com.frauddetection.rules.ThresholdRule;
import com.frauddetection.rules.WhitelistRule;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Service
public class RuleLoaderService {

    private final RuleEngine ruleEngine = new RuleEngine();

    // 加载规则库
    public List<Rule> loadRules() {
        return Arrays.asList(
                new ThresholdRule(BigDecimal.valueOf(10000)),
                new WhitelistRule(Set.of("acc123", "acc456"))
        );
    }

    // 项目启动时注册规则到 RuleEngine
    @PostConstruct
    public void initializeRules() {
        List<Rule> rules = loadRules();
        rules.forEach(ruleEngine::registerRule);
        System.out.println("Rules successfully loaded into RuleEngine: " + ruleEngine.getRules());
    }

    // 提供 RuleEngine 的访问接口
    public RuleEngine getRuleEngine() {
        return ruleEngine;
    }
}
