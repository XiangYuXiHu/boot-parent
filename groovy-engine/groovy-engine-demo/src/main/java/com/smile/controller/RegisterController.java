package com.smile.controller;

import com.smile.groovy.engine.core.helper.RegisterScriptHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Description
 * @ClassName RegisterController
 * @Author smile
 * @date 2023.04.05 21:20
 */
@RestController
@RequestMapping("/v1/register")
public class RegisterController {

    @Autowired
    private RegisterScriptHelper registerScriptHelper;

    /**
     * 新增脚本
     *
     * @param scriptName
     * @param scriptContent
     * @return
     * @throws Exception
     */
    @PostMapping("script")
    public String addScript(String scriptName, String scriptContent) throws Exception {
        boolean isSuccess = registerScriptHelper.registerScript(scriptName, scriptContent, true);
        return String.valueOf(isSuccess);
    }
}
