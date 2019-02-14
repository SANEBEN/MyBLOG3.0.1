package com.myblog.version3.configuration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Created by Trace on 2018-05-16.<br/>
 * Desc: swagger2配置类
 */
@SuppressWarnings({"unused"})
@Configuration
@EnableSwagger2
public class Swagger2Config {

    @Bean("UserApis")
    public Docket userApis() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("用户模块")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.myblog.version3.controller.User"))
                .paths(PathSelectors.any())
                .build();
    }

    @Bean("PublicApis")
    public Docket publicApis() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("公共模块")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.myblog.version3.controller.Public"))
                .paths(PathSelectors.any())
                .build();
    }

    @Bean("ManagementApis")
    public Docket managementApis() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("后台模块")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.myblog.version3.controller.Management"))
                .paths(PathSelectors.any())
                .build();
    }

    @Bean("ArticleApis")
    public Docket articleApis(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("文章模块")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.myblog.version3.controller.Article"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("三年二班的路小雨博客网站API文档")
                .description("个人博客网站项目")
                .termsOfServiceUrl("")
                .contact("@三年二班的路小雨")
                .version("1.0")
                .build();
    }
}
