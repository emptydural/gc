package com.test.sj.login.dao.impl;

import java.util.ArrayList;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.test.sj.login.dao.LoginDAO;
import com.test.sj.login.service.impl.LoginVO;

@Repository
public class LoginDAOImpl implements LoginDAO{

	private final SqlSessionTemplate sqlSession;
	
	public LoginDAOImpl(SqlSessionTemplate sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	/*
	 * 로그인 시 유저정보 로드
	 * 정보 종재시 LOGIN이라는 권한을 내려준다.
	 */
	@Override
	public LoginVO loadUserByUsername(String username) {
		ArrayList<String>	authList	=	new ArrayList<>();
		LoginVO 			loginVO		=	sqlSession.selectOne("loginMapper.loadUserByUsername", username);
		
		if(loginVO != null) {
			authList.add("LOGIN");
			loginVO.setAuthlist(authList);
		}
		
		return loginVO;
	}

}
