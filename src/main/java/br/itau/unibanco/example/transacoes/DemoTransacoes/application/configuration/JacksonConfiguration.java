package br.itau.unibanco.example.transacoes.DemoTransacoes.application.configuration;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfiguration {

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer offsetDateTimeCustomizer() {
        return builder -> builder
                .featuresToDisable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE)
                .modules(new JavaTimeModule());
    }
}
