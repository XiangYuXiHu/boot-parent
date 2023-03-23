package com.smile.rule.engine.rule;

import java.util.concurrent.TimeUnit;

/**
 * 一个独立线程轮询监听指定目录文件的变化配合RuleFilterLoader （ 规则过滤器加载器）使用
 *
 * @Description
 * @ClassName RuleEngineManager
 * @Author smile
 * @date 2023.03.23 20:52
 */
public class RuleEngineManager {

    public static volatile boolean isCancel = false;

    public static void refreshScript(int intervalSeconds) {
        Thread thread = new Thread("refreshGroovyScript") {
            @Override
            public void run() {
                while (!isCancel) {
                    try {
                        RuleEngineLoader.getInstance().refreshRule();
                        TimeUnit.SECONDS.sleep(intervalSeconds);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        thread.setDaemon(true);
        thread.start();
    }

    public static void cancel() {
        isCancel = true;
    }
}
