package com.smile.rule.engine.service;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description
 * @ClassName GroovyTemplateService
 * @Author smile
 * @date 2023.03.20 20:09
 */
@Service
public class GroovyTemplateService implements InitializingBean {

    private static final Map<String, String> SCRIPT_TEMPLATE_MAP = new HashMap<>();

    public String getScript(String fileName) {
        String template = SCRIPT_TEMPLATE_MAP.get(fileName);
        if (StringUtils.isEmpty(template)) {
            String errorMessage = String.format("请添加脚本文件%s到resources目录下", template);
            throw new RuntimeException(errorMessage);
        }
        return template;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        String path = "classpath*:*.groovy_template";
        PathMatchingResourcePatternResolver pathResolver = new PathMatchingResourcePatternResolver();
        Arrays.stream(pathResolver.getResources(path)).parallel().forEach(resource -> {
            try {
                String filename = resource.getFilename();
                BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()));
                StringBuilder builder = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    builder.append(line).append("\n");
                }
                SCRIPT_TEMPLATE_MAP.put(filename, builder.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
