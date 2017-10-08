package com.goodeen.config.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {
  @Bean
  SessionInterceptor sessionInterceptor() {
    return new SessionInterceptor();
  }

  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(sessionInterceptor()).addPathPatterns("/**").excludePathPatterns(
        "/favicon.ico", "/data/*.json", "/**/*.css", "/**/*.js", "/**/*.swf", "/**/*.png",
        "/**/*.gif", "/**/*.jpg", "/**/*.eot", "/**/*.svg", "/**/*.ttf", "/**/*.woff", 
        "/login", "/logout", "/signup", "/account/resendPassword", "/account/forgetPassword/**",
        "/user/create", "/user/activate/**", "/user/resetPassword", "/user/email/available",
        "/user/screenName/available", "/settings/**");
  }

}
