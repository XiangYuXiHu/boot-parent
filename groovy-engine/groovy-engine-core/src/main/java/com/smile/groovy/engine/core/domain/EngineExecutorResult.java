package com.smile.groovy.engine.core.domain;


import com.smile.groovy.engine.core.constants.ExecutionStatus;

/**
 * 脚本执行结果
 *
 * @author smile
 */
public class EngineExecutorResult {

    /**
     * 执行状态
     */
    private ExecutionStatus executionStatus;

    /**
     * 返回内容
     */
    private Object context;

    /**
     * 异常信息
     */
    private Throwable exception;

    /**
     * 自定义异常描述
     */
    private String errorMessage;

    /**
     * 获取context为指定的类型
     */
    public <T> T context() {
        return (T) context;
    }

    private EngineExecutorResult(ExecutionStatus executionStatus, String errorMessage) {
        this.executionStatus = executionStatus;
        this.errorMessage = errorMessage;
    }

    private EngineExecutorResult(ExecutionStatus executionStatus) {
        this.executionStatus = executionStatus;
    }

    private EngineExecutorResult(ExecutionStatus executionStatus, Throwable exception) {
        this.executionStatus = executionStatus;
        this.exception = exception;
    }

    private EngineExecutorResult(ExecutionStatus executionStatus, Object context) {
        this.executionStatus = executionStatus;
        this.context = context;
    }

    /**
     * 执行失败
     *
     * @param exception 异常信息
     * @return EngineExecutorResult<java.lang.Object>
     */
    public static EngineExecutorResult failed(Throwable exception) {
        return new EngineExecutorResult(ExecutionStatus.FAILED, exception);
    }

    /**
     * 执行失败
     *
     * @param errorMessage 异常信息
     * @return EngineExecutorResult<java.lang.Object>
     */
    public static EngineExecutorResult failed(String errorMessage) {
        return new EngineExecutorResult(ExecutionStatus.PARAM_ERROR, errorMessage);
    }

    /**
     * 执行成功
     *
     * @param context 内容
     * @return EngineExecutorResult<java.lang.Object>
     */
    public static EngineExecutorResult success(Object context) {
        return success(ExecutionStatus.SUCCESS, context);
    }

    /**
     * 执行成功
     *
     * @param context 内容
     * @param status  执行状态
     * @return EngineExecutorResult<java.lang.Object>
     */
    public static EngineExecutorResult success(ExecutionStatus status, Object context) {
        return new EngineExecutorResult(status, context);
    }

    public ExecutionStatus getExecutionStatus() {
        return executionStatus;
    }

    public void setExecutionStatus(ExecutionStatus executionStatus) {
        this.executionStatus = executionStatus;
    }

    public Object getContext() {
        return context;
    }

    public void setContext(Object context) {
        this.context = context;
    }

    public Throwable getException() {
        return exception;
    }

    public void setException(Throwable exception) {
        this.exception = exception;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        return "EngineExecutorResult{" +
                "executionStatus=" + executionStatus +
                ", context=" + context +
                ", exception=" + exception +
                ", errorMessage='" + errorMessage + '\'' +
                '}';
    }
}
