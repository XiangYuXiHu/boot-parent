package com.smile.groovy.classpath.loader;

import com.google.common.collect.Lists;
import com.smile.groovy.classpath.config.GroovyClasspathLoaderProperties;
import com.smile.groovy.engine.core.compiler.DynamicCodeCompiler;
import com.smile.groovy.engine.core.domain.ScriptEntry;
import com.smile.groovy.engine.core.domain.ScriptQuery;
import com.smile.groovy.engine.core.exception.LoadScriptException;
import com.smile.groovy.engine.core.loader.ScriptLoader;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.CollectionUtils;
import org.springframework.util.DigestUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

/**
 * @Description
 * @ClassName ClasspathScriptLoader
 * @Author smile
 * @date 2023.04.05 11:18
 */
public class ClasspathScriptLoader implements ScriptLoader {

    private Logger logger = LoggerFactory.getLogger(ClasspathScriptLoader.class);

    private final DynamicCodeCompiler dynamicCodeCompiler;
    private final GroovyClasspathLoaderProperties groovyClasspathLoaderProperties;
    private final FilenameFilter filenameFilter;

    public ClasspathScriptLoader(DynamicCodeCompiler dynamicCodeCompiler,
                                 GroovyClasspathLoaderProperties groovyClasspathLoaderProperties,
                                 FilenameFilter filenameFilter) {
        this.dynamicCodeCompiler = dynamicCodeCompiler;
        this.groovyClasspathLoaderProperties = groovyClasspathLoaderProperties;
        this.filenameFilter = filenameFilter;
    }

    @Override
    public ScriptEntry load(ScriptQuery query) throws Exception {
        String uniqueKey = query.getUniqueKey();
        ClassPathResource classPathResource = new ClassPathResource(uniqueKey);
        if (!classPathResource.isFile()) {
            throw new LoadScriptException(String.format("[%s] is not a groovy file, please check!", uniqueKey));
        }
        String fileContext = readFileAsString(classPathResource.getFile());
        String fingerprint = DigestUtils.md5DigestAsHex(fileContext.getBytes());
        Class<?> aClass = dynamicCodeCompiler.compile(fileContext, uniqueKey);
        ScriptEntry scriptEntry = new ScriptEntry(classPathResource.getFilename(), fileContext, fingerprint, System.currentTimeMillis());
        scriptEntry.setClazz(aClass);
        return scriptEntry;
    }

    @Override
    public List<ScriptEntry> load() throws Exception {
        List<ScriptEntry> scriptEntries = Lists.newArrayList();
        List<File> fileList = loadClassPathFile(groovyClasspathLoaderProperties.getDirectory(), filenameFilter);
        if (CollectionUtils.isEmpty(fileList)) {
            logger.warn("can not found groovy script from directory [{}]", groovyClasspathLoaderProperties.getDirectory());
            return scriptEntries;
        }

        for (File file : fileList) {
            String fileContext = readFileAsString(file);
            if (StringUtils.isBlank(fileContext)) {
                logger.error("note can not found script content by fileName [{}], because file content is empty.", file.getName());
                continue;
            }
            String fingerprint = DigestUtils.md5DigestAsHex(fileContext.getBytes());
            ScriptEntry scriptEntry = new ScriptEntry(file.getName(), fileContext, fingerprint, System.currentTimeMillis());
            scriptEntries.add(scriptEntry);
        }
        return scriptEntries;
    }

    /**
     * 读取文件内容为string
     *
     * @param file
     * @return
     */
    private String readFileAsString(File file) throws IOException {
        try (FileInputStream in = new FileInputStream(file)) {
            int size = in.available();
            byte[] buf = new byte[size];
            in.read(buf);
            return new String(buf, StandardCharsets.UTF_8);
        }
    }

    private List<File> loadClassPathFile(String directory, FilenameFilter filenameFilter) throws IOException {
        List<File> result = Lists.newArrayList();
        if (StringUtils.isBlank(directory)) {
            return result;
        }

        ClassPathResource classPathResource = new ClassPathResource(directory);
        File file = classPathResource.getFile();
        if (file.isFile()) {
            result.add(file);
        }
        if (file.isDirectory()) {
            File[] listFiles = file.listFiles(filenameFilter);
            if (null != listFiles) {
                result.addAll(Arrays.asList(listFiles));
            }
        }
        return result;
    }
}
