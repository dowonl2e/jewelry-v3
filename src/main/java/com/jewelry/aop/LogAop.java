package com.jewelry.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
public class LogAop {

	@Around(
			"(" +
			"execution(* com.jewelry.manager.*.*Service.insert(..)) || " +
			"execution(* com.jewelry.manager.*.*Service.update(..)) ||" +
			"execution(* com.jewelry.manager.*.*Service.multi(..))" + 
			")"
	)
	public Object logging(ProceedingJoinPoint pjp) throws Throwable {
		
		Object result = pjp.proceed();
		
		log.info("==> Aspect Root:: " + pjp.getSignature().getDeclaringTypeName());
		log.info("==> Aspect Method:: " + pjp.getSignature().getName());
		
		return result;
	}
	
}
