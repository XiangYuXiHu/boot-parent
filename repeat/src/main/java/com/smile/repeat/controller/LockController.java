package com.smile.repeat.controller;

import com.smile.repeat.anno.DistributedLock;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description
 * @ClassName LockController
 * @Author smile
 * @date 2020.03.01 16:49
 */
@RestController
@RequestMapping("/lock")
public class LockController {

    @DistributedLock(prefix = "test")
    @GetMapping("/test")
    public String query(@RequestParam String token) {
        return "success-" + token;
    }

}
