package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author 86189
 * @program: IntelliJ IDEA
 * @description:
 * @date 2022-04-19 16:09:06
 */
@Configuration
@EnableSwagger2
@ComponentScan(basePackages = {"controller"})
@EnableWebMvc
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("对外开放接口API文档")   //大标题
                .description("HTTP对外开放接口")  //小标题
                .version("1.0.0")   //版本
                .termsOfServiceUrl("http://localhost:8080")    //终端服务器
                .license("LICENSE") //链接显示文字
                .licenseUrl("http://localhost:8080")   //网站链接
                .build();
    }
}
