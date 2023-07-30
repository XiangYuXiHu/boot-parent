package com.smile.storage.controller;

import com.simle.common.base.ResultBean;
import com.smile.storage.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description
 * @ClassName StorageController
 * @Author smile
 * @date 2022.04.09 14:08
 */
@RequestMapping("/storage")
@RestController
public class StorageController {

    @Autowired
    private StorageService storageService;


    /**
     * 扣减库存
     *
     * @param productId
     * @param count
     * @return
     */
    @GetMapping(value = "decrease")
    public ResultBean<String> decrease(@RequestParam("productId") Long productId, @RequestParam("count") Integer count) {
        storageService.decrease(productId, count);
        return ResultBean.success("扣减库存成功！");
    }


}
