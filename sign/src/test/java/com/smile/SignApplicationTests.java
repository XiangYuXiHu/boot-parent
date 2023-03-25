package com.smile;

import com.smile.service.SignService;
import com.smile.util.DateUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Map;
import java.util.TreeMap;

@SpringBootTest
class SignApplicationTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    SignService signService;

    @Test
    public void testSign() {
        int userId = 10001;
        LocalDate today = LocalDate.now();
        boolean signed = signService.sign(userId, today);
        if (signed) {
            System.out.println("您已签到：" + DateUtil.format(today, "yyyy-MM-dd"));
        } else {
            System.out.println("签到完成：" + DateUtil.format(today, "yyyy-MM-dd"));
        }
        LocalDate yesterday = LocalDate.now().minusDays(1);
        boolean yesterdaySigned = signService.sign(userId, yesterday);
        if (yesterdaySigned) {
            System.out.println("您已签到：" + DateUtil.format(yesterday, "yyyy-MM-dd"));
        } else {
            System.out.println("签到完成：" + DateUtil.format(yesterday, "yyyy-MM-dd"));
        }

        boolean isSigned = signService.isSign(userId, today);
        if (isSigned) {
            System.out.println("您已签到：" + DateUtil.format(today, "yyyy-MM-dd"));
        } else {
            System.out.println("尚未签到：" + DateUtil.format(today, "yyyy-MM-dd"));
        }

        long count = signService.signCount(userId, today);
        System.out.println("本月签到次数：" + count);

        long continuousSignCount = signService.continuousSignCount(userId, today);
        System.out.println("连续签到次数：" + continuousSignCount);

        String firstSignDay = signService.firstSignDay(userId, today);
        System.out.println("本月首次签到：" + firstSignDay);

        System.out.println("当月签到情况：");
        TreeMap<String, Boolean> monthSign = signService.monthSign(userId, today);
        for (Map.Entry<String, Boolean> entry : monthSign.entrySet()) {
            System.out.println(entry.getKey() + ": " + (entry.getValue() ? "√" : "-"));
        }
    }

}
