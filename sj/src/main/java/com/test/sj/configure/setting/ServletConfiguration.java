package com.test.sj.configure.setting;

import java.util.ArrayList;

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
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesView;
import org.springframework.web.servlet.view.tiles3.TilesViewResolver;

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
	 * 정적 리소스 경로 설정해야 한다.(20190501 완료)
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		log.debug("==================== [START : STATIC RESOURCE SETTING] ====================");
		
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
		
		log.debug("==================== [END : STATIC RESOURCE SETTING] ====================");
	}
	
	/**
	 * viewResolver가 필요하다. (20190501 완료)
	 * 우선 순위는 타일즈보다 뒤로
	 * default - jsp, jstlview 이용
	 * tilesviewer도 필요
	 */
	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		TilesViewResolver				tilesViewResolver	= new TilesViewResolver();
		InternalResourceViewResolver	viewResolver 		= new InternalResourceViewResolver();
		
		log.debug("==================== [START : DEFAULT VIEW RESOLVER SETTING] ====================");
		
		/*tilesView*/
		tilesViewResolver.setViewClass(TilesView.class);
		tilesViewResolver.setOrder(1);
		log.debug("set tiles view");
		
		/*default jstlview*/
		viewResolver.setViewClass(JstlView.class);
		viewResolver.setPrefix(SjProp.DEFAULT_RESOLVER_PREFIX);
		viewResolver.setSuffix(SjProp.DEFAULT_RESOLVER_SUFFIX);
		viewResolver.setOrder(2);
		
		log.debug("default prefix : " + SjProp.DEFAULT_RESOLVER_PREFIX);
		log.debug("default suffix : " + SjProp.DEFAULT_RESOLVER_SUFFIX);
		
		log.debug("==================== [END : DEFAULT VIEW RESOLVER SETTING] ====================");
		
		/*2개를 추가*/
		registry.viewResolver(tilesViewResolver);
		registry.viewResolver(viewResolver);
	}
	
	/**
	 * tiles config 추가필요 
	 * TODO : WebMvcConfigurer에는 해당 부분에 대응하는 메서드가 없는 것으로 보인다. 그냥 Bean으로 추가하자.(20190504 완료)
	 * tiles.xml부분은 JavaConfig로 하기가 까다롭다. 얌전히 xml로 진행하자
	 */
	
	@Bean(name = "tilesConfigurer")
	public TilesConfigurer getTilesConfigure() {
		TilesConfigurer		tilesConfigurer	= new TilesConfigurer();
		ArrayList<String>	definsionList	= new ArrayList<>();
		String[] 			definitions		= null;
		
		definsionList.add(SjProp.TILES_CONFIGURE_LOCATION);
		
		definitions = definsionList.toArray(new String[definsionList.size()]);
		
		tilesConfigurer.setDefinitions(definitions);
		
		return tilesConfigurer;
	}
}
