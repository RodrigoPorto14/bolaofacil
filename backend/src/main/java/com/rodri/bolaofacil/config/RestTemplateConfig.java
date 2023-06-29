package com.rodri.bolaofacil.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig 
{
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) 
    {
        return builder
            .requestFactory(HttpComponentsClientHttpRequestFactory::new)
            .build();
    }
}