package com.smile.groovy.engine.core.filter;

import java.io.File;
import java.io.FilenameFilter;

/**
 * @Description
 * @ClassName GroovyFileNameFilter
 * @Author smile
 * @date 2023.04.04 23:00
 */
public class GroovyFileNameFilter implements FilenameFilter {
    @Override
    public boolean accept(File dir, String name) {
        return name.endsWith(".groovy");
    }
}
