package com.example.corso.service.connector;

import com.example.corso.dto.DocenteDTO;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Base64;

@Component
public class DocenteConnector {
//    private String credentials = "user:pass1234";
//    private String encodedAuth = Base64.getEncoder().encodeToString(credentials.getBytes());

    @Autowired
    WebClient webClient;

    public DocenteDTO getDocente(Long id) {
        return webClient.get()
                .uri("/docenti/{id}", id)
//                .header(HttpHeaders.AUTHORIZATION, "Basic " + encodedAuth)
                .retrieve()
                .bodyToMono(DocenteDTO.class)
                .block();
    }



}
