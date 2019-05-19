package com.test.sj.login.dao;

import com.test.sj.login.service.impl.LoginVO;

public interface LoginDAO {
	public LoginVO loadUserByUsername(String username);
}
