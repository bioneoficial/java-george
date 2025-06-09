package com.javageorge.controllers;

import com.javageorge.entities.Prova;
import com.javageorge.entities.Turma;
import com.javageorge.repositories.ProfessorRepository;
import com.javageorge.repositories.ProvaRepository;
import com.javageorge.repositories.TurmaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/provas")
public class ProvaController {

    @Autowired
    private ProvaRepository provaRepository;

    @Autowired
    private TurmaRepository turmaRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    @GetMapping
    public List<Prova> listarProvas() {
        return provaRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Prova> buscarProvaPorId(@PathVariable Integer id) {
        return provaRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Prova não encontrada"));
    }

    @GetMapping("/turma/{turmaId}")
    public List<Prova> buscarProvasPorTurma(@PathVariable Integer turmaId) {
        Turma turma = turmaRepository.findById(turmaId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Turma não encontrada"));
        return provaRepository.findByTurma(turma);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Prova criarProva(@RequestBody @Valid Prova prova) {
        // Verificar se a turma existe
        if (prova.getTurma() != null && prova.getTurma().getCodigoTurma() != null) {
            Turma turma = turmaRepository.findById(prova.getTurma().getCodigoTurma())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Turma não encontrada"));
            prova.setTurma(turma);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Turma é obrigatória");
        }

        // A prova não tem campo professor na classe, vamos remover essa parte

        // Verificar se o peso é válido (entre 0 e 1)
        if (prova.getPeso() == null || prova.getPeso() <= 0 || prova.getPeso() > 1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Peso deve estar entre 0 e 1");
        }

        return provaRepository.save(prova);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Prova> atualizarProva(@PathVariable Integer id, @RequestBody @Valid Prova provaAtualizada) {
        return provaRepository.findById(id)
                .map(provaExistente -> {
                    // Verificar se a turma existe
                    if (provaAtualizada.getTurma() != null && provaAtualizada.getTurma().getCodigoTurma() != null) {
                        Turma turma = turmaRepository.findById(provaAtualizada.getTurma().getCodigoTurma())
                                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Turma não encontrada"));
                        provaExistente.setTurma(turma);
                    }

                    // Atualiza apenas os campos não nulos da prova atualizada
                    if (provaAtualizada.getDataProva() != null) {
                        provaExistente.setDataProva(provaAtualizada.getDataProva());
                    }
                    if (provaAtualizada.getPeso() != null) {
                        if (provaAtualizada.getPeso() <= 0 || provaAtualizada.getPeso() > 1) {
                            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Peso deve estar entre 0 e 1");
                        }
                        provaExistente.setPeso(provaAtualizada.getPeso());
                    }

                    return ResponseEntity.ok(provaRepository.save(provaExistente));
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Prova não encontrada"));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarProva(@PathVariable Integer id) {
        provaRepository.findById(id)
                .map(prova -> {
                    provaRepository.delete(prova);
                    return true;
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Prova não encontrada"));
    }
}
