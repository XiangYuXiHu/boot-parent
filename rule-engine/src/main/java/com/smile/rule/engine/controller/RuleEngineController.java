package com.smile.rule.engine.controller;

import com.smile.rule.engine.rule.RuleEngineProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description
 * @ClassName RuleEngineController
 * @Author smile
 * @date 2023.03.23 22:13
 */
@RestController
@RequestMapping("rule")
public class RuleEngineController {

    @Autowired
    private RuleEngineProcessor ruleEngineProcessor;

    @GetMapping("engine")
    public void ruleEngine() {
        ruleEngineProcessor.ruleEngine(null);
    }
}
