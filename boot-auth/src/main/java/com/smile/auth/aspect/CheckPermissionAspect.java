package com.smile.auth.aspect;

import com.smile.auth.anno.CheckPermission;
import com.smile.auth.exception.BizException;
import com.smile.auth.service.MenuService;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.Objects;

/**
 * @Description
 * @ClassName CheckPermissionAspect
 * @Author smile
 * @date 2022.04.29 22:27
 */
@Aspect
@Component
public class CheckPermissionAspect {

    private static final String USER_ID = "user_id";

    @Autowired
    private MenuService menuService;

    @Pointcut("@annotation(com.smile.auth.anno.CheckPermission)")
    public void checkPermission() {
    }

    @Before("checkPermission()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();
        HttpServletResponse response = sra.getResponse();

        Enumeration<String> enumeration = request.getHeaderNames();
        Long userId = null;
        while (enumeration.hasMoreElements()) {
            String name = enumeration.nextElement();
            if (USER_ID.equalsIgnoreCase(name)) {
                userId = Long.valueOf(request.getHeader(name));
            }
        }
        if (!Objects.isNull(userId)) {
            Class<?> clazz = joinPoint.getTarget().getClass();
            String methodName = joinPoint.getSignature().getName();
            Class[] parameterTypes = ((MethodSignature) joinPoint.getSignature()).getParameterTypes();
            Method method = clazz.getMethod(methodName, parameterTypes);
            if (method.getAnnotation(CheckPermission.class) != null) {
                CheckPermission checkPermission = method.getAnnotation(CheckPermission.class);
                String menuCode = checkPermission.value();
                if (StringUtils.isNoneBlank(menuCode)) {
                    boolean isAllow = menuService.isAllowOperator(userId, menuCode);
                    if (!isAllow) {
                        throw new BizException("没有访问权限");
                    }
                }
            }
        }
    }
}
