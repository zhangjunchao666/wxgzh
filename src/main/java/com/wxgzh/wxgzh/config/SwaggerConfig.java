package com.wxgzh.wxgzh.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * swagger配置文件
 * @author zhangjunchao
 */
@Configuration
@EnableSwagger2
@EnableKnife4j
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfig {

    /**
     * 后端调试栏
     * @return
     */
    @Bean("rest")
    public Docket api4Rest() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).groupName("后端调试").pathMapping("/").select().paths(PathSelectors.any()).build();
    }

    // @Bean("api")
    // public Docket api() {
    //     return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).groupName("前端调试").pathMapping("/api").select().paths(PathSelectors.any()).apis(RequestHandlerSelectors.basePackage("com.hndfsj.app.bis")).build();
    // }


    /**
     * 小程序调试栏
     * @return
     */
    //@Bean("wx")
    //public Docket api4Wx() {
    //    return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).groupName("小程序调试").pathMapping("/api-miniapp").select().paths(PathSelectors.any()).apis(RequestHandlerSelectors.basePackage("com.hndfsj.app.miniapp")).build();
    //}

    /**
     * 根据项目进行设置
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("微信公众号demo").version("1.0.0").
                contact(new Contact("Mr.zjc", "www.hndfsj.com", "zhangjunchao@163.com")).
                description("此项目是微信公众号开发的一个demo,开发环境下开启swagger便于调试和对接，生产环境下关闭swagger。").build();
    }

}

