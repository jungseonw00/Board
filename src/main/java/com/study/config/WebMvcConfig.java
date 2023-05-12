package com.study.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.ShallowEtagHeaderFilter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Filter;

import static org.springframework.http.CacheControl.noCache;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 정적 리소스 캐시 설정
        registry.addResourceHandler("/static/**") // 매핑 URI 설정
                .addResourceLocations("classpath:/static/") // 정적 리소스 위치 설정
                .setCacheControl(noCache());
    }

    @Bean
    public FilterRegistrationBean<ShallowEtagHeaderFilter> shallowEtagHeaderFilter() {
        FilterRegistrationBean<ShallowEtagHeaderFilter> frb = new FilterRegistrationBean<>();
        frb.setFilter(new ShallowEtagHeaderFilter());
        frb.addUrlPatterns("/static/*");
        frb.setOrder(1);
        return frb;
    }

    @Bean
    public FilterRegistrationBean httpHeaderFilter() {
        FilterRegistrationBean<Filter> filterFilterRegistrationBean = new FilterRegistrationBean<>();
        filterFilterRegistrationBean.setFilter(new HeaderConfig());
        filterFilterRegistrationBean.setOrder(2);
        filterFilterRegistrationBean.addUrlPatterns("/*");
        return filterFilterRegistrationBean;
    }
}
