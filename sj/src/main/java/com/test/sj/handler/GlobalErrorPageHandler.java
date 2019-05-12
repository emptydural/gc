package com.test.sj.handler;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import lombok.extern.slf4j.Slf4j;

/**
 * AbstractAnnotationConfigDispatcherServletInitializer
 * 에는 에러페이지 구현체가 명시되있지 않기 때문에 따로 ControllerAdvice로 구현한다.
 * TODO : 다만 jsp쪽의 500 미처리 에러는 톰캣에서 해야지 여기서는 처리가 불가능할 것이다.(20190504)
 */
@Slf4j
@ControllerAdvice
public class GlobalErrorPageHandler{
	
	/**
	 * 404 처리, 에러 페이지로 넘김
	 * dispatcherServlet에서 해당 에러를 이쪽으로 넘겨줘야만 처리 가능(20190504 완료)
	 */
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	@ExceptionHandler(NoHandlerFoundException.class)
	@Order(Ordered.HIGHEST_PRECEDENCE)
	public String notFoundException(Exception exception) {
		
		log.debug("404 ERROR : " + exception.toString());
		
		return "error/error";
	}
	
	/**
	 *  나머지 에러 공통 처리
	 * home으로 돌아감
	 * dispatcherServlet에서 해당 에러를 이쪽으로 넘겨줘야만 처리 가능(20190504 완료)
	 */
	@ExceptionHandler(Exception.class)
	@Order(Ordered.LOWEST_PRECEDENCE)
	public String globalException(HttpServletRequest request, Exception exception) {
		
		log.debug("=====================================");
		log.debug("* Exception: " + exception.toString());
		log.debug("* Exception url: " + request.getRequestURL());
		log.debug("* Exception message: " + exception.getMessage());
		log.debug("=====================================");
		
		return "redirect:/";
	}
}
