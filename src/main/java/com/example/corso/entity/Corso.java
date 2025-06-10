package com.example.corso.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "corso")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Corso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private Integer ore;

    @Column(name = "anno_accademico")
    private Integer anno;

    @Column(name = "id_docente")
    private Long docenteId;



}
