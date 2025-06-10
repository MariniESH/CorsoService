package com.example.corso.dto;

import lombok.Data;

import java.util.List;

@Data
public class CorsoDTO {

    private Long id;
    private String nome;
    private Integer ore;
    private Integer anno;
    private DocenteDTO docente;
    private List<AlunnoWithoutCorsiDTO> alunni;
}
