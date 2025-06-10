package com.example.corso.controller;

import com.example.corso.entity.CorsoAlunni;
import com.example.corso.service.CorsoAlunniService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/iscrizioni")
public class CorsoAlunniController {

    @Autowired
    private CorsoAlunniService service;

    @GetMapping("/lista")
    public List<CorsoAlunni> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}/alunni")
    public List<CorsoAlunni> findByCorsoId(@PathVariable Long id) {
        return service.findByCorsoId(id);
    }

    @GetMapping("/{id}/corsi")
    public List<CorsoAlunni> findByAlunnoId(@PathVariable Long id) {
        return service.findByAlunnoId(id);
    }

    @PostMapping("iscrivi")
    public List<CorsoAlunni> postIscrizione(@RequestBody List<CorsoAlunni> iscritti) {
        for (CorsoAlunni iscritto : iscritti) {
            service.save(iscritto);
        }
        return iscritti;
    }

    @DeleteMapping("{id}/corsi")
    public void deleteIscrizione(@PathVariable Long id) {
        service.deleteCorsi(id);
    }

}
