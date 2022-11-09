package com.jpaBoard.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;

// 스프링은 @Configuration이 지정된 클래스를 자바 기반의 설정 파일로 인식한다.
@Configuration
// 해당 클래스에서 참조할 properties 파일의 위치를 지정한다.
@PropertySource("classpath:/application.properties")
public class DatabaseConfig {

    @Bean
    // 접두사가 spring.datasource.hikari로 시작하는 설정을 모두 매핑(바인딩)한다.
    @ConfigurationProperties(prefix = "spring.datasource.hikari")
    public HikariConfig hikariConfig() {
        return new HikariConfig();
    }

    @Bean
    public DataSource dataSource() {
        return new HikariDataSource(hikariConfig());
    }
}
