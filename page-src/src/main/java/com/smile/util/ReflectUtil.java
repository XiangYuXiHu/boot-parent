package com.smile.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

/**
 * @Description
 * @ClassName ReflectUtil
 * @Author smile
 * @date 2023.02.19 06:21
 */
public class ReflectUtil {

    private static Logger logger = LoggerFactory.getLogger(ReflectUtil.class);

    public static Object getFieldValue(Object obj, String fieldName) {
        Object result = null;
        Field field = getField(obj, fieldName);
        field.setAccessible(true);
        try {
            result = field.get(obj);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return result;
    }

    private static Field getField(Object object, String fieldName) {
        Field field = null;

        for (Class<?> aClass = object.getClass(); aClass != Object.class; aClass = aClass.getSuperclass()) {
            try {
                Field[] declaredFields = aClass.getDeclaredFields();
                Field[] fields = aClass.getFields();
                field = aClass.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {

            }
        }
        return field;
    }

    public static void setField(Object obj, String fieldName, Object value) {
        Field field = getField(obj, fieldName);
        if (null != field) {
            field.setAccessible(true);
            try {
                field.set(obj, value);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
