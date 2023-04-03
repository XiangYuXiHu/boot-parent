package com.smile.groovy.engine.core.domain;

/**
 * @Description
 * @ClassName ScriptQuery
 * @Author smile
 * @date 2023.04.01 19:33
 */
public class ScriptQuery {

    private String uniqueKey;

    public ScriptQuery(String uniqueKey) {
        this.uniqueKey = uniqueKey;
    }

    public String getUniqueKey() {
        return uniqueKey;
    }

    public void setUniqueKey(String uniqueKey) {
        this.uniqueKey = uniqueKey;
    }

    @Override
    public String toString() {
        return "ScriptQuery{" +
                "uniqueKey='" + uniqueKey + '\'' +
                '}';
    }
}
