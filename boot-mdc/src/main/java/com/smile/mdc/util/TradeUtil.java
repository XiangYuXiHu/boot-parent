package com.smile.mdc.util;

import java.util.UUID;

/**
 * @Description
 * @ClassName TradeUtil
 * @Author smile
 * @date 2023.02.11 18:11
 */
public class TradeUtil {

    public static String getTradeId() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
