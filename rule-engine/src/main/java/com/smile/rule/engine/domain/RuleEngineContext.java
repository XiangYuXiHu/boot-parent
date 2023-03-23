package com.smile.rule.engine.domain;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 执行引擎上下文
 *
 * @Description
 * @ClassName RuleEngineExecutorContext
 * @Author smile
 * @date 2023.03.20 19:52
 */
public class RuleEngineContext implements Serializable {

    private String bizId;

    private String nextScenario;

    /**
     *
     */
    private Map<String, Object> data = new HashMap<>();

    public String getBizId() {
        return bizId;
    }

    public void setBizId(String bizId) {
        this.bizId = bizId;
    }

    public String getNextScenario() {
        return nextScenario;
    }

    public void setNextScenario(String nextScenario) {
        this.nextScenario = nextScenario;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }
}
