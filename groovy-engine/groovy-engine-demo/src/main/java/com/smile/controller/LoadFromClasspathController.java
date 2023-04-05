package com.smile.controller;

import com.smile.groovy.engine.core.domain.EngineExecutorResult;
import com.smile.groovy.engine.core.domain.ExecuteParams;
import com.smile.groovy.engine.core.domain.ScriptQuery;
import com.smile.groovy.engine.core.executor.EngineExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description
 * @ClassName LoadFromClasspathController
 * @Author smile
 * @date 2023.04.05 15:31
 */
@Slf4j
@RestController
@RequestMapping("/v1/loadFromPath")
public class LoadFromClasspathController extends BaseController {

    @Autowired
    private EngineExecutor engineExecutor;

    /**
     * * 测试通过执行脚本run方法来执行脚本
     * * scriptName只要能唯一定位到脚本即可
     *
     * @param scriptName
     * @return
     */
    @GetMapping("changeOrder")
    public String changeOrderInfo(String scriptName) {
        ExecuteParams executeParams = buildOrderParams();
        EngineExecutorResult result = engineExecutor.execute(new ScriptQuery(scriptName), executeParams);
        String code = result.getExecutionStatus().getCode();
        if ("200".equalsIgnoreCase(code)) {
            log.info("脚本执行成功......");
        } else {
            log.info("脚本执行失败......");
        }
        log.info("使用groovy脚本来动态修改订单信息=========>>>>>>>>>>>执行结果：{}", result);
        return "success";
    }
}
