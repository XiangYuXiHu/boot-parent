package com.smile;

import com.smile.rule.engine.RuleEngineApplication;
import groovy.util.logging.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RuleEngineApplication.class)
public class RuleEngineApplicationTests {


    @Test
    public void contextLoads() {
    }
}
