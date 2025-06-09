package com.javageorge.controllers;

import com.javageorge.entities.*;
import com.javageorge.repositories.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/notas")
public class NotaController {

    @Autowired
    private NotaRepository notaRepository;

    @Autowired
    private MatriculaRepository matriculaRepository;

    @Autowired
    private ProvaRepository provaRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    @GetMapping
    public List<Nota> listarNotas() {
        return notaRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Nota> buscarNotaPorId(@PathVariable Integer id) {
        return notaRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Nota não encontrada"));
    }

    @GetMapping("/aluno/{alunoId}/turma/{turmaId}")
    public List<Nota> buscarNotasPorAlunoETurma(@PathVariable Integer alunoId, @PathVariable Integer turmaId) {
        // Buscar matrícula do aluno na turma
        Optional<Matricula> matriculaOpt = matriculaRepository.findByAlunoCodigoPessoaAndTurmaCodigoTurma(alunoId, turmaId);

        if (matriculaOpt.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Matrícula não encontrada para este aluno e turma");
        }

        return notaRepository.findByMatricula(matriculaOpt.get());
    }

    @GetMapping("/prova/{provaId}")
    public List<Nota> buscarNotasPorProva(@PathVariable Integer provaId) {
        Prova prova = provaRepository.findById(provaId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Prova não encontrada"));
        return notaRepository.findByProva(prova);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Nota lancarNota(@RequestBody @Valid Nota nota) {
        // Verificar se a matrícula existe
        if (nota.getMatricula() == null || nota.getMatricula().getCodigoMatricula() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Matrícula é obrigatória");
        }

        Matricula matricula = matriculaRepository.findById(nota.getMatricula().getCodigoMatricula())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Matrícula não encontrada"));
        nota.setMatricula(matricula);

        // Verificar se a prova existe
        if (nota.getProva() == null || nota.getProva().getCodigoProva() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Prova é obrigatória");
        }

        Prova prova = provaRepository.findById(nota.getProva().getCodigoProva())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Prova não encontrada"));
        nota.setProva(prova);

        // Verificar se o valor da nota é válido (entre 0 e 10)
        if (nota.getValor() < 0 || nota.getValor() > 10) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Valor da nota deve estar entre 0 e 10");
        }

        // Nota não tem campo professor, então não precisamos definir

        return notaRepository.save(nota);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Nota> atualizarNota(@PathVariable Integer id, @RequestBody @Valid Nota notaAtualizada) {
        return notaRepository.findById(id)
                .map(notaExistente -> {
                    // Não permitir alterar a matrícula ou a prova, apenas o valor da nota
                    if (notaAtualizada.getValor() < 0 || notaAtualizada.getValor() > 10) {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Valor da nota deve estar entre 0 e 10");
                    }

                    // Nota não tem campo professor, então não precisamos verificar

                    notaExistente.setValor(notaAtualizada.getValor());
                    return ResponseEntity.ok(notaRepository.save(notaExistente));
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Nota não encontrada"));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarNota(@PathVariable Integer id) {
        notaRepository.findById(id)
                .map(nota -> {
                    // Verificar se o professor que está deletando é o mesmo da turma/prova
                    // Normalmente essa verificação seria feita com autenticação e não no corpo do request

                    notaRepository.delete(nota);
                    return true;
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Nota não encontrada"));
    }
}
