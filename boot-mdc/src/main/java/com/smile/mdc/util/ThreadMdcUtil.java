package com.smile.mdc.util;

import com.smile.mdc.domain.Constants;
import jdk.nashorn.internal.codegen.CompilerConstants;
import org.slf4j.MDC;

import java.util.Map;
import java.util.concurrent.Callable;

/**
 * @Description
 * @ClassName ThreadMdcUtil
 * @Author smile
 * @date 2023.02.11 19:09
 */
public class ThreadMdcUtil {

    public static void setIfAbsent() {
        if (MDC.get(Constants.TRADE_ID) == null) {
            MDC.put(Constants.TRADE_ID, TradeUtil.getTradeId());
        }
    }

    public static <T> Callable<T> wrapper(final Callable<T> callable, final Map<String, String> context) {
        return () -> {
            if (null == context) {
                MDC.clear();
            } else {
                MDC.setContextMap(context);
            }
            setIfAbsent();
            try {
                return callable.call();
            } finally {
                MDC.clear();
            }
        };
    }

    public static Runnable wrapper(final Runnable runnable, final Map<String, String> context) {
        return () -> {
            if (null == context) {
                MDC.clear();
            } else {
                MDC.setContextMap(context);
            }
            setIfAbsent();
            try {
                runnable.run();
            } finally {
                MDC.clear();
            }
        };
    }


}
