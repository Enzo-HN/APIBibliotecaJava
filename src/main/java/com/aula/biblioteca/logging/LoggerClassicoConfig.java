package com.aula.biblioteca.logging;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("singleton-classico")
class LoggerClassicoConfig {

    @Bean
    ILogger logger() {
        return AppLogger.getInstance();
    }
}