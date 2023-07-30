package com.smile.storage.tcc;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description
 * @ClassName ResultHolder
 * @Author smile
 * @date 2023.07.30 07:57
 */
public class ResultHolder {

    private static Map<Class<?>, Map<String, String>> map = new ConcurrentHashMap<>();

    public static void setResult(Class<?> actionClass, String xid, String v) {
        Map<String, String> result = map.get(actionClass);
        if (null == result) {
            synchronized (map) {
                if (null == result) {
                    result = new ConcurrentHashMap<>(16);
                    map.put(actionClass, result);
                }
            }
        }
        result.put(xid, v);
    }

    public static String getResult(Class<?> actionClass, String xid) {
        Map<String, String> result = map.get(actionClass);
        if (null != result) {
            return result.get(xid);
        }
        return null;
    }

    public static void removeResult(Class<?> actionClass, String xid) {
        Map<String, String> results = map.get(actionClass);
        if (null != results) {
            results.remove(xid);
        }
    }
}
