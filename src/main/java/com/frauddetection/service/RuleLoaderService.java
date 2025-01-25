package com.frauddetection.service;

import com.frauddetection.rules.Rule;
import com.frauddetection.rules.ThresholdRule;
import com.frauddetection.rules.WhitelistRule;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Service
public class RuleLoaderService {

    public List<Rule> loadRules() {
        return Arrays.asList(
                new ThresholdRule(BigDecimal.valueOf(10000)),
                new WhitelistRule(Set.of("acc123", "acc456"))
        );
    }
}
