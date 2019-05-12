package com.test.sj.configure;

import java.util.Arrays;

import javax.servlet.Filter;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.FrameworkServlet;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.test.sj.configure.setting.RootConfiguration;
import com.test.sj.configure.setting.ServletConfiguration;

import lombok.extern.slf4j.Slf4j;

/**
 * Web.xml 대응
 *  해당 클래스에서는 특정 context에 대한 dispatcher-servlet 등록
 *  및 참조 되어야할 context들을 loader에 등록할 것이고
 *  해당 dispatcher-servlet으로 오는 요청에 대한 제한 까지 해당 클래스에서 처리 할 것이다.(20190502 완료)
 * 기타 에러 페이지 리다이렉트에 대해서는 현재 여기서 정의할 수가 없는 것으로 보이며
* 404, 500 에러에 대해서만 따로 컨트롤러 advice를 만들어 핸들링할 예정이다. (20190502 진행중)
*/
@Slf4j
public class WebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer{
	
	/* 
	 * loader
	 * dispatcher
	 * url pattern
	 * filter 의 배열 length를 정의한다.
	 * 해당 클래스 내에서만 사용하므로 접근 제어는 자신만으로 하겠다.
	 */
	private final static int SJCONTEXT_LOADER_NUM		= 1,
							 SJCONTEXT_DISPATCHER_NUM	= 1,
							 SJURL_PATTERN_NUM			= 1,
							 SJFILTER_NUM				= 1;
	/**
	 * root에는 모든 context에서 참조할 수 있도록 loader에 등록할 context들을 등록해야 하고
	 */
	@Override
	protected Class<?>[] getRootConfigClasses() {
		Class<?>[] loaderContext =  new Class<?>[SJCONTEXT_LOADER_NUM];
		log.debug("==================== [START : ROOT CONTEXT SETTING] ====================");
		
		loaderContext[0] = RootConfiguration.class;
		
		Arrays.asList(loaderContext)
				.forEach(x -> {
					log.debug("LOAD : " + x.getName());
				});
		
		log.debug("==================== [END : ROOT CONTEXT SETTING] ====================");
		return loaderContext;
	}

	/**
	 * servlet에는 dispatcherServlet에 등록할 context들을 등록해야 한다.
	 */
	@Override
	protected Class<?>[] getServletConfigClasses() {
		log.debug("==================== [START : SERVLET CONTEXT SETTING] ====================");
		Class<?>[] servletContext =  new Class<?>[SJCONTEXT_DISPATCHER_NUM];
		
		servletContext[0] = ServletConfiguration.class;
		
		Arrays.asList(servletContext)
			.forEach(x -> {
				log.debug("LOAD : " + x.getName());
			});
		
		log.debug("==================== [END : SERVLET CONTEXT SETTING] ====================");
		return servletContext;
	}

	/**
	 * 또한 URL호출 시 dispatcherServlet에서 어떤 패턴으로 된 URL을 받을 것인지에 대한 Mapping을 정의해주어야 하고
	 */
	@Override
	protected String[] getServletMappings() {
		String[]	pattern		= new String[SJURL_PATTERN_NUM];
		String		allPattern	= "/";
		log.debug("==================== [START : PATTERN SETTING] ====================");
		
		pattern[0] = allPattern;
		
		log.debug("pattern" + Arrays.toString(pattern));
		log.debug("==================== [END : PATTERN SETTING] ====================");
		return pattern;
	}
	
	/**
	 * dispatcher에 등록한 서블릿에서 사용할 필터를 필요에 따라 등록한다.
	 * 여기서는 요청에 대하여 항상 UTF-8로 인코딩하는 필터 하나만을 등록한다.
	 */
	@Override
	protected Filter[] getServletFilters() {
		Filter[]	filter			= new Filter[SJFILTER_NUM];
		Filter		encodingFilter	= new CharacterEncodingFilter("UTF-8", true);
//					putPatchFilter	= new HttpPutFormContentFilter();
		log.debug("==================== [START : FILTER SETTING] ====================");
		
		
		filter[0] = encodingFilter;
//		filter[1] = putPatchFilter;
		
		Arrays.asList(filter)
			.forEach(x -> {
				log.debug("LOAD : " + x.getClass().getName());
			});
		
		log.debug("==================== [END : FILTER SETTING] ====================");
		return filter;
	}
	
	/**
	 * 404처리는 해당 영역이나 필터레서 처리해야 할 것 같다.
	 * TODO : 404처리를 위해 NoHandlerFoundException을 Throw하도록 처리한다. 
	 * 하여 어떻게든 dispatcher을 가져와서 처리를 해야 했기에 createDispatcherServlet을 오버라이딩해서 설정을 바꿨다. (20190504 완료)
	 * TODO : 근데 FrameworkServlet는 DispatcherServlet의 부모인거 같은데 이건 또 뭥미?(20190504)
	 */
	@Override
	protected FrameworkServlet createDispatcherServlet(WebApplicationContext servletAppContext) {
		log.debug("==================== [START : CREATE DISPATCHER SERVLET] ====================");
		
		DispatcherServlet	dispatcherServlet	= (DispatcherServlet) super.createDispatcherServlet(servletAppContext);
		FrameworkServlet	frameworkServlet	= null;
		
		log.debug("SETTING : THROWS NO_HANDLER_EXCEPTION -> TRUE");
		
		dispatcherServlet.setThrowExceptionIfNoHandlerFound(true);
		
		frameworkServlet = dispatcherServlet;
		
		log.debug("==================== [END : CREATE DISPATCHER SERVLET] ====================");
		return frameworkServlet;
	}
}