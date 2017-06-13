package org.zhiqsyr.mfmall.web.aop.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 接口方法性能监控
 *
 * @author zhiqsyr
 * @since 2017/5/11
 */
@Aspect
@Component
public class PerformanceAspect {

    private static final Logger logger = LoggerFactory.getLogger(PerformanceAspect.class);

    @Pointcut("execution (* org.zhiqsyr.mfmall.service.*.*(..))")
    public void service() {}

    @Around("service()")
    public Object aroundService(ProceedingJoinPoint joinPoint) throws Throwable {
        long bg = System.currentTimeMillis();

        Object result = joinPoint.proceed();

        long taked = (System.currentTimeMillis() - bg) / 1000;
        if (taked > 10) {
            // 拼凑方法标识，例如 net.iclassmate.weike.webapp.service.impl.SpaceServiceImpl.getSpaceByDomain(java.lang.String)
            StringBuilder methodName = new StringBuilder(200);
            Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
            methodName.append(method.getDeclaringClass().getName()).append(".").append(method.getName());
            methodName.append("(");
            Class<?>[] parameterTypes = method.getParameterTypes();
            if (parameterTypes.length>0) {
                for(Class<?> parameterType : parameterTypes) methodName.append(parameterType.getName()).append(",");
                methodName.deleteCharAt(methodName.length()-1);
            }
            methodName.append(")");

            logger.warn("{} 执行用时：{}", methodName, taked);
        }

        return result;
    }

}
