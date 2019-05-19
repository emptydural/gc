package com.test.sj.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
/**
 * Web.xml 이 올라가는 과정을 Log로 찍는다.
 */

@Slf4j
@Aspect
@Component
public class ConfigureAspect {
	
	/**
	 * pointcut은 본인이 만든 커스텀 annotation을 사용한다.
	 * 각 메서드의 메서드 시그니쳐를 받아 메서드 이름 및 파라미터를 출력
	 * 메서드 위에 붙일 경우는 @annotation을 사용하나 클래스 밑의 모든 메서드에 대하 메서드 시그니쳐 출력이므로 @within을 사용
	 * TODO : Loader에 Aop가 적용되지 않는다. 확인 필요(20190518)
	 */
	@Before(value = "@within(com.test.sj.aop.annotation.ConfigLoggingEnable)")
	public void checkConfigure(JoinPoint jp) {
		
		log.debug("method signiture : " + jp.getSignature().toShortString());
	}
}
