package com.smile.groovy.engine.core.compiler;

import com.smile.groovy.engine.core.domain.ScriptEntry;

/**
 * 动态代码编译器
 *
 * @author smile
 */
public interface DynamicCodeCompiler {

    /**
     * 将code文本编译为Class对象
     *
     * @param code
     * @param name
     * @return
     * @throws Exception
     */
    Class<?> compile(String code, String name) throws Exception;

    /**
     * 将scriptEngineEntry编译为Class
     *
     * @param scriptEntry
     * @return
     * @throws Exception
     */
    Class<?> compile(ScriptEntry scriptEntry) throws Exception;
}
