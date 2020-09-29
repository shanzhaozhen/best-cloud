package org.shanzhaozhen.basicapi.config.mvc;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@ConfigurationProperties(prefix = "upload")
@Getter
@Setter
@Configuration
public class CustomWebMvcConfig implements WebMvcConfigurer {

    private String relativePath;

    private String realPath;

//    @Override
//    public void addViewControllers(ViewControllerRegistry registry) {
////        registry.addViewController("/login").setViewName("public/login");
//        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);                                  //过滤时优先执行
//    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
//        registry.addResourceHandler(relativePath + "**").addResourceLocations("file:" + realPath);
//        registry.addResourceHandler("/swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/statics/**").addResourceLocations("classpath:/statics/");
        // 解决 SWAGGER 404报错
        registry.addResourceHandler("/swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}
