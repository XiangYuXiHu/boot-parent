package com.smile.rule.engine.rule;

import com.smile.rule.engine.domain.RuleEngineContext;
import com.smile.rule.engine.domain.RuleEngine;
import org.springframework.stereotype.Component;

/**
 * @Description
 * @ClassName CertainRule
 * @Author smile
 * @date 2023.03.23 20:13
 */
@Component
public class CertainRule extends RuleEngine {

    @Override
    public boolean run(RuleEngineContext context) {
        System.out.println("-----不变规则java实现----");
        return false;
    }

    @Override
    public boolean isRun() {
        return true;
    }

    @Override
    public int runOrder() {
        return 2;
    }
}
