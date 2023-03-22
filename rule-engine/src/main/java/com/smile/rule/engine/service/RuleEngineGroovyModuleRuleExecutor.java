package com.smile.rule.engine.service;

import com.smile.rule.engine.domain.RuleEngineGroovyModule;
import com.smile.rule.engine.domain.RuleEngineGroovyExecutor;
import groovy.lang.GroovyClassLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description
 * @ClassName RuleEngineGroovyModuleRuleExecutor
 * @Author smile
 * @date 2023.03.22 15:13
 */
@Component
public class RuleEngineGroovyModuleRuleExecutor implements RuleEngineGroovyExecutor<RuleEngineGroovyModule> {

    private Map<String, Class<RuleEngineGroovyModule>> scriptAndClazz = new HashMap<>();

    @Autowired
    private GroovyTemplateService groovyTemplateService;

    @Override
    public RuleEngineGroovyModule getInstance(String name) {
        try {
            Class<RuleEngineGroovyModule> engineGroovyModuleRuleClass = scriptAndClazz.get(name);
            if (engineGroovyModuleRuleClass == null) {
                throw new IllegalArgumentException(String.format("script: %s not load", name));
            }
            return engineGroovyModuleRuleClass.newInstance();
        } catch (Exception e) {
            throw new RuntimeException("创建module rule异常");
        }
    }

    @Override
    public void parseAndCache(String name, String script) {
        String scriptTemplate = groovyTemplateService.getScript("ScriptTemplate.groovy_template");
        String clazzName = RuleEngineGroovyModuleRuleExecutor.class.getSimpleName() + "_" + name;
        String fullScript = String.format(scriptTemplate, clazzName, script);
        GroovyClassLoader loader = new GroovyClassLoader();
        Class aClass = loader.parseClass(fullScript);

        scriptAndClazz.put(name, aClass);
    }
}
