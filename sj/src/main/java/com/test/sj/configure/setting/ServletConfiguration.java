package com.test.sj.configure.setting;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import com.test.sj.configure.prop.SjProp;

import lombok.extern.slf4j.Slf4j;

/**
 * Servlet-context 대응
 * View 관련 설정을 한다.
 * Controller 및 view Resolver관련 설정을 한다.
 * @Autowired는 사용하지 않으므로 annotation config는 하지 ㅇ낳지만
 * autoProxy 및 컴포너트 스캔은 필요하다.
 * TODO : 해당 컨텍스트의 빈 생성 메서드가 2번 호출되는 문제가 있었다.
 *  또한 리소스 Path 등록이 필요하기 떄문에 WebMvcConfigureAdapter를 상속받는다.
 * 원인은 ComponentScan시 @Configuration에 대해 scan 범위에서 제외시켜주지 않아 일어났던 일(20190503 완료)
 */
@Slf4j
@Configuration
@EnableWebMvc
@EnableAspectJAutoProxy
@ComponentScan(basePackages = SjProp.BASE_PACKAGE,
				useDefaultFilters = false,
				includeFilters = @ComponentScan.Filter(type=FilterType.ANNOTATION,
													classes = Controller.class),
				excludeFilters = @ComponentScan.Filter(type=FilterType.ANNOTATION,
													classes = {Service.class, Repository.class, Configuration.class})
		)
public class ServletConfiguration extends WebMvcConfigurerAdapter{
	
	/**
	 * viewResolver가 필요하다. (20190501 완료)
	 * jsp, jstlview 이용
	 */
	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		
		log.debug("==================== [START : DEFAULT VIEW RESOLVER SETTING] ====================");
		
		viewResolver.setViewClass(JstlView.class);
		viewResolver.setPrefix(SjProp.DEFAULT_RESOLVER_PREFIX);
		viewResolver.setSuffix(SjProp.DEFAULT_RESOLVER_SUFFIX);
		viewResolver.setOrder(1);
		
		log.debug("default prefix : " + SjProp.DEFAULT_RESOLVER_PREFIX);
		log.debug("default suffix : " + SjProp.DEFAULT_RESOLVER_SUFFIX);
		
		log.debug("==================== [END : DEFAULT VIEW RESOLVER SETTING] ====================");
		
		registry.viewResolver(viewResolver);
	}
	
	/**
	 * 정적 리소스 경로 설정해야 한다.(20190501 완료)
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resource/**").addResourceLocations("/resources/");
	}
}
