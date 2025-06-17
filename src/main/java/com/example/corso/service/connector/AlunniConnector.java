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

    private final WebClient.Builder webClientBuilder;

    @Autowired
    public AlunniConnector(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    private WebClient webClient() {
        // every time you build, the filter will inject the header
        return webClientBuilder.build();
    }

    public List<AlunnoWithoutCorsiDTO> getAlunni(List<Long> ids) {
        if (ids.isEmpty()) { return List.of(); }

        return webClient().post()
                .uri("/alunni/by-ids", ids)
                .bodyValue(ids)
                .retrieve()
                .bodyToFlux(AlunnoWithoutCorsiDTO.class)
                .collectList()
                .block();
    }
}
