package com.example.corso.service.connector;

import com.example.corso.dto.AlunnoWithoutCorsiDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Base64;
import java.util.List;

@Component
public class AlunniConnector {
//    private String credentials = "user:pass1234";
//    private String encodedAuth = Base64.getEncoder().encodeToString(credentials.getBytes());

    @Autowired
    WebClient webClient;

    public List<AlunnoWithoutCorsiDTO> getAlunni(List<Long> ids) {
        if (ids.isEmpty()) { return List.of(); }

        return webClient.post()
                .uri("/alunni/by-ids", ids)
                .bodyValue(ids)
//                .header(HttpHeaders.AUTHORIZATION, "Basic " + encodedAuth)
                .retrieve()
                .bodyToFlux(AlunnoWithoutCorsiDTO.class)
                .collectList()
                .block();
    }
}
