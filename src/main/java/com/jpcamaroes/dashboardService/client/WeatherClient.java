package com.jpcamaroes.dashboardService.client;

import com.jpcamaroes.dashboardService.config.WeatherConfig;
import com.jpcamaroes.dashboardService.dto.WeatherResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class WeatherClient {

    private final WeatherConfig config;

    public Mono<WeatherResponse> getWeatherDefault() {
        WebClient webClient = WebClient.builder()
                .baseUrl(config.getBaseUrl())
                .build();

        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("q", config.getCity() + "," + config.getCountry())
                        .queryParam("appid", config.getKey())
                        .queryParam("units", config.getUnits())
                        .queryParam("lang", config.getLang())
                        .build())
                .retrieve()
                .bodyToMono(WeatherResponse.class);
    }
}