package com.smile.rule.engine.rule;

import com.smile.rule.engine.domain.RuleEngine;
import groovy.lang.GroovyClassLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.util.CollectionUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Modifier;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 规则过滤器加载器，用于加载基于Spring的RuleEngine实现类和动态编译指定文件基于Groovy的RuleEngine实现类。
 *
 * @Description
 * @ClassName RuleEngineLoader
 * @Author smile
 * @date 2023.03.23 20:51
 */
public class RuleEngineLoader {
    private static volatile RuleEngineLoader instance;
    private List<RuleEngine> certainRules;
    private Map<String, RuleEngine> ruleRegistry = new HashMap<>();
    private List<RuleEngine> allRules = new ArrayList<>();
    private Map<String, Long> fileLastModified = new HashMap<>();


    private RuleEngineLoader() {
    }

    public void refreshRule() throws IOException {
        String path = "classpath*:*.groovy_template";
        PathMatchingResourcePatternResolver pathResolver = new PathMatchingResourcePatternResolver();
        Arrays.stream(pathResolver.getResources(path)).forEach(resource -> {
            try {
                String filename = resource.getFilename();
                Long lastModified = fileLastModified.get(filename);
                File file = resource.getFile();
                long modified = file.lastModified();
                if (lastModified != null && modified != lastModified) {
                    System.out.println("reload script " + filename);
                    ruleRegistry.remove(filename);
                }
                RuleEngine ruleEngine = ruleRegistry.get(filename);
                if (null == ruleEngine) {
                    System.out.println("load script " + filename);
                    GroovyClassLoader classLoader = new GroovyClassLoader();
                    Class aClass = classLoader.parseClass(file);
                    if (!Modifier.isAbstract(aClass.getModifiers())) {
                        ruleEngine = (RuleEngine) aClass.newInstance();
                        ruleRegistry.put(filename, ruleEngine);
                        allRules.clear();
                        fileLastModified.put(filename, modified);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }

    public List<RuleEngine> getRuleEngine() {
        if (!CollectionUtils.isEmpty(allRules)) {
            return allRules;
        }
        allRules.addAll(certainRules);
        allRules.addAll(ruleRegistry.values());
        Collections.sort(allRules);
        return allRules;
    }

    public static RuleEngineLoader getInstance() {
        if (instance == null) {
            synchronized (RuleEngineLoader.class) {
                if (instance == null) {
                    instance = new RuleEngineLoader();
                }
            }
        }
        return instance;
    }

    public void setCertainRules(List<RuleEngine> certainRules) {
        this.certainRules = certainRules;
    }
}
