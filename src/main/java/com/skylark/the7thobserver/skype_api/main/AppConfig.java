package com.skylark.the7thobserver.skype_api.main;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Jared on 9/2/16.
 */
public class AppConfig
{
    @Bean
    public RestTemplate getRestTemplate()
    {
        RestTemplateBuilder builder = new RestTemplateBuilder();
        return builder.build();
    }

    @Bean
    public SkypeApi getSkypeApi()
    {
        return new SkypeApi(getRestTemplate());
    }
}

 