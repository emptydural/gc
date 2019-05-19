package com.test.sj.aop.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 실행시점에서 해당 어노테이션이 붙은 클래스를 LOGGING 한다.
 * 해당 어노테이션은 클래스에 붙는 것만을 허용한다.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ConfigLoggingEnable {
	/**
	 *특정한 설정은 따로 잡지 않는다.
	 *해당 어노테이션을 기준으로 AOP를 가능하게 하기 위한 지시자의 역할만 담당
	 */
}
