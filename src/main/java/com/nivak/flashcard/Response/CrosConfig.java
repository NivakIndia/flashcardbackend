package com.nivak.flashcard.Response;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CrosConfig {
    @Bean
    WebMvcConfigurer corsConfigurer(){
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry corsRegistry){
                corsRegistry.addMapping("/**")
                .allowedOrigins("https://nivakflashcard.netlify.app")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*");
            }
        };
    }
}


