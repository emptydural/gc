package com.test.sj.configure.ex;

import java.util.ArrayList;

import javax.annotation.PostConstruct;

import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeAliasRegistry;
import org.springframework.stereotype.Component;

import com.test.sj.vo.home.HomeVO;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * root config에서 Mybatis 관련 빈 설정시 Mybatis 셋팅 클래스
 * Mybatis Config 설정은 따로 빼서 가지고 있는다.
 * 컴포넌트로 만들고 이를 Getter로 받아오자 (20190512 완료)
 */
@Slf4j
@Component(value = "myBatisConfigureSetting")
public class MyBatisConfigureSetting {
	
	@Getter
	private Configuration myBatisConfig;
	
	@PostConstruct
	private void getMybatisConfigration() {
		log.debug("MyBatisConfig setting postConstruct check");
		myBatisConfig = new Configuration();
		
		TypeAliasRegistry	alias		= myBatisConfig.getTypeAliasRegistry();
		ArrayList<Class<?>> classList	= loadAliasClasses();
		
		/*Alias 등록*/
		classList.stream().forEach(clazz -> {
			alias.registerAlias(clazz.getSimpleName(), clazz);
		});
		
		/* Mapper에서 빈 파라미터는 NULL로 자동설정 */
		myBatisConfig.setJdbcTypeForNull(JdbcType.NULL);
	}
	
	/**
	 * Alias지정시 보통 패키지를 생략하고 클래스 이름만 적는 식으로 지정하는 경우가 많아
	 * 클래스를 가지고 있고 이를 일괄 지정하도록 구현해보자(20190512 완료)
	 * @return
	 */
	private ArrayList<Class<?>> loadAliasClasses() {
		ArrayList<Class<?>> classList = new ArrayList<>();
		
		/*Alias로 등록할 클래스에 대해 여기에 설정*/
		classList.add(HomeVO.class);
		
		return classList;
	}
}
