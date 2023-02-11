package com.smile.domain;

import com.alibaba.ttl.TransmittableThreadLocal;

/**
 * @Description
 * @ClassName UserContext
 * @Author smile
 * @date 2023.02.11 12:15
 */
public class UserContext {

//    private static InheritableThreadLocal<User> userContext = new InheritableThreadLocal<>();

    private static TransmittableThreadLocal<User> userContext = new TransmittableThreadLocal<>();

    public static User getUser() {
        return userContext.get();
    }

    public static void setUser(User user) {
        userContext.set(user);
    }

    public static void removeUser() {
        userContext.remove();
    }
}
