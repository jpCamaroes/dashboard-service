package com.jpcamaroes.dashboardService.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "weather.api")
public class WeatherConfig {
    private String baseUrl;
    private String key;
    private String city;
    private String country;
    private String units;
    private String lang;
}