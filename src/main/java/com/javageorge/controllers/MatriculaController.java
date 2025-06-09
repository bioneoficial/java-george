package com.javageorge.controllers;

import com.javageorge.entities.Aluno;
import com.javageorge.entities.Matricula;
import com.javageorge.entities.Turma;
import com.javageorge.repositories.AlunoRepository;
import com.javageorge.repositories.MatriculaRepository;
import com.javageorge.repositories.TurmaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/matriculas")
public class MatriculaController {

    @Autowired
    private MatriculaRepository matriculaRepository;

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private TurmaRepository turmaRepository;

    @GetMapping
    public List<Matricula> listarMatriculas() {
        return matriculaRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Matricula> buscarMatriculaPorId(@PathVariable Integer id) {
        return matriculaRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Matrícula não encontrada"));
    }

    @GetMapping("/aluno/{alunoId}")
    public List<Matricula> buscarMatriculasPorAluno(@PathVariable Integer alunoId) {
        Aluno aluno = alunoRepository.findById(alunoId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aluno não encontrado"));
        return matriculaRepository.findByAluno(aluno);
    }

    @GetMapping("/turma/{turmaId}")
    public List<Matricula> buscarMatriculasPorTurma(@PathVariable Integer turmaId) {
        Turma turma = turmaRepository.findById(turmaId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Turma não encontrada"));
        return matriculaRepository.findByTurma(turma);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Matricula realizarMatricula(@RequestBody @Valid Matricula matricula) {
        // Verificar se o aluno existe
        if (matricula.getAluno() == null || matricula.getAluno().getCodigoPessoa() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Aluno é obrigatório");
        }

        Aluno aluno = alunoRepository.findById(matricula.getAluno().getCodigoPessoa())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aluno não encontrado"));
        matricula.setAluno(aluno);

        // Verificar se a turma existe
        if (matricula.getTurma() == null || matricula.getTurma().getCodigoTurma() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Turma é obrigatória");
        }

        Turma turma = turmaRepository.findById(matricula.getTurma().getCodigoTurma())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Turma não encontrada"));
        matricula.setTurma(turma);

        // Verificar se a turma está lotada
        if (turma.estaLotada()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Turma está lotada");
        }

        // Verificar se o aluno já está matriculado nesta turma
        if (matriculaRepository.findByAlunoCodigoPessoaAndTurmaCodigoTurma(aluno.getCodigoPessoa(), turma.getCodigoTurma()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Aluno já matriculado nesta turma");
        }

        // Definir a data da matrícula para hoje se não for informada
        if (matricula.getDataMatricula() == null) {
            matricula.setDataMatricula(LocalDate.now());
        }

        // Definir o status como "ATIVA" se não for informado
        if (matricula.getStatus() == null) {
            matricula.setStatus("ATIVA");
        }

        // Salvar a matrícula
        return matriculaRepository.save(matricula);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Matricula> atualizarMatricula(@PathVariable Integer id, @RequestBody @Valid Matricula matriculaAtualizada) {
        return matriculaRepository.findById(id)
                .map(matriculaExistente -> {
                    // Atualizar apenas o status e a data de cancelamento
                    if (matriculaAtualizada.getStatus() != null) {
                        matriculaExistente.setStatus(matriculaAtualizada.getStatus());

                        // Se o status for "CANCELADA", definir a data de cancelamento para hoje se não for informada
                        if ("CANCELADA".equals(matriculaAtualizada.getStatus()) && matriculaExistente.getDataCancelamento() == null) {
                            matriculaExistente.setDataCancelamento(LocalDate.now());
                        }
                    }

                    if (matriculaAtualizada.getDataCancelamento() != null) {
                        matriculaExistente.setDataCancelamento(matriculaAtualizada.getDataCancelamento());
                    }

                    return ResponseEntity.ok(matriculaRepository.save(matriculaExistente));
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Matrícula não encontrada"));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelarMatricula(@PathVariable Integer id) {
        matriculaRepository.findById(id)
                .map(matricula -> {
                    // Em vez de deletar, apenas cancelar a matrícula
                    matricula.setStatus("CANCELADA");
                    matricula.setDataCancelamento(LocalDate.now());
                    matriculaRepository.save(matricula);
                    return true;
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Matrícula não encontrada"));
    }
}
