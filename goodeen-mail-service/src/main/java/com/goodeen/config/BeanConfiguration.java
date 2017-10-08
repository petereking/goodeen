package com.goodeen.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;

@Configuration
public class BeanConfiguration {
  @Bean
  public FreeMarkerConfigurationFactoryBean freeMarkerConfiguration() {
    FreeMarkerConfigurationFactoryBean fc = new FreeMarkerConfigurationFactoryBean();
    fc.setTemplateLoaderPath("classpath:templates");
    return fc;
  }
}
