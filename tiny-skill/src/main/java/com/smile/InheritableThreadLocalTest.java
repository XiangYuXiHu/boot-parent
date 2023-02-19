package com.smile;

import com.alibaba.ttl.threadpool.TtlExecutors;
import com.smile.entity.User;
import com.smile.entity.UserContext;

import java.util.concurrent.ExecutorService;

import static java.util.concurrent.Executors.newFixedThreadPool;

/**
 * InheritableThreadLocal就是支持子线程的ThreadLocal
 *
 * @Description
 * @ClassName InheritableThreadLocalTest
 * @Author smile
 * @date 2023.02.11 11:46
 */
public class InheritableThreadLocalTest {

    public static void main(String[] args) {
        inheritableThreadLocalTest1();
    }


    /**
     * 父子线程相同的值
     */
    public static void inheritableThreadLocalTest() {
        User user = new User();
        user.setUsername("jim");
        UserContext.setUser(user);
        System.out.println(user + "---->" + user.getUsername());

        new Thread(() -> {
            User user1 = UserContext.getUser();
            System.out.println(user1 + "++++>" + user1.getUsername());
        }).start();
    }

    /**
     * 线程池失效
     */
    public static void inheritableThreadLocalTest1() {
        ExecutorService executorService = newFixedThreadPool(1);
        User user = new User();
        user.setUsername("jim");
        UserContext.setUser(user);
        executorService.submit(() -> {
            User user1 = UserContext.getUser();
            System.out.println(user1 + "--->" + user1.getUsername());
        });

        User jack = new User();
        jack.setUsername("jack");
        UserContext.setUser(jack);
        executorService.submit(() -> {
            User user1 = UserContext.getUser();
            System.out.println(user1 + "++++>" + user1.getUsername());
        });
    }


    public static void transmittableThreadLocalTest() {
        ExecutorService executorService = TtlExecutors.getTtlExecutorService(newFixedThreadPool(1));
        User user = new User();
        user.setUsername("jim");
        UserContext.setUser(user);
        executorService.submit(() -> {
            User user1 = UserContext.getUser();
            System.out.println(user1 + "--->" + user1.getUsername());
        });

        User jack = new User();
        jack.setUsername("jack");
        UserContext.setUser(jack);
        executorService.submit(() -> {
            User user1 = UserContext.getUser();
            System.out.println(user1 + "++++>" + user1.getUsername());
        });
    }
}
