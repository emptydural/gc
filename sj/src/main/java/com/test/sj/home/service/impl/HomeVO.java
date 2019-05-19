package com.test.sj.home.service.impl;

import java.io.Serializable;
import java.util.Locale;

import lombok.Getter;
import lombok.Setter;
/**
 * Home Controller VO
 * 현재로서는 시간만 필요(20190503)
 *
 */
@Getter
@Setter
public class HomeVO implements Serializable{

	/**
	 * generate
	 */
	private static final long serialVersionUID = -7473590032673339083L;

	private String currTime;
	private Locale serverLocale;
}
