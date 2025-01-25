package com.frauddetection.rules;

import com.frauddetection.model.Transaction;

public interface Rule {
    boolean matches(Transaction transaction); // 判断是否匹配规则
    String getName();                        // 获取规则名称
    int getPriority();                       // 获取规则优先级
}
