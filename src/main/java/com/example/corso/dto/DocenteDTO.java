package com.example.corso.dto;

import lombok.Data;

import java.sql.Date;

@Data
public class DocenteDTO {
    private Long id;
    private String nome;
    private String cognome;
    private Date data;
}
