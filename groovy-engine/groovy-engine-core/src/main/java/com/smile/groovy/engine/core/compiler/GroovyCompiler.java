package com.smile.groovy.engine.core.compiler;

import com.smile.groovy.engine.core.domain.ScriptEntry;
import groovy.lang.GroovyClassLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description
 * @ClassName GroovyCompiler
 * @Author smile
 * @date 2023.04.01 23:25
 */
public class GroovyCompiler implements DynamicCodeCompiler {
    private Logger logger = LoggerFactory.getLogger(GroovyClassLoader.class);

    @Override
    public Class<?> compile(String code, String name) throws Exception {
        GroovyClassLoader classLoader = getGroovyClassLoader();
        logger.warn("compiling file {}", name);
        return classLoader.parseClass(code, name);
    }

    @Override
    public Class<?> compile(ScriptEntry scriptEntry) throws Exception {
        GroovyClassLoader classLoader = getGroovyClassLoader();
        return classLoader.parseClass(scriptEntry.getScriptContent(), scriptEntry.getName());
    }

    /**
     * 为什么要New 一个class loader呢？这个就要从Class对象垃圾回收说起，一个Class要被回收必须同时满足以下三个条件：
     * <li>该Class 的所有实例都已经被回收</li>
     * <li>加载该类的classLoader已经被回收</li>
     * <li>该Class 没有被引用</li>
     * 通过使用 new 一个classLoader 来加载动态脚本就是为了解决动态类回收问题，因为classLoader可以提前被回收，
     * 但是在目前版本的Groovy中，使用同一个GroovyClassLoader来加载脚本Class到方法区，该Class也可以在GroovyClassLoader之前被卸载，
     * 原因是：并不是真正由同一个GroovyClassLoader对象来加载的Class，而是每次GroovyClassLoader加载Class时都会创建一个类型为
     * {@link GroovyClassLoader.InnerLoader}的类加载器来加载Class，所以实质上还是每次加载都使用新的ClassLoader
     * </p>
     *
     * @return a new GroovyClassLoader
     */
    public GroovyClassLoader getGroovyClassLoader() {
        return new GroovyClassLoader();
    }
}
