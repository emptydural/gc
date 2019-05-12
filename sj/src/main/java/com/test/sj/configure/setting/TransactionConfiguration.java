package com.test.sj.configure.setting;

import java.util.Collections;
import java.util.List;

import javax.xml.transform.Source;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.interceptor.MatchAlwaysTransactionAttributeSource;
import org.springframework.transaction.interceptor.RollbackRuleAttribute;
import org.springframework.transaction.interceptor.RuleBasedTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import com.test.sj.configure.prop.SjProp;

/**
 * 트랜젝션 기능은 AOP로 구현하자
 * aop로 구현하기 위한 Aspect annotation은 클래스 단위이기 때문에 분리하였다.
 */

@Aspect
@Configuration
public class TransactionConfiguration {

	/* 
	 * rootConfigure에서 만든 transactionManager콩이 필요
	 */
	private DataSourceTransactionManager transactionManager;
	
	private TransactionConfiguration(DataSourceTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}
	
	/**
	 * txAdvice를 구현하고
	 * tx:method와 rollback-for은 RuleBasedTransactionAttribute 클래스에 구현한다.
	 * tx:attributes는 RuleBasedTransactionAttribute로, tx:advice는 TransactionInterceptor로
	 * 하나만 있는 리스트로 추가/삭제가 없기 떄문에 한번 singletonList도 사용(메모리) (20190511 완료)
	 */
	@Bean(name = "txAdvice")
	private TransactionInterceptor txAdvice() {
		MatchAlwaysTransactionAttributeSource	source					= new MatchAlwaysTransactionAttributeSource();
		RuleBasedTransactionAttribute 			transactionAttribute	= new RuleBasedTransactionAttribute();
		List<RollbackRuleAttribute> 			roleBackRule 			= Collections.singletonList(new RollbackRuleAttribute(Exception.class));
		TransactionInterceptor 					txAdvice 				= null;
		
		/*모든 메서드 exception떳을 때 롤백 rule로*/
		transactionAttribute.setName("*");
		transactionAttribute.setRollbackRules(roleBackRule);
		
		source.setTransactionAttribute(transactionAttribute);
		
		txAdvice = new TransactionInterceptor(transactionManager, source);
		
		return txAdvice;
	}
	
	/**
	 * 관심 포인트를 설정해야 한다.
	 * 기존은 annotation:driven으로 transactional이 있는 메서드나 클래스에 적용되었지만
	 * 여기서는 포인트컷으로 한다, 전자정부 방식대로 구현하면 될 것 같다(20190512 완료)
	 */
	@Bean(name = "txAdvisor")
	private Advisor txAdvisor() {
		AspectJExpressionPointcut	pointcut	= new AspectJExpressionPointcut();
		DefaultPointcutAdvisor		txAdvisor	= null;
		
		/*pointcut은 서비스가 있는 serviceImpl 기준으로*/
		pointcut.setExpression("execution(* " + SjProp.BASE_PACKAGE + ".service..impl.*Impl.*(..)");
		
		return txAdvisor;
	}
}
