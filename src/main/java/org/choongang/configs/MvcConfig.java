package org.choongang.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableJpaAuditing
@EnableConfigurationProperties(FileProperties.class)
public class MvcConfig implements WebMvcConfigurer {

    @Autowired
    private FileProperties fileProperties;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) { // 정적 경로 설정
        registry.addResourceHandler(fileProperties.getUrl() + "**")
                .addResourceLocations("file:///" + fileProperties.getPath());
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/");
    }

    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource ms = new ResourceBundleMessageSource();
        ms.setDefaultEncoding("UTF-8");
        ms.setBasenames("messages.commons", "messages.validations", "messages.errors");

        return ms;
    }

    @Bean
    public HiddenHttpMethodFilter hiddenHttpMethodFilter() {
        return  new HiddenHttpMethodFilter();
        // form에서 get,post말고 쓸수 있게 만들어준다.
    }
}
