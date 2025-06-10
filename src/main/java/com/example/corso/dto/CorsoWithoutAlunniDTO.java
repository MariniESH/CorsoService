package com.example.corso.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CorsoWithoutAlunniDTO {
    private Long id;
    private String nome;
    private Integer ore;
    private Integer anno;
    private DocenteDTO docente;
}
