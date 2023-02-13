package com.study.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;

// 스프링은 @Configuration이 지정된 클래스를 자바 기반의 설정 파일로 인식한다.
@Configuration
// 해당 클래스에서 참조할 properties 파일의 위치를 지정한다.
@PropertySource("classpath:/application.properties")
@RequiredArgsConstructor
public class DatabaseConfig {

    // 스프링 컨테이너이다. Bean의 생성과 사용, 관계, 생명 주기 등을 관리합니다.
    private final ApplicationContext context;

    @Bean
    // @PropertySource에 저장된 파일에서 접두사가 spring.datasource.hikari로 시작하는 설정을 모두 매핑(바인딩)한다.
    @ConfigurationProperties(prefix = "spring.datasource.hikari")
    public HikariConfig hikariConfig() {
        return new HikariConfig();
    }

    // 커넥션 풀을 지원하기 위한 인터페이스이다.
    @Bean
    public DataSource dataSource() {
        return new HikariDataSource(hikariConfig());
    }

    // 마이바티스와 스프링의 연동 모듈로 사용된다.
    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource());
        factoryBean.setMapperLocations(context.getResources("classpath:/mappers/**/*Mapper.xml"));
        factoryBean.setTypeAliasesPackage("com.study.home.dto");
        factoryBean.setConfigLocation(context.getResource("classpath:/config/mybatis-config.xml"));
        return factoryBean.getObject();
    }

    @Bean
    public SqlSessionTemplate sqlSession() throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory());
    }
}
