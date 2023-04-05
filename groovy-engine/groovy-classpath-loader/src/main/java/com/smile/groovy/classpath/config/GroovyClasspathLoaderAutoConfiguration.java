package com.smile.groovy.classpath.config;

import com.smile.groovy.classpath.loader.ClasspathScriptLoader;
import com.smile.groovy.engine.core.compiler.DynamicCodeCompiler;
import com.smile.groovy.engine.core.filter.GroovyFileNameFilter;
import com.smile.groovy.engine.core.loader.ScriptLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FilenameFilter;

/**
 * @Description
 * @ClassName GroovyClasspathLoaderAutoConfiguration
 * @Author smile
 * @date 2023.04.05 11:09
 */
@Configuration
@EnableConfigurationProperties(value = {GroovyClasspathLoaderProperties.class})
@ConditionalOnProperty(prefix = GroovyClasspathLoaderProperties.PREFIX, value = "enable", havingValue = "true")
public class GroovyClasspathLoaderAutoConfiguration {

    private final Logger logger = LoggerFactory.getLogger(GroovyClasspathLoaderAutoConfiguration.class);

    @Bean
    public ScriptLoader classpathScriptLoader(DynamicCodeCompiler dynamicCodeCompiler,
                                              GroovyClasspathLoaderProperties classpathLoaderProperties) {
        logger.info("loading classpath ScriptLoader");
        return new ClasspathScriptLoader(dynamicCodeCompiler, classpathLoaderProperties, groovyFileNameFilter());
    }


    @Bean
    public FilenameFilter groovyFileNameFilter() {
        return new GroovyFileNameFilter();
    }
}
