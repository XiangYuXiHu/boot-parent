package com.smile.storage.controller;

import com.smile.storage.service.StorageService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description
 * @ClassName StorageController
 * @Author smile
 * @date 2023.07.30 08:35
 */
@RestController
@RequestMapping("storage")
public class StorageController {

    private final StorageService storageService;

    public StorageController(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping("/decreaseStorage")
    public String decreaseStorage(@RequestParam("productId") Long productId, @RequestParam("count") Integer count) {
        storageService.decreaseStorage(productId, count);
        return "减少商品库存成功！";
    }
}
