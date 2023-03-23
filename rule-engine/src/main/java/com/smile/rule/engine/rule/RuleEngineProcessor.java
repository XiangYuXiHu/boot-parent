package com.smile.rule.engine.rule;

import com.smile.rule.engine.domain.RuleEngine;
import com.smile.rule.engine.domain.RuleEngineContext;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 业务规则处理器核心入口
 *
 * @Description
 * @ClassName RuleEngineProcessor
 * @Author smile
 * @date 2023.03.23 20:54
 */
@Component
public class RuleEngineProcessor {

    public void ruleEngine(RuleEngineContext context) {
        List<RuleEngine> ruleEngines = RuleEngineLoader.getInstance().getRuleEngine();
        if (!CollectionUtils.isEmpty(ruleEngines)) {
            ruleEngines.stream().forEach(ruleEngine -> {
                if (ruleEngine.isRun()) {
                    ruleEngine.run(context);
                }
            });
        }
    }
}
