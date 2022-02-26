package com.pan.pmall.config;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: Mr.Pan
 * @create: 2022-01-29 11:30
 **/
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    /*配置生成的文档信息
     * 配置生成规则*/

    /*Docket封装接口的文档信息
     * 一般放入Spring容器中的都命名为 getXXX*/
    @Bean
    public Docket getDocket() {
        /*DocumentationType.SWAGGER_2: 接口文档风格*/
        return new Docket(DocumentationType.SWAGGER_2)
                /*用来获取接口封面的基本信息*/
                .apiInfo(apiInfo())
                .select()
                /*指定接口文档扫描包
                 * 设置basePackage会将包下的所有被@Api标记类的所有方法作为api*/
                .apis(RequestHandlerSelectors.basePackage("com.pan.pmall.controller"))
                /*指定扫描路径, 一般为any()
                 * 也可指定为user下的路径paths(PathSelectors.regex("/user/"))*/
                .paths(PathSelectors.any())
                .build()
                /*配置输入token*/
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts());
    }

    /*配置生成文档信息*/
    private ApiInfo apiInfo() {
        /*apiInfo是一个接口, 它借助工厂模式的ApiInfoBuilder来创造对象
         * build()方法会返回一个apiInfo对象*/
        return new ApiInfoBuilder()
                .title("《潘氏商城》后端接口文档说明")
                .description("此文档详细说明了商城后端接口的规范")
                .termsOfServiceUrl("http://localhost:8080/pmall")
                .version("v1.0.1")
                .contact(new Contact("pan", "www.pandemo.top", "pan@qq.com"))
                .build();
    }

    /*以下为token相关配置*/
    private List<ApiKey> securitySchemes() {
        List<ApiKey> apiKeyList = new ArrayList<>();
        /*new ApiKey(参数名, 参数的key, 参数放在请求的什么位置)*/
        apiKeyList.add(new ApiKey("token", "Authorization", "header"));
        return apiKeyList;
    }

    private List<SecurityContext> securityContexts() {
        List<SecurityContext> securityContexts = new ArrayList<>();
        securityContexts.add(
                SecurityContext.builder()
                        .securityReferences(defaultAuth())
                        .forPaths(PathSelectors.regex("^(?!auth).*$"))
                        .build()
        );
        return securityContexts;
    }

    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        List<SecurityReference> securityReferences = new ArrayList<>();
        securityReferences.add(new SecurityReference("Authorization", authorizationScopes));
        return securityReferences;
    }
}
