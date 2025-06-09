package com.javageorge.controllers;

import com.javageorge.entities.Aluno;
import com.javageorge.services.AlunoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/alunos")
@Tag(name = "Alunos", description = "Endpoints para gerenciamento de alunos")
public class AlunoController {

    private final AlunoService alunoService;

    @Autowired
    public AlunoController(AlunoService alunoService) {
        this.alunoService = alunoService;
    }

    @GetMapping
    @Operation(summary = "Listar todos os alunos", description = "Retorna uma lista com todos os alunos cadastrados")
    public ResponseEntity<List<Aluno>> findAll() {
        List<Aluno> alunos = alunoService.findAll();
        return ResponseEntity.ok(alunos);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar aluno por ID", description = "Retorna um aluno pelo seu ID")
    public ResponseEntity<Aluno> findById(@PathVariable Integer id) {
        try {
            Aluno aluno = alunoService.findById(id);
            return ResponseEntity.ok(aluno);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/matricula/{matricula}")
    @Operation(summary = "Buscar aluno por matrícula", description = "Retorna um aluno pela sua matrícula única")
    public ResponseEntity<Aluno> findByMatriculaUnica(@PathVariable String matricula) {
        try {
            Aluno aluno = alunoService.findByMatriculaUnica(matricula);
            return ResponseEntity.ok(aluno);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Cadastrar novo aluno", description = "Cadastra um novo aluno no sistema")
    public ResponseEntity<Aluno> create(@RequestBody Aluno aluno) {
        Aluno novoAluno = alunoService.save(aluno);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoAluno);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar aluno", description = "Atualiza os dados de um aluno existente")
    public ResponseEntity<Aluno> update(@PathVariable Integer id, @RequestBody Aluno aluno) {
        try {
            Aluno alunoAtualizado = alunoService.update(id, aluno);
            return ResponseEntity.ok(alunoAtualizado);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remover aluno", description = "Remove um aluno do sistema")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        try {
            alunoService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
