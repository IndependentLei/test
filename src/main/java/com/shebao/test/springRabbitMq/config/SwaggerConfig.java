//package com.shebao.test.springRabbitMq.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import springfox.documentation.builders.ApiInfoBuilder;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.service.Contact;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;
//
///**
// * @Description :
// * @ClassName : SwaggerConfig
// * @Author : jdl
// * @Create : 2022-07-02 13:53
// */
//@Configuration
//@EnableSwagger2
//public class SwaggerConfig {
//    @Bean
//    public Docket webApiConfig(){
//        return new Docket(DocumentationType.SWAGGER_2)
//                .groupName("webApi")
//                .apiInfo(weApiInfo())
//                .select()
//                .build();
//    }
//
//    private ApiInfo weApiInfo(){
//        return new ApiInfoBuilder()
//                .title("rabbitmq接口文档")
//                .description("rabbitMQ微服务接口定义")
//                .version("1.0")
//                .contact(new Contact("JiDaoLei","http://www.7b114.xyz","1265105759@qq.com"))
//                .build();
//    }
//}
