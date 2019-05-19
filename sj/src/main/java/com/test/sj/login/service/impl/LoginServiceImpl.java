package com.test.sj.login.service.impl;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.test.sj.login.dao.LoginDAO;

/**
 * UserDetailsService의 구현체가 필요하다.
 * 이는 로그인시 유저이름을 로드하는 부분은 반드시 커스터마이징이 필요하기 때문이다.
 * 
 * 이미 설계도인 UserDetailsService는 있으므로 트랜젝션이 되도록 Impl만 생성하자
 * 
 * 해당 서비스를 구현한 해당 구현체는 시큐리티의 로드 정보로서 사용한다.
 */
@Service
public class LoginServiceImpl implements UserDetailsService {
	
	private final LoginDAO loginDAO;
	
	public LoginServiceImpl(LoginDAO loginDAO) {
		this.loginDAO = loginDAO;
	}
	
	/**
	 * 로그인 시 유저 정보 로드
	 */
	@Override
	public LoginVO loadUserByUsername(String username) throws UsernameNotFoundException {
		LoginVO loginInfo = null;
		
		loginInfo = loginDAO.loadUserByUsername(username);
		
		return loginInfo;
	}
}