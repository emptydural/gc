package com.test.sj.configure.setting;

import java.io.IOException;
import java.util.Properties;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.FilterType;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.test.sj.configure.ex.MyBatisConfigureSetting;
import com.test.sj.configure.prop.SjProp;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import lombok.extern.slf4j.Slf4j;
import net.sf.log4jdbc.Log4jdbcProxyDataSource;
import net.sf.log4jdbc.tools.Log4JdbcCustomFormatter;
import net.sf.log4jdbc.tools.LoggingType;

/**
 * Root-context 대응
 * contextloaderlistener에 들어갈 bean들을 정의하여 담을 것 이다.
 * view와는 관련 없는 Model에 관련된 Bean들을 여기서 정의해줄 예정이다.
 */
/**
 * transaction bean은 여기서 설정하나 포인트컷은 AOP에서 여기서 선언한 빈을 참조하여 선언하도록 해야 할 것 같다.
 * root에서의 component scan은 view단에 관련된 controller를 제외한 나머지로 정의한다.
 * @PropertySource(value = "classpath:/properties/resource.properties")
 * 프로퍼티 및 yami는 파일 생성 자체를 금지당했기 때문에 enum 클래스로 등록해보자
 * TODO : EnableTransactionManagement 어노테이션을 쓰면 platformTransactionManagement 인터페이스를 구현한 빈을
 * 트랜젝션 매니저로 사용하고 원하는위치에 transactional 어노테이션을 놓는 것으로 기능이 동작한다.
 * 즉 tx:annotation-driven과 동일하나 여기서는 aop로 포인트컷을 주어 구현을 해보자(20190508 주석 완료)
 */
//@EnableTransactionManagement
@Slf4j
@Configuration
@ComponentScan(basePackages = SjProp.BASE_PACKAGE,
				useDefaultFilters = false,
				includeFilters = @ComponentScan.Filter(type=FilterType.ANNOTATION,
													classes= {Service.class, Repository.class, Component.class}),
				excludeFilters = @ComponentScan.Filter(type=FilterType.ANNOTATION,
													classes = {Controller.class, Configuration.class})
				)
public class RootConfiguration{
	/**
	 * 커넥션 풀을 정의해서 사용하자
	 * 커넥션 풀은 해당 과제에서 꼭 HikariCP를 사용하도록 제한받았으므로 해당 풀을 이용하여 등록해야 한다.
	 * 이름은 기존과 동일하게 dataSource(20190503 완료)
	 */
	@Bean(name = "dataSource")
	public HikariDataSource getDataSource() {
		log.debug("==================== [START : HIKARI CP SETTING] ====================");
		
		HikariConfig		hikariConfig		= new HikariConfig();
		Properties 			dsProperties		= new Properties();
		HikariDataSource	dataSource			= null; 
		
		
		String	classNm 	= SjProp.DB.HIKARICP_CLASSNAME.getProp(),
				dbUrl		= SjProp.DB.DB_URL_PRIMARY.getProp(),
				username	= SjProp.DB.DB_USERNAME_PRIMARY.getProp(),
				password	= SjProp.DB.DB_PASSWORD_PRIMARY.getProp();
		
		int		maxPoolSize	= SjProp.DB.HIKARICP_MAX_POOLSIZE.getPropToInt(),
				minIdleSize	= SjProp.DB.HIKARICP_MIN_IDLESIZE.getPropToInt(),
				idleTimeout	= SjProp.DB.HIKARICP_TIMEOUT.getPropToInt();
				
		log.debug("classNm : " + classNm);
		log.debug("dbUrl : " + dbUrl);
		log.debug("username : " + username);
		log.debug("password : " + password);
		log.debug("maxPoolSize : " + maxPoolSize);
		log.debug("minIdleSize : " + minIdleSize);
		log.debug("idleTimeout : " + idleTimeout);
		
		/*
		 * TODO : 본래는 jdbcUrl, userName, password 3개의 필드에 setter를 이용하여 때려박았으나
		 * 로그에서 DataSourceClassName이 있을 경우 jdbcUrl을 무시해버리고 그 떄문에 에러가 나서
		 * 해당 3개의 파라미터는 프로퍼티로 넣었다.(20190503 완료) 
		 */
		dsProperties.setProperty("url", dbUrl);
		dsProperties.setProperty("user", username);
		dsProperties.setProperty("password", password);
		
		hikariConfig.setConnectionTestQuery("select 1 from dual");
		hikariConfig.setDataSourceClassName(classNm);
		hikariConfig.setMaximumPoolSize(maxPoolSize);
		hikariConfig.setMinimumIdle(minIdleSize);
		hikariConfig.setIdleTimeout(idleTimeout);
		hikariConfig.setDataSourceProperties(dsProperties);
		
		dataSource = new HikariDataSource(hikariConfig);
		
		log.debug("==================== [END : HIKARI CP SETTING] ====================");
		
		return dataSource;
	}
	
