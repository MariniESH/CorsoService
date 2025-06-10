package com.example.corso.service;

import com.example.corso.dto.AlunnoWithoutCorsiDTO;
import com.example.corso.dto.CorsoDTO;
import com.example.corso.dto.CorsoWithoutAlunniDTO;
import com.example.corso.dto.DocenteDTO;
import com.example.corso.entity.Corso;
import com.example.corso.entity.CorsoAlunni;
import com.example.corso.entity.response.ApiBaseResponse;
import com.example.corso.mapper.CorsoMapper;
import com.example.corso.repository.CorsoAlunniRepository;
import com.example.corso.repository.CorsoRepository;
import com.example.corso.service.connector.AlunniConnector;
import com.example.corso.service.connector.DocenteConnector;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CorsoService {

    @Autowired
    CorsoRepository corsoRepository;

    @Autowired
    CorsoAlunniRepository corsoAlunniRepository;

    @Autowired
    CorsoMapper corsoMapper;

    @Autowired
    DocenteConnector docenteConnector;

    @Autowired
    AlunniConnector alunniConnector;

    public List<CorsoDTO> findAll() {
        List<CorsoDTO> corsi = corsoMapper.toDTO(corsoRepository.findAll());


        for (CorsoDTO corso : corsi) {
            // Per ogni corso mi controllo se c'è un docente, se non lo trovo me lo setto
            if (corso.getDocente() != null && corso.getDocente().getId() != null) {
                DocenteDTO docente = docenteConnector.getDocente(corso.getDocente().getId());
                corso.setDocente(docente);
            } else {
                corso.setDocente(null);
            }
            // Ricerca di eventuali alunni nella tabella CorsoAlunni
            try {
                // Get alunnoIds from CorsoAlunni join table
                List<Long> alunnoIds = corsoAlunniRepository.findAlunniByCorsoId(corso.getId()).stream()
                        .map(CorsoAlunni::getAlunnoId)
                        .toList();

                // Fetch actual alunni from external service
                List<AlunnoWithoutCorsiDTO> alunni = alunnoIds.stream()
                        .map(alunniConnector::getAlunno)
                        .toList();

                corso.setAlunni(alunni);

            } catch (Exception e) {
                e.printStackTrace();
                corso.setAlunni(null);
            }
        }

        return corsi;
    }


    public CorsoDTO findById(Long id) {
        CorsoDTO corso = corsoMapper.toDTO(corsoRepository.findById(id).orElseThrow());
        if (corso.getDocente() != null && corso.getDocente().getId() != null) {
            DocenteDTO docente = docenteConnector.getDocente(corso.getDocente().getId());
            corso.setDocente(docente);
        } else {
            corso.setDocente(null);
        }

        try {
            // Get alunnoIds from CorsoAlunni join table
            List<Long> alunnoIds = corsoAlunniRepository.findAlunniByCorsoId(corso.getId()).stream()
                    .map(CorsoAlunni::getAlunnoId)
                    .toList();

            // Fetch actual alunni from external service
            List<AlunnoWithoutCorsiDTO> alunni = alunnoIds.stream()
                    .map(alunniConnector::getAlunno)
                    .toList();

            corso.setAlunni(alunni);

        } catch (Exception e) {
            e.printStackTrace();
            corso.setAlunni(null);
        }

        return corso;
    }

    public List<CorsoWithoutAlunniDTO> getCorsiWithoutAlunni(List<Long> alunnoIds) {
        List<CorsoWithoutAlunniDTO> corsi = corsoMapper.toWithoutAlunniDTO(corsoRepository.findAllById(alunnoIds));
        for (CorsoWithoutAlunniDTO corso : corsi) {
            if (corso.getDocente() != null && corso.getDocente().getId() != null) {
                corso.setDocente(docenteConnector.getDocente(corso.getDocente().getId()));
            }else {
                corso.setDocente(null);
            }
        }
        return corsi;
    }


    @Transactional
    public ApiBaseResponse<CorsoDTO> save(CorsoDTO corsoDTO) {
        try {
            DocenteDTO docente = null;

            // Optional lookup of docente details
            if (corsoDTO.getDocente() != null && corsoDTO.getDocente().getId() != null) {
                docente = docenteConnector.getDocente(corsoDTO.getDocente().getId());
            }

            // Map and persist
            Corso corso = corsoMapper.toEntity(corsoDTO);
            corso = corsoRepository.save(corso);

            // Prepare response
            CorsoDTO savedDto = corsoMapper.toDTO(corso);
            savedDto.setDocente(docente); // set if available (can be null)

            try {
                // Get alunnoIds from CorsoAlunni join table
                List<CorsoAlunni> alunni = corsoAlunniRepository.findAlunniByCorsoId(corso.getId());
                corsoAlunniRepository.deleteAll(alunni);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (corsoDTO.getAlunni() != null && !corsoDTO.getAlunni().isEmpty()) {
                List<Long> alunnoIds = new ArrayList<>();
                corsoDTO.getAlunni().forEach(alunno -> alunnoIds.add(alunno.getId()));
                List<CorsoAlunni> iscrizioni = new ArrayList<>();
                List<AlunnoWithoutCorsiDTO> alunni = new ArrayList<>();
                alunnoIds.forEach(alunnoId -> {
                    CorsoAlunni iscrizione = new CorsoAlunni();
                    iscrizione.setAlunnoId(alunnoId);
                    iscrizione.setCorsoId(savedDto.getId());
                    iscrizioni.add(iscrizione);
                    alunni.add(alunniConnector.getAlunno(alunnoId));
                });
                corsoAlunniRepository.saveAll(iscrizioni);
                savedDto.setAlunni(alunni);
            }

            return ApiBaseResponse.<CorsoDTO>builder()
                    .content(savedDto)
                    .code(HttpStatus.CREATED.value())
                    .message("Corso salvato con successo")
                    .build();

        } catch (Exception e) {
            return ApiBaseResponse.<CorsoDTO>builder()
                    .content(null)
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .message("Errore interno durante il salvataggio del corso: " + e.getMessage())
                    .build();
        }
    }


    public void delete(Long id) {
        Corso corso = corsoRepository.findById(id).orElseThrow();
        List<CorsoAlunni> alunni = corsoAlunniRepository.findAlunniByCorsoId(id);
        corsoAlunniRepository.deleteAll(alunni);
        corsoRepository.deleteById(corso.getId());
    }


    public List<CorsoDTO> findByDocente(Long docenteId) {
        List<CorsoDTO> corsi = corsoMapper.toDTO(corsoRepository.findByDocenteId(docenteId));
        return corsi;
    }

}
