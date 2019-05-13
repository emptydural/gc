package com.test.sj.vo.login;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;
import lombok.Setter;

/**
 * 처음 객체를 생성하는 시점에 사용자 계정이름, 패스워드, 권한 등을 가지고 있다.
 * 옥션 회원 아이디를 그대로 사용한다 코드하고 이름만 가지고 있도록 지정
 * 기타 기간제 계정이나 사용제제여부등의 파라미터도 LoginVO에서 제어할 수 있도록 boolean 파라미터 4개를 가지도록 만든다.(20190511 완료)
 * */
@Getter
@Setter
public class LoginVO implements UserDetails, Serializable {
	
	private static final long serialVersionUID = 1022191104058731528L;
	
	private String			username,		/*아이디*/
							password,		/*패스워드*/
							userCode,		/*유저Cd*/
							userNm;			/*유저명*/
	
	private List<String> 	authlist;		/*사용자권한 리스트*/
	
	/**
	 * 기간제, 사용 제제, 인증만료, 휴먼계정 여부
	 * default = true로 만듦
	 * TODO : 이렇게 보면 VO는 페이징이나 이것처럼 초기값이 필요한 파라미터의 경우에 대하여 상당히 유용하다고 생각된다.
	 */
	private boolean			isAccountNonExpired			= true,
							isAccountNonLocked			= true,
							isCredenticalsNonExpired 	= true,
							isEnabled					= true;
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		final List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		for (final String authName : authlist) {
			authorities.add(new SimpleGrantedAuthority(authName.toUpperCase()));
		}
		return authorities;
	}

	/* 기간제 계정의 경우 기간 만료 여부*/
	@Override
	public boolean isAccountNonExpired() {
		return isAccountNonExpired;
	}

	// 사용 제제 여부
	@Override
	public boolean isAccountNonLocked() {
		return isAccountNonLocked;
	}

	// 인증정보 만료 여부
	@Override
	public boolean isCredentialsNonExpired() {
		return isCredenticalsNonExpired;
	}

	// 휴먼계정 여부
	@Override
	public boolean isEnabled() {
		return isEnabled;
	}

}
