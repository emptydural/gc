package com.test.sj.configure.prop;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 셋팅 값 정의
 * 실행시 서버 관련 셋팅 값들을 정의한다.
 */
@SuppressWarnings("unused")
public class SjProp {
	
	/**
	 * Server 상태에 따른 변경 파라미터가 존재할 시 이를 제어해주는 파라미터가 하나 필요하다.
	 * TODO : 추후 Setter을 사용하여 바꿔도 괜찮을 지를 고려하여 final은 일단 붙이지 않음(20190509) 
	 */
	private static ServerStatus status	= ServerStatus.LOCAL_STATUS;
	/**
	 * path관련 값들은 모두 해당 클래스에서 static final로 생성한다.
	 * TODO : 패키지명은 본래는 enum으로 관리해서 값을 가져오려 했으나 enum으로는 되지 않고 static으로만 가능했다.
	 * enum과 static의 컴파일 순서 차이는 추후 봐야 할 듯 하다, (20190504)
	 */
	public static final String	BASE_PACKAGE 			= "com.test.sj",
								DEFAULT_RESOLVER_PREFIX	= "WEB-INF/views/",
								DEFAULT_RESOLVER_SUFFIX = ".jsp",
								MYBATIS_MAPPER_LOCATION = "/mapper/**/*.xml";
	/**
	 * 해당 클래스 및 내부의 enum에 대해 고정값이냐 서버에 따른 변동값이냐에 따른 제어가 필요 
	 * LOCAL	: 0
	 * DEV		: 1
	 * REAL		: 2
	 * 로 정의
	 */
	public enum ServerStatus {
		LOCAL_STATUS(0),
		DEV_STATUS(1),
		REAL_STATUS(2);
		
		private final int serverStatus;
		
		private ServerStatus(int serverStatus) {
			this.serverStatus = serverStatus;
		}
		
		public int getStatus() {
			return this.serverStatus;
		}
	}
	
	/**
	 * DB 관련 셋팅 값 정의
	 * 클래스 이름처럼 서버 상태에 상관없이 고정인 값이 있고 다른 값이 있다.
	 * 이를 분리처리하는 파라미터가 필요
	 * 분리처리되는 파라미터는 위에서부터 순서대로 LOCAL, DEV, REAL값으로 정의
	 * oracle pool 사용, MAX, MIN 스레드풀 갯수, TIMEOUT 셋팅값은 공통
	 * URL, USERNAME, PASSWORD는 서버 상태에 따라 변경될 수 있는 값이라고 판단하고 진행해보자.
	 * 
	 * TODO : URL, USERNAME, PASSWORD는 편의상 같은 값으로 넣었으며 변경 필요(20190511)
	 */
	public enum DB {
		/*커넥션*/
		HIKARICP_CLASSNAME(
				true,
				Arrays.asList("oracle.jdbc.pool.OracleDataSource")),
		
		/*스레드 pool max*/
		HIKARICP_MAX_POOLSIZE(
				true,
				Arrays.asList("10")),
		
		/*최소 pool*/
		HIKARICP_MIN_IDLESIZE(
				true,
				Arrays.asList("5")),
		
		/*미사용시 종료 Timeout*/
		HIKARICP_TIMEOUT(
				true,
				Arrays.asList("300000")),
		
		/*DB URL*/
		DB_URL_PRIMARY(
				false,
				Arrays.asList(
						"jdbc:oracle:thin:@39.115.145.229:1521:orcl",
						"jdbc:oracle:thin:@39.115.145.229:1521:orcl",
						"jdbc:oracle:thin:@39.115.145.229:1521:orcl"
				)),
		
		/*DB USERNAME*/
		DB_USERNAME_PRIMARY(
				false,
				Arrays.asList(
						"auctionwini",
						"auctionwini",
						"auctionwini"
				)),
		
		/*DB PASSWORD*/
		DB_PASSWORD_PRIMARY(
				false,
				Arrays.asList(
						"auction",
						"auction",
						"auction"
				));
		
		
		private final boolean		isFixed;		
		private final List<String>	dbPropList;
		
		private DB(boolean isFixed, List<String> dbPropList) {
			this.isFixed = isFixed;
			this.dbPropList = dbPropList;
		}
		
		/*
		 * 프로퍼티 값 반환시 고정값이라면 첫째값 반환
		 * 아니라면 지정해둔 서버 상태에 따른 값 반환이 되도록 구현해야 한다.
		 */
		public String getProp() {
			String dbProp = "";
			
			if (isFixed) {
				dbProp = dbPropList.get(0);
			} else {
				dbProp = dbPropList.get(SjProp.status.getStatus());
			}
			return dbProp;
		}
		
		/**
		 * 프로퍼티에서 가져오는 값이 int로 적용일 시 int로 반환
		 */
		public int getPropToInt() {
			String dbProp = "";
			
			if (isFixed) {
				dbProp = dbPropList.get(0);
			} else {
				dbProp = dbPropList.get(SjProp.status.getStatus());
			}
			return Integer.valueOf(dbProp);
		}
	}
	
}
