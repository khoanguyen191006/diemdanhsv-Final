package com.example.gateway.configuration;

import feign.Feign;
import feign.Request;
import feign.Retryer;
import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class FeignMultipartConfig {

    @Bean
    public Encoder feignFormEncoder(ObjectFactory<HttpMessageConverters> converters) {
        return new SpringFormEncoder(new SpringEncoder(converters));
    }

    @Bean
    public Request.Options feignOptions() {
        return new Request.Options(310000, 310000);
    }

    @Bean
    @Scope("prototype")
    public Feign.Builder feignBuilder() {
        return Feign.builder()
                .options(new Request.Options(310000, 310000))
                .retryer(Retryer.NEVER_RETRY);
    }
}
