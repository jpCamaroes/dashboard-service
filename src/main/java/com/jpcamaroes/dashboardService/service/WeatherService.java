package com.jpcamaroes.dashboardService.service;


import com.jpcamaroes.dashboardService.config.WeatherConfig;
import com.jpcamaroes.dashboardService.dto.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class WeatherService {

    private final WebClient webClient;
    private final WeatherConfig weatherConfig;

    @Autowired
    public WeatherService(WeatherConfig config) {
        this.weatherConfig = config;
        this.webClient = WebClient.builder()
                .baseUrl(config.getBaseUrl())
                .build();
    }

    public WeatherResponse getWeather() {
        String uri = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("q", weatherConfig.getCity() + "," + weatherConfig.getCountry())
                        .queryParam("appid", weatherConfig.getKey())
                        .queryParam("units", weatherConfig.getUnits())
                        .queryParam("lang", weatherConfig.getLang())
                        .build())
                .toString(); // Apenas para visualizar a URI (não faz a requisição ainda)

        System.out.println("Requesting weather from URI: " + uri);

        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("q", weatherConfig.getCity() + "," + weatherConfig.getCountry())
                        .queryParam("appid", weatherConfig.getKey())
                        .queryParam("units", weatherConfig.getUnits())
                        .queryParam("lang", weatherConfig.getLang())
                        .build())
                .retrieve()
                .onStatus(
                        status -> status.is4xxClientError() || status.is5xxServerError(),
                        clientResponse -> clientResponse.bodyToMono(String.class).map(body ->
                                new RuntimeException("Erro na chamada da API: " + body)
                        )
                )
                .bodyToMono(WeatherResponse.class)
                .doOnNext(response -> System.out.println("Resposta da API: " + response))
                .block(); // cuidado: bloqueia a thread atual
    }
}