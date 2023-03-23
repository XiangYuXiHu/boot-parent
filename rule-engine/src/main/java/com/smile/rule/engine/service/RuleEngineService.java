package com.smile.rule.engine.service;

import com.smile.rule.engine.domain.RuleEngine;
import com.smile.rule.engine.rule.RuleEngineLoader;
import com.smile.rule.engine.rule.RuleEngineManager;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description
 * @ClassName GroovyTemplateService
 * @Author smile
 * @date 2023.03.20 20:09
 */
@Service
public class RuleEngineService implements InitializingBean {

    @Autowired
    private List<RuleEngine> ruleEngineList;

    private static final int FRESH_INTERVAL = 5;

    @Override
    public void afterPropertiesSet() throws Exception {
        RuleEngineLoader.getInstance().setCertainRules(ruleEngineList);
        RuleEngineManager.refreshScript(FRESH_INTERVAL);
    }
}
