package com.example.KaraveddyConnect.config.mapper;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.boot.jackson.autoconfigure.JsonMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tools.jackson.databind.DeserializationFeature;
import tools.jackson.databind.MapperFeature;
import tools.jackson.databind.SerializationFeature;

/**
 * @author Anuruththan-local
 * @org Smartzi
 * @since 2026-09-22 17:02 PM
 **/
@Configuration
public class JacksonConfig {
    @Bean
    public JsonMapperBuilderCustomizer customizer() {
        return builder -> builder
                .changeDefaultPropertyInclusion(value ->
                        JsonInclude.Value.construct(
                                JsonInclude.Include.NON_NULL,
                                JsonInclude.Include.NON_NULL
                        )
                )
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                .disable(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES)
                .disable(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY)
                .disable(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS);
    }
}