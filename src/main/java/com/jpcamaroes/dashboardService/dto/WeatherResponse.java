package com.jpcamaroes.dashboardService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeatherResponse {
    private String cidade;
    private double temperatura;
    private int umidade;
    private double vento;
}