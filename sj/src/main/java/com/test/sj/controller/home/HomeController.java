package com.test.sj.controller.home;

import java.util.Locale;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.test.sj.service.home.HomeService;
import com.test.sj.vo.home.HomeVO;

import lombok.extern.slf4j.Slf4j;

/**
 * 첫 welcome-page 용도
 * 시간만 보여준다.
 * 시간은 DB를 통해서 부여하자(20190503 완료)
 */
@Slf4j
@Controller
public class HomeController {
	
	private HomeService homeService;
	
	public HomeController(HomeService homeService) {
		this.homeService = homeService;
	}
	
	/**
	 * 첫 페이지에 시간을 반환한다.
	 * @param locale
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		log.info("Welcome home! The client locale is {}.", locale);
		
		HomeVO homeVO = homeService.selectHomeCurrentTimeInfo();
		
		model.addAttribute("serverTime", homeVO);
		
		return "home/home.tiles";
	}
	
	/**
	 * 첫 페이지에 시간을 반환한다.
	 * TODO : 해당 homecopy는 SQL에서 EXCEPTION을 고의로 발생시켜 에러가 제대로 핸들링 되는지를 체크한다(20190507 완료)
	 * @param locale
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/homecopy", method = RequestMethod.GET)
	public String homecopy(Locale locale, Model model) {
		log.info("Welcome underworld! The client locale is {}.", locale);
		
		HomeVO homeVO = homeService.selectHomeCurrentTimeInfo2();
		
		model.addAttribute("serverTime", homeVO);
		
		return "home/homecopy.tiles";
	}
	
}
