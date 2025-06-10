package com.example.corso.service.connector;

import com.example.corso.dto.AlunnoWithoutCorsiDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Component
public class AlunniConnector {

    @Autowired
    WebClient webClient;

    public AlunnoWithoutCorsiDTO getAlunno(Long id) {
        return webClient.get()
                .uri("/alunni/{id}", id)
                .retrieve()
                .bodyToMono(AlunnoWithoutCorsiDTO.class)
                .block();
    }
}
