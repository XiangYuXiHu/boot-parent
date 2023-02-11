package com.smile.mdc.interceptor;

import com.smile.mdc.util.TradeUtil;
import org.slf4j.MDC;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.smile.mdc.domain.Constants.TRADE_ID;

/**
 * @Description
 * @ClassName LogInterceptor
 * @Author smile
 * @date 2023.02.11 17:56
 */
public class LogInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String tradeId = request.getHeader(TRADE_ID);
        if (StringUtils.isEmpty(tradeId)) {
            tradeId = TradeUtil.getTradeId();
        }
        MDC.put(TRADE_ID, tradeId);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        MDC.remove(TRADE_ID);
    }
}
