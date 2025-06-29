package com.jpcamaroes.dashboardService.controller;

import com.jpcamaroes.dashboardService.client.AuthClient;
import com.jpcamaroes.dashboardService.client.WeatherClient;
import com.jpcamaroes.dashboardService.dto.WeatherResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class WeatherController {

    private final AuthClient authClient;
    private final WeatherClient weatherClient;

    @GetMapping("/weather")
    public Mono<ResponseEntity<WeatherResponse>> getWeather(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {
        String jwt = authHeader.startsWith("Bearer ") ? authHeader.substring(7) : authHeader;
        return authClient.validateToken(jwt)
                .flatMap(isValid -> {
                    if (Boolean.TRUE.equals(isValid)) {
                        return weatherClient.getWeatherDefault()
                                .map(ResponseEntity::ok);
                    } else {
                        return Mono.just(ResponseEntity.status(401).build());
                    }
                });
    }
}