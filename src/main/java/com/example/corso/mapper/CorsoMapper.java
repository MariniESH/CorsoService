package com.example.corso.mapper;

import com.example.corso.dto.CorsoDTO;
import com.example.corso.dto.CorsoWithoutAlunniDTO;
import com.example.corso.entity.Corso;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface CorsoMapper {

    @Mapping(target = "docenteId", source = "docente.id")
    Corso toEntity(CorsoDTO corsoDTO);

    @Mapping(target = "docente.id", source = "docenteId")
    CorsoDTO toDTO(Corso corso);

    @Mapping(target = "docente.id", source = "docenteId")
    CorsoWithoutAlunniDTO toWithoutAlunniDTO(Corso corso);

    Set<Corso> toEntity(Set<CorsoDTO> corsoDTO);

    Set<CorsoDTO> toDTO(Set<Corso> corso);

    List<CorsoDTO> toDTO(List<Corso> corso);

    List<CorsoWithoutAlunniDTO> toWithoutAlunniDTO(List<Corso> corso);



}
