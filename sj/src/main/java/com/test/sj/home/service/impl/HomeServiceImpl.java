package com.test.sj.home.service.impl;

import org.springframework.stereotype.Service;

import com.test.sj.home.dao.HomeDao;
import com.test.sj.home.service.HomeService;

/**
 * homeController Service
 */
@Service
public class HomeServiceImpl implements HomeService{

	private final HomeDao homeDao;
	
	public HomeServiceImpl(HomeDao homeDao) {
		this.homeDao = homeDao;
	}
	
	/*현재 시각 반환 */
	@Override
	public HomeVO selectHomeCurrentTimeInfo() {
		return homeDao.selectHomeCurrentTimeInfo();
	}

	/*현재 시각 반환 */
	@Override
	public HomeVO selectHomeCurrentTimeInfo2() {
		return homeDao.selectHomeCurrentTimeInfo2();
	}
}
