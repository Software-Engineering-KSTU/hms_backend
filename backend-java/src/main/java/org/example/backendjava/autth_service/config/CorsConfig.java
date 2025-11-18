package org.example.backendjava.autth_service.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Применить CORS ко всем путям (/**)
                .allowedOrigins("*") // Разрешить любой источник (Access-Control-Allow-Origin: *)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Разрешить методы
                .allowedHeaders("*") // Разрешить любые заголовки
                .allowCredentials(false) // Если не требуются куки/авторизация
                .maxAge(3600); // Кэшировать настройки CORS на 1 час
    }
}
