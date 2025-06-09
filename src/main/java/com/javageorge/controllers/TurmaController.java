package com.javageorge.controllers;

import com.javageorge.entities.Disciplina;
import com.javageorge.entities.Professor;
import com.javageorge.entities.Turma;
import com.javageorge.repositories.DisciplinaRepository;
import com.javageorge.repositories.ProfessorRepository;
import com.javageorge.repositories.TurmaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/turmas")
public class TurmaController {

    @Autowired
    private TurmaRepository turmaRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    @GetMapping
    public List<Turma> listarTurmas() {
        return turmaRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Turma> buscarTurmaPorId(@PathVariable Integer id) {
        return turmaRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Turma não encontrada"));
    }

    @GetMapping("/periodo/{periodo}")
    public List<Turma> buscarTurmasPorPeriodo(@PathVariable String periodo) {
        return turmaRepository.findByPeriodo(periodo);
    }

    @GetMapping("/professor/{professorId}")
    public List<Turma> buscarTurmasPorProfessor(@PathVariable Integer professorId) {
        Professor professor = professorRepository.findById(professorId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Professor não encontrado"));
        return turmaRepository.findByProfessor(professor);
    }

    @GetMapping("/disciplina/{disciplinaId}")
    public List<Turma> buscarTurmasPorDisciplina(@PathVariable Integer disciplinaId) {
        Disciplina disciplina = disciplinaRepository.findById(disciplinaId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Disciplina não encontrada"));
        return turmaRepository.findByDisciplina(disciplina);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Turma criarTurma(@RequestBody @Valid Turma turma) {
        // Verificar se a disciplina existe
        if (turma.getDisciplina() != null && turma.getDisciplina().getCodigoDisciplina() != null) {
            disciplinaRepository.findById(turma.getDisciplina().getCodigoDisciplina())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Disciplina não encontrada"));
        }

        // Verificar se o professor existe
        if (turma.getProfessor() != null && turma.getProfessor().getCodigoPessoa() != null) {
            professorRepository.findById(turma.getProfessor().getCodigoPessoa())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Professor não encontrado"));
        }

        return turmaRepository.save(turma);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Turma> atualizarTurma(@PathVariable Integer id, @RequestBody @Valid Turma turmaAtualizada) {
        return turmaRepository.findById(id)
                .map(turmaExistente -> {
                    // Verificar se a disciplina existe
                    if (turmaAtualizada.getDisciplina() != null && turmaAtualizada.getDisciplina().getCodigoDisciplina() != null) {
                        disciplinaRepository.findById(turmaAtualizada.getDisciplina().getCodigoDisciplina())
                                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Disciplina não encontrada"));
                    }

                    // Verificar se o professor existe
                    if (turmaAtualizada.getProfessor() != null && turmaAtualizada.getProfessor().getCodigoPessoa() != null) {
                        professorRepository.findById(turmaAtualizada.getProfessor().getCodigoPessoa())
                                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Professor não encontrado"));
                    }

                    // Atualiza apenas os campos não nulos da turma atualizada
                    if (turmaAtualizada.getPeriodo() != null) {
                        turmaExistente.setPeriodo(turmaAtualizada.getPeriodo());
                    }
                    if (turmaAtualizada.getLimiteAlunos() != null) {
                        turmaExistente.setLimiteAlunos(turmaAtualizada.getLimiteAlunos());
                    }
                    if (turmaAtualizada.getDataInicio() != null) {
                        turmaExistente.setDataInicio(turmaAtualizada.getDataInicio());
                    }
                    if (turmaAtualizada.getDataFim() != null) {
                        turmaExistente.setDataFim(turmaAtualizada.getDataFim());
                    }
                    if (turmaAtualizada.getDisciplina() != null) {
                        turmaExistente.setDisciplina(turmaAtualizada.getDisciplina());
                    }
                    if (turmaAtualizada.getProfessor() != null) {
                        turmaExistente.setProfessor(turmaAtualizada.getProfessor());
                    }

                    return ResponseEntity.ok(turmaRepository.save(turmaExistente));
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Turma não encontrada"));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarTurma(@PathVariable Integer id) {
        turmaRepository.findById(id)
                .map(turma -> {
                    turmaRepository.delete(turma);
                    return true;
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Turma não encontrada"));
    }
}
