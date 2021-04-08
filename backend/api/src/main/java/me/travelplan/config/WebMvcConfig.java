package me.travelplan.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

@EnableWebMvc
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    /****************************************************************************************
     * 정적 리소스를 제공하기 위한 설정
     * RestDocs의 .html 파일들이 Static Resource로 생성되기 때문에 필요함
     * 1. HTTP 요청이 들어오면 Controller가 있는지 없는지 확인
     * 2. 해당하는 Controller가 없다면 RESOURCE_LOCATIONS에 같은 Path의 정적 리소스가 있는지 확인
     ****************************************************************************************/
    private static final String [] RESOURCE_LOCATIONS = {
            "classpath:/static/"
    };
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/**")
                .addResourceLocations(RESOURCE_LOCATIONS)
                .setCachePeriod(3600)
                .resourceChain(true)
                .addResolver(new PathResourceResolver());
    }

    /****************************************************************************************
     * CORS 설정
     * 모두 허용함
     ****************************************************************************************/
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);
    }


    /****************************************************************************************
     * Encoding 설정
     * log 에 한글이 있어도 깨지지 않도록 하기 위함
     ****************************************************************************************/
    @Bean
    public CharacterEncodingFilter characterEncodingFilter() {
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);
        return characterEncodingFilter;
    }

}
