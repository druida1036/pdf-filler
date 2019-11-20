package com.hackathon.pdffiller.config;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class config {

    // Register Jackson Hibernate5 Module to handle JSON serialization of lazy-loaded entities
    // Any beans of type com.fasterxml.jackson.databind.Module are automatically
    // registered with the auto-configured Jackson2ObjectMapperBuilder
    // https://docs.spring.io/spring-boot/docs/current/reference/html/howto-spring-mvc.html#howto-customize-the-jackson-objectmapper
    @Bean
    public Module hibernate5Module() {
        Hibernate5Module hibernate5Module = new Hibernate5Module();
        hibernate5Module.enable( Hibernate5Module.Feature.WRITE_MISSING_ENTITIES_AS_NULL );
        hibernate5Module.disable( Hibernate5Module.Feature.USE_TRANSIENT_ANNOTATION );
        return hibernate5Module;
    }
}
