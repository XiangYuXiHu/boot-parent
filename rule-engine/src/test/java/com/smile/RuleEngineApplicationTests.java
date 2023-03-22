package com.smile;

import com.smile.rule.engine.RuleEngineApplication;
import com.smile.rule.engine.domain.RuleEngineGroovyModule;
import com.smile.rule.engine.domain.RuleEngineExecuteContext;
import com.smile.rule.engine.service.RuleEngineGroovyModuleRuleExecutor;
import groovy.util.logging.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RuleEngineApplication.class)
public class RuleEngineApplicationTests {

    @Autowired
    private RuleEngineGroovyModuleRuleExecutor ruleExecutor;

    @Test
    public void contextLoads() {
    }

    @Test
    public void test() {
        String name = "testGroovy";
        String strategyLogicUnit = "if(context.getData().get('amount')>=20000){\n" +
                "            context.nextScenario='A'\n" +
                "            return true\n" +
                "        }";
        ruleExecutor.parseAndCache(name, strategyLogicUnit);
        RuleEngineGroovyModule instance = ruleExecutor.getInstance(name);
        RuleEngineExecuteContext executeContext = new RuleEngineExecuteContext();
        executeContext.getData().put("amount", 30000);
        Boolean result = instance.run(executeContext);
        System.out.println(result);
    }
}
