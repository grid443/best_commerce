package com.best.commerce.apigateway.config;

import com.best.commerce.apigateway.ApiGatewayApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
@ConditionalOnProperty(name = "swagger.enabled", havingValue = "true")
public class SwaggerConfig {
  @Bean
  public Docket api() {
    List<SecurityScheme> schemeList = new ArrayList<>();
    return new Docket(DocumentationType.SWAGGER_2)
        .securitySchemes(schemeList)
        .select()
        .apis(RequestHandlerSelectors.basePackage(ApiGatewayApplication.class.getPackageName()))
        .paths(PathSelectors.any())
        .build();
  }
}
