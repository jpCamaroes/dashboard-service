package com.jpcamaroes.dashboardService.controller;

import com.jpcamaroes.dashboardService.dto.WeatherResponse;
import com.jpcamaroes.dashboardService.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {

    @Autowired
    private WeatherService service;

    @GetMapping
    public WeatherResponse getWeather() {
        return service.getWeather();
    }
}