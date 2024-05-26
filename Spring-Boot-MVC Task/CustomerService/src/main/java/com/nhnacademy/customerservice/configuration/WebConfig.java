package com.nhnacademy.customerservice.configuration;

import com.nhnacademy.customerservice.interceptor.LoginCheckInterceptor;

import com.nhnacademy.customerservice.repository.UserRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;



@Configuration
public class WebConfig implements WebMvcConfigurer{
    private final UserRepository userRepository;

    public WebConfig(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        LoginCheckInterceptor loginCheckInterceptor = new LoginCheckInterceptor(userRepository);
        registry.addInterceptor(loginCheckInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/cs/login");
    }

}
