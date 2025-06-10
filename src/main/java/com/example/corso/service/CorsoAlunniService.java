package com.example.corso.service;

import com.example.corso.entity.CorsoAlunni;
import com.example.corso.repository.CorsoAlunniRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CorsoAlunniService {

    @Autowired
    private CorsoAlunniRepository corsoAlunniRepository;

    public List<CorsoAlunni> findAll() {
         return corsoAlunniRepository.findAll();
    }

    public List<CorsoAlunni> findByCorsoId (Long id) {
        return corsoAlunniRepository.findAlunniByCorsoId(id);
    }

    public List<CorsoAlunni> findByAlunnoId (Long id) {
        return corsoAlunniRepository.findCorsiByAlunnoId(id);
    }

    public void save(CorsoAlunni corsoAlunni) {
        corsoAlunniRepository.save(corsoAlunni);
    }

    public void deleteCorsi(Long idAlunno) {
        List<CorsoAlunni> corsi = corsoAlunniRepository.findCorsiByAlunnoId(idAlunno);
        corsoAlunniRepository.deleteAll(corsi);
    }
}
