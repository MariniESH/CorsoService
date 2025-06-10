package com.example.corso.controller;


import com.example.corso.dto.CorsoDTO;
import com.example.corso.dto.CorsoWithoutAlunniDTO;
import com.example.corso.entity.response.ApiBaseResponse;
import com.example.corso.service.CorsoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/corsi")
public class CorsoController {

    @Autowired
    CorsoService corsoService;

    // LISTA
    @GetMapping("/lista")
    public List<CorsoDTO> list() {
        return corsoService.findAll();
    }

    @GetMapping("/{id}")
    public CorsoDTO findById(@PathVariable Long id) {
        return corsoService.findById(id);
    }

    @PostMapping("/by-ids")
    public List<CorsoWithoutAlunniDTO> getCorsoByIds(@RequestBody List<Long> ids) {
        return corsoService.getCorsiWithoutAlunni(ids);
    }

    // CREATE
    @PostMapping("/save")
    public ApiBaseResponse<CorsoDTO> save(@RequestBody CorsoDTO corso) {
        return corsoService.save(corso);
    }

    // UPDATE
    @PutMapping("/{id}")
    public ApiBaseResponse<CorsoDTO> update(@PathVariable Long id, @RequestBody CorsoDTO corso) {
        corso.setId(id);
        return corsoService.save(corso);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        corsoService.delete(id);
    }

    @GetMapping("/docente/{docenteid}")
    public List<CorsoDTO> findByDocente(@PathVariable Long docenteid) {
        return corsoService.findByDocente(docenteid);
    }


//    @GetMapping("alunni/{id}")
//    public String showAlunni(@PathVariable Long id, Model model) {
//        model.addAttribute("corso", corsoService.get(id));
//        model.addAttribute("alunni", alunnoService.findAll());
//        List<Long> alunniId = new ArrayList<>();
//        for (AlunnoWithoutCorsiDTO alunno : corsoService.get(id).getAlunni()) {
//            alunniId.add(alunno.getId());
//        }
//        model.addAttribute("iscritti", alunniId);
//        return "alunni-iscritti";
//    }



//    @PostMapping("/alunni/{idCorso}")
//    public String addAlunni(
//            @PathVariable Long idCorso,
//            @RequestParam(required = false, name = "alunniIds") List<Long> idAlunni) {
//
//        corsoService.updateAlunni(idCorso, idAlunni != null ? idAlunni : new ArrayList<>());
//        return "redirect:/corsi/lista";
//    }
}
