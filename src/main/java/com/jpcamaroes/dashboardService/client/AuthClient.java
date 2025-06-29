package com.jpcamaroes.dashboardService.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@Service
public class AuthClient {

    private final WebClient webClient;

    public AuthClient(@Value("${auth.service.url}") String authServiceUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(authServiceUrl)
                .build();
    }

    public Mono<Boolean> validateToken(String jwt) {
        return webClient.get()
                .uri("/auth/validate")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwt)
                .retrieve()
                .toBodilessEntity()
                .map(response -> true)
                .onErrorResume(WebClientResponseException.Unauthorized.class, e -> Mono.just(false))
                .onErrorResume(e -> Mono.error(e));
    }
}