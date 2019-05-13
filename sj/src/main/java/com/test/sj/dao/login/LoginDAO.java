package com.test.sj.dao.login;

import com.test.sj.vo.login.LoginVO;

public interface LoginDAO {
	public LoginVO loadUserByUsername(String username);
}
