package com.goodeen.config.druid;

import java.util.HashMap;
import java.util.Map;

import org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.alibaba.druid.support.spring.stat.DruidStatInterceptor;

@Configuration
@EnableTransactionManagement
public class DruidStatConfig {
  @Bean
  public ServletRegistrationBean druidServlet() {
    ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean();
    servletRegistrationBean.setServlet(new StatViewServlet());
    servletRegistrationBean.addUrlMappings("/druid/*");
    Map<String, String> initParameters = new HashMap<String, String>();
    initParameters.put("resetEnable", "false");
    initParameters.put("allow", "127.0.0.1");
    initParameters.put("deny", "192.168.20.38");
    servletRegistrationBean.setInitParameters(initParameters);
    return servletRegistrationBean;
  }

  @Bean
  public FilterRegistrationBean filterRegistrationBean() {
    FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
    filterRegistrationBean.setFilter(new WebStatFilter());
    filterRegistrationBean.addUrlPatterns("/*");
    filterRegistrationBean.addInitParameter("exclusions",
        "*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*");
    return filterRegistrationBean;
  }

  @Bean
  public DruidStatInterceptor druidStatInterceptor() {
    DruidStatInterceptor druidStatInterceptor = new DruidStatInterceptor();
    return druidStatInterceptor;
  }

  @Bean
  public BeanNameAutoProxyCreator beanNameAutoProxyCreator() {
    BeanNameAutoProxyCreator beanNameAutoProxyCreator = new BeanNameAutoProxyCreator();
    beanNameAutoProxyCreator.setProxyTargetClass(true);
    beanNameAutoProxyCreator.setInterceptorNames("druidStatInterceptor");
    return beanNameAutoProxyCreator;
  }
}
