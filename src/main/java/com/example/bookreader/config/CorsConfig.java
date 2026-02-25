package com.example.bookreader.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        try {
            registry.addMapping("/**")  // все endpoints
                    .allowedOrigins(
                            "http://localhost:5500",
                            "http://127.0.0.1:5500",
                            "http://" + InetAddress.getLocalHost().getHostAddress() + ":5500")
                    .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH")
                    .allowedHeaders("*")
                    .allowCredentials(true)
                    .maxAge(3600);
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }
}