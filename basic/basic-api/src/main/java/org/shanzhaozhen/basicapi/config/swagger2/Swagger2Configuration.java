package org.shanzhaozhen.basicapi.config.swagger2;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Getter
@Setter
@Configuration
@EnableSwagger2
@ConfigurationProperties(prefix = "swagger")
@ConditionalOnProperty(name = "swagger.enabled", havingValue = "true")
public class Swagger2Configuration {

    private String title;

    private String description;

    private String termsOfServiceUrl;

    private String contactName;

    private String contactUrl;

    private String contactEmail;

    private String license;

    private String licenseUrl;

    private String version;

    /**
     * 指定扫描包的路径来指定要创建API的目录，一般是控制器这个包
     *
     * @return
     */
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo())
                .select()   // 选择那些路径和api会生成document
                .paths(PathSelectors.any()) // 对所有路径进行监控
                .apis(RequestHandlerSelectors.any())    // 对所有api进行监控
                .apis(RequestHandlerSelectors.basePackage("org.shanzhaozhen.basicapi.controller"))//这是注意的代码
                .build();
    }

    /**
     * 设置API的基本信息
     *
     * @return
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(title)
                .description(description)
                .termsOfServiceUrl(termsOfServiceUrl)
                .contact(new Contact(contactName, contactUrl, contactEmail))
                .license(license)
                .licenseUrl(licenseUrl)
                .version(version)
                .build();
    }


}
