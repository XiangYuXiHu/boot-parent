package com.smile.oauth.smiple;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

/**
 * @Description
 * @ClassName Test
 * @Author smile
 * @date 2023.10.05 08:56
 */
public class Test {

    public static void main(String[] args) throws UnsupportedEncodingException {
        String string = Base64.getEncoder().encodeToString("jim:123456".getBytes("utf-8"));
        System.out.println(string);
    }
}
