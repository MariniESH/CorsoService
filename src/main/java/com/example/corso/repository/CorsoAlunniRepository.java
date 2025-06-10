package com.example.corso.repository;

import com.example.corso.entity.CorsoAlunni;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CorsoAlunniRepository extends JpaRepository<CorsoAlunni, Long> {

    List<CorsoAlunni> findCorsiByAlunnoId(Long alunnoId);

    List<CorsoAlunni> findAlunniByCorsoId(Long corsoId);

}
