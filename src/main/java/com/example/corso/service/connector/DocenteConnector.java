package com.example.corso.service.connector;

import com.example.corso.dto.DocenteDTO;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class DocenteConnector {

    @Autowired
    WebClient webClient;

    public DocenteDTO getDocente(Long id) {
        return webClient.get()
                .uri("/docenti/{id}", id)
                .retrieve()
                .bodyToMono(DocenteDTO.class)
                .block();
    }



}
