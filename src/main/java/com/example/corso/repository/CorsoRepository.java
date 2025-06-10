package com.example.corso.repository;

import com.example.corso.entity.Corso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CorsoRepository extends JpaRepository<Corso, Long> {

    List<Corso> findByDocenteId(Long id);
}
