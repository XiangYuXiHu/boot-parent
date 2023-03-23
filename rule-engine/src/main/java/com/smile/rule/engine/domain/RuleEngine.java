package com.smile.rule.engine.domain;

/**
 * 规则过滤器抽象类，用于扩展实现业务规则，供Java和Groovy继承
 *
 * @author 12780
 */
public abstract class RuleEngine implements Comparable<RuleEngine> {

    /**
     * 执行
     *
     * @param context
     * @return
     */
    public abstract boolean run(RuleEngineContext context);

    /**
     * 规则是否被执行
     *
     * @return
     */
    public abstract boolean isRun();

    /**
     * 执行顺序
     *
     * @return
     */
    public abstract int runOrder();

    /**
     * 排序
     *
     * @param o
     * @return
     */
    @Override
    public int compareTo(RuleEngine o) {
        return Integer.compare(runOrder(), o.runOrder());
    }
}
