package com.smile.groovy.engine.core.constants;

/**
 * 执行状态
 *
 * @author smile
 */
public enum ExecutionStatus {
    FAILED("500", "执行失败"),
    SUCCESS("200", "执行成功"),
    NO_SCRIPT("4004", "没有找到groovy脚本"),
    PARAM_ERROR("4003", "参数有误");;
    private String code;
    private String desc;

    ExecutionStatus(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
