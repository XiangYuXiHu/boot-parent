package com.smile.threadlocal;

/**
 * @Description
 * @ClassName User
 * @Author smile
 * @date 2023.02.11 12:14
 */
public class User {

    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                '}';
    }
}
