package com.nhnacademy.exam.hotel.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSourceConfig {

	@Bean
	public BasicDataSource dataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setUrl("jdbc:mysql://133.186.241.167:3306/nhn_exam_29");
		dataSource.setUsername("nhn_exam_29");
		dataSource.setPassword("uY3mgjoz");
		dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");

		// 최적화 파라미터 설정
		dataSource.setInitialSize(5);
		dataSource.setMaxTotal(5);
		dataSource.setMaxIdle(5);
		dataSource.setMinIdle(5);

		// 추가 최적화 설정
		dataSource.setTestOnBorrow(true);
		dataSource.setValidationQuery("SELECT 1");

		return dataSource;
	}
}