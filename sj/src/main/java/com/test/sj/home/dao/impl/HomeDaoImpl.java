package com.test.sj.home.dao.impl;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.test.sj.home.dao.HomeDao;
import com.test.sj.home.service.impl.HomeVO;

/**
 * Dao Impl단에서만 sqlSession 주입받도록 
 * Mapper 어노테이션을 이용한 방식은 가공을 서비스에서 해야 하기 떄문에 dao로 사용
 */
@Repository
public class HomeDaoImpl implements HomeDao{

	private final SqlSessionTemplate sqlSession;
	
	public HomeDaoImpl(SqlSessionTemplate sqlSession) {
		this.sqlSession = sqlSession;
	}

	/*현재 시각 반환*/
	@Override
	public HomeVO selectHomeCurrentTimeInfo() {
		return sqlSession.selectOne("homeMapper.selectHomeCurrentTimeInfo");
	}
	
	/*현재시각 반환 - 고의로 에러낸 SQL*/
	@Override
	public HomeVO selectHomeCurrentTimeInfo2() {
		return sqlSession.selectOne("homeMapper.selectHomeCurrentTimeInfo2");
	}
}
