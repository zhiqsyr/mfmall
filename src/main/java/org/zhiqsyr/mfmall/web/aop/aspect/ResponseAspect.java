package org.zhiqsyr.mfmall.web.aop.aspect;

import org.zhiqsyr.mfmall.domain.dto.Response;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Method;

/**
 * RESTful 统一应答
 * 
 * @author dongbz at 2016-07-21
 */
@Aspect
@Component
public class ResponseAspect {

	@Pointcut("execution (* org.zhiqsyr.mfmall.service.*.*(..))")
	public void restful() {}

	@Around("restful()")
	public Object aroundRestful(ProceedingJoinPoint joinPoint) throws Throwable {
		// 只会处理声明 RequestMapping/GetMapping/PostMapping/PutMapping/DeleteMaping 注解的方法
		Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
		boolean isHandled = method.isAnnotationPresent(RequestMapping.class)
				|| method.isAnnotationPresent(GetMapping.class) || method.isAnnotationPresent(PostMapping.class)
				|| method.isAnnotationPresent(PutMapping.class) || method.isAnnotationPresent(DeleteMapping.class);
		if (!isHandled) {
			return joinPoint.proceed();
		}

		Response response = new Response();
		response.setHttpStatus(HttpStatus.OK);
		response.setData(joinPoint.proceed());
		response.setMessage("SUCCESS");
		return response;
	}
	
}