	/**
	 * 또한 그에 따른 트랜젝션 설정
	 * 이미 만들어진 dataSource 콩이 필요하므로 순서 관계가 필요할 것 같다.
	 * 어쩌피 Bean 생성시 위에서 아래로 읽어가며 생성하지만 확실하게 해주려면 DependsOn을 사용하여 특정 빈에 의존한다는 것을 나타내는 것이 더욱 확실하다.(20190504 완료)
	 * TODO : 그냥 클래스단위로 분리하는 것이 훨씬 가독성과 관리면에서 수월할까?(20190511 생각중)
	 */
	@Bean(name = "transactionManager")
	@DependsOn(value = "dataSource")
	public DataSourceTransactionManager getTransactionMng(HikariDataSource dataSource) {
		log.debug("==================== [START : TRANSACTION MANAGER] ====================");
		
		DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
		transactionManager.setDataSource(dataSource);
		
		log.debug("transaction dataSource : " + dataSource.getClass().getName());
		log.debug("==================== [END : TRANSACTION MANAGER] ====================");
		
		return transactionManager;
	}
	
	/**
	 *  쿼리 로그 어떤 방식으로 보여줄 건지 지정이 필요하고 (20190506 완료)
	 */
	@Bean(name="dataSourceLog")
	@DependsOn(value = "dataSource")
	public Log4jdbcProxyDataSource getDataSourceLog(HikariDataSource dataSource) {
		log.debug("==================== [START : DB LOG SETTING] ====================");
		
		Log4jdbcProxyDataSource dataSourceLog	= new Log4jdbcProxyDataSource(dataSource);
		Log4JdbcCustomFormatter logFormatter	= new Log4JdbcCustomFormatter();
		
		/**
		 * 로깅 타입, SQL 시작 전 Prefix, 마진 셋팅
		 */
		LoggingType	type		= LoggingType.MULTI_LINE;
		String		sqlPrefix	= "SQL : \n";
		
		logFormatter.setLoggingType(type);
		logFormatter.setSqlPrefix(sqlPrefix);
		
		dataSourceLog.setLogFormatter(logFormatter);
		
		log.debug("==================== [END : DB LOG SETTING] ====================");
		
		return dataSourceLog;
	}
	/**
	 * Mybatis 설정 및 Mapper를 연결해줘야 한다.
	 * TODO : xml설정을 이용한 configLocation대신 Configuration 클래스를 직접 이용(20190512 완료)
	 */
	@Bean(name = "sqlSessionFactory")
	@DependsOn(value = "dataSourceLog")
	public SqlSessionFactoryBean getSqlSessionFactory(Log4jdbcProxyDataSource dataSourceLog,
			ApplicationContext applicationContext, MyBatisConfigureSetting myBatisConfigureSetting) throws IOException{
		log.debug("==================== [START : MYBATIS SETTING] ====================");
		
		SqlSessionFactoryBean 					sqlSessionFactory	= new SqlSessionFactoryBean();
		
		/*
		 * Mapper 인터페이스를 이용하지 않기 떄문에 Mapper Location은 그대로 xml로 지정한다.
		 * 즉 @MapperScan도 사용하지 않는다.(20190509 완료)
		 */
		Resource[] mapperLocations = applicationContext.getResources("classpath:"+SjProp.MYBATIS_MAPPER_LOCATION);
		
		sqlSessionFactory.setDataSource(dataSourceLog);
		sqlSessionFactory.setConfiguration(myBatisConfigureSetting.getMyBatisConfig());
		sqlSessionFactory.setMapperLocations(mapperLocations);
		
		log.debug("==================== [END : MYBATIS SETTING] ====================");
		
		return sqlSessionFactory;
	}
	
	/**
	 * Mapper를 사용하지 않으므로
	 * Dao단에서 사용할 sql 템플릿을 생성해야 한다.(20190509 완료)
	 */
	@Bean(name = "sqlSession")
	@DependsOn(value = "sqlSessionFactory")
	public SqlSessionTemplate getSqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
		
		SqlSessionTemplate sqlSession = new SqlSessionTemplate(sqlSessionFactory);
		
		return sqlSession;
	}
	
}
