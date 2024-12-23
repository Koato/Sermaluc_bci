package com.bci.sermaluc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import java.util.List;

@Configuration
public class WebConfig {

    @Bean
    public List<HttpMessageConverter<?>> configureMessageConverters() {
        // Crear un convertidor JSON y devolverlo en una lista
        MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
        return List.of(jsonConverter);
    }
}
