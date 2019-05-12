package com.test.sj.dao.home.impl;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.test.sj.dao.home.HomeDao;
import com.test.sj.vo.home.HomeVO;

/**
 * Dao Impl단에서만 sqlSession 주입받도록 
 * Mapper 어노테이션을 이용한 방식은 가공을 서비스에서 해야 하기 떄문에 dao로 사용
 */
@Repository
public class HomeDaoImpl implements HomeDao{

	private final SqlSession sqlSession;
	
	public HomeDaoImpl(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	@Override
	public HomeVO selectHomeCurrentTimeInfo() {
		return sqlSession.selectOne("homeMapper.selectHomeCurrentTimeInfo");
	}
	
	@Override
	public HomeVO selectHomeCurrentTimeInfo2() {
		return sqlSession.selectOne("homeMapper.selectHomeCurrentTimeInfo2");
	}
}
