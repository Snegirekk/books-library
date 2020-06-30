package com.snegirekk.books_library.core.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.snegirekk.books_library.core.api_client.ReviewApiClient;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Configuration
public class AppConfig {

    private final String REVIEW_SERVICE_URL = "http://app-review:8081/api/v1";

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public ReviewApiClient reviewApiClient() {
        MappingJackson2HttpMessageConverter jsonMessageConverter = new MappingJackson2HttpMessageConverter();
        jsonMessageConverter.setObjectMapper(
                new ObjectMapper()
                    .setPropertyNamingStrategy(new PropertyNamingStrategy.SnakeCaseStrategy())
                    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        );

        RestTemplate client = new RestTemplate(Collections.singletonList(jsonMessageConverter));

        return new ReviewApiClient(client, REVIEW_SERVICE_URL, modelMapper());
    }
}
