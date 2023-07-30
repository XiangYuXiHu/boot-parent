package com.simle.tcc;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description
 * @ClassName ResultHolder
 * @Author smile
 * @date 2023.07.29 06:53
 */
public class ResultHolder {

    private static Map<Class<?>, Map<String, String>> map = new ConcurrentHashMap<>();

    public static void setResult(Class<?> actionClass, String xid, String v) {
        Map<String, String> results = map.get(actionClass);
        if (null == results) {
            synchronized (map) {
                if (null == results) {
                    results = new ConcurrentHashMap<>(16);
                    map.put(actionClass, results);
                }
            }
        }

        results.put(xid, v);
    }

    public static String getResult(Class<?> actionClass, String xid) {
        Map<String, String> result = map.get(actionClass);
        if (null != result) {
            return result.get(xid);
        }
        return null;
    }

    public static void remove(Class<?> actionClass, String xid) {
        Map<String, String> result = map.get(actionClass);
        if (null != result) {
            result.remove(xid);
        }
    }
}
