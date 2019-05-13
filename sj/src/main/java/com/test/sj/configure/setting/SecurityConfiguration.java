package com.test.sj.configure.setting;

import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.test.sj.service.login.impl.LoginServiceImpl;

import lombok.extern.slf4j.Slf4j;

/**
 * security.xml 대응
 * 현재 유저 정보 로드 해주는 서비스 콩은 rootConfigurer에서 생성되므로 반드시 해당 클래스가 root보다 나중에 실행되어야 한다.
 */
@Slf4j
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
	
	private	final LoginServiceImpl loginServiceImpl;
	
	public SecurityConfiguration(LoginServiceImpl loginServiceImpl) {
		this.loginServiceImpl = loginServiceImpl;
	}

	/**
	 * 폼 로그인을 구현해야 한다.
	 * 현 페이지에 대해 접근 제한은 두지 말자 하지만 현재 여기 버전만 하더라도 로그인 접근에 대한 제어는 반드시 필요하다. 추후에 작업(20190511, 미완료)
	 * 로그인 페이지는 /login
	 * 프로세싱은 /loginprocessing
	 * 로그아웃은 /logout으로
	 * 파라미터는 username, password로 받는다.
	 * default는 /으로 잡고 항상 여기로 가도록 구현하자
	 * 
	 * 성공시에는 메인으로, 실패시에는 /login 페이지로 가도록 만들자
	 * 또한 로그아웃시에는 여기서는 세션을 다 날리도록 하자
	 * 
	 * TODO : csrf 토큰에 의해 403 권한 에러가 나온다. 이는 추후 더 알아보고 일단 disable처리하자(20190508, 완료)
	 * TODO : 실패 처리를 위해서는 SimpleUrlAuthenticationFailureHandler를 구현한 로그인 실패 핸들러가 하나 필요하다. 
	 * 때문에 실패시 alert처리가 되어있지 않으며 이는 추후에 작업(20190511, 미완료)
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		log.debug("==================== [START : SECURITY URL SETTING] ====================");
		
		http
		.authorizeRequests()
			.anyRequest().permitAll()
			.and()	/*접근 제한 두지 말고*/
		.formLogin()
			.loginPage("/login")
			.loginProcessingUrl("/loginprocessing")
			.usernameParameter("username")
			.passwordParameter("password")
			.permitAll()
			.defaultSuccessUrl("/")
			.failureUrl("/login")
			.and()	/*로그인 페이지 설정*/
		.logout()
			.logoutUrl("/logout")
			.logoutSuccessUrl("/")
			.invalidateHttpSession(true)
			.and()	/*로그아웃 설정*/
		.csrf()
			.disable()
			.httpBasic();
		
		log.debug("==================== [END : SECURITY URL SETTING] ====================");
	}
	/**
	 * Manager설정이 필요하다.
	 * 유저서비스와 패스워드 인코딩 방식을 설정해주는 부분이필요한데
	 * 일단 AUCTIONWINI의 유저를 참조할 생각이므로 
	 * 패스워드 인코딩은 동일하게 SHA512로 지정한다.(20190507 완료)
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		log.debug("==================== [START : USER AUTH SETTING] ====================");
		
		ShaPasswordEncoder sha512 = new ShaPasswordEncoder(512);
		
		auth
			.userDetailsService(loginServiceImpl)
			.passwordEncoder(sha512);
		
		log.debug("==================== [END : USER AUTH SETTING] ====================");
	}
	
	/**
	 * 권한처리에 대해 예외로 할 URL 정의 필요하다.
	 * 예외 대상은 정적 리소스정도면 되려나
	 */
	@Override
	public void configure(WebSecurity web) throws Exception {
		log.debug("==================== [START : SECURITY EXCEPT SETTING] ====================");
		
		web
			.ignoring()
			.antMatchers("/resources/**");
		
		log.debug("==================== [START : SECURITY EXCEPT SETTING] ====================");
	}
	
	/**
	 * 세션 처리를 해야 할 것이다.
	 * TODO : 굳이 세션 bean이 없어도 동작에는 전혀 문제가 없어서 넣지 않았다. 이는 어째서인지 조금 더 파봐야 할 것 같다.(20190511)
	 */
	
	/**
	 * 권한에 대한 핸들링이 필요하다.
	 * TODO : AccessDeniedHandler를 구현하여 핸들링해야 한다.(20190508, 미완료)
	 */
}
