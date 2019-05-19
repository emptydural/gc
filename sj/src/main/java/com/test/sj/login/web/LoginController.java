package com.test.sj.login.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 로그인 처리 컨트롤러
 */
@Controller
public class LoginController {

	/*로그인 페이지 이동*/
	@GetMapping(value="/login")
	public String login() {
		return "login/login.tiles";
	}
}
