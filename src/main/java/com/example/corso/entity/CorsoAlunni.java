package com.example.corso.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "corsoalunni")
public class CorsoAlunni {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_corso")
    private Long corsoId;

    @Column(name = "id_alunni")
    private Long alunnoId;
}
