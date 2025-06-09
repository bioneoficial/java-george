package com.javageorge.controllers;

import com.javageorge.entities.Disciplina;
import com.javageorge.services.DisciplinaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/disciplinas")
@Tag(name = "Disciplinas", description = "Endpoints para gerenciamento de disciplinas")
public class DisciplinaController {

    private final DisciplinaService disciplinaService;

    @Autowired
    public DisciplinaController(DisciplinaService disciplinaService) {
        this.disciplinaService = disciplinaService;
    }

    @GetMapping
    @Operation(summary = "Listar todas as disciplinas", description = "Retorna uma lista com todas as disciplinas cadastradas")
    public ResponseEntity<List<Disciplina>> findAll() {
        List<Disciplina> disciplinas = disciplinaService.findAll();
        return ResponseEntity.ok(disciplinas);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar disciplina por ID", description = "Retorna uma disciplina pelo seu ID")
    public ResponseEntity<Disciplina> findById(@PathVariable Integer id) {
        try {
            Disciplina disciplina = disciplinaService.findById(id);
            return ResponseEntity.ok(disciplina);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/nome/{nome}")
    @Operation(summary = "Buscar disciplina por nome", description = "Retorna uma disciplina pelo seu nome")
    public ResponseEntity<Disciplina> findByNome(@PathVariable String nome) {
        try {
            Disciplina disciplina = disciplinaService.findByNome(nome);
            return ResponseEntity.ok(disciplina);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Cadastrar nova disciplina", description = "Cadastra uma nova disciplina no sistema")
    public ResponseEntity<Disciplina> create(@RequestBody Disciplina disciplina) {
        Disciplina novaDisciplina = disciplinaService.save(disciplina);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaDisciplina);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar disciplina", description = "Atualiza os dados de uma disciplina existente")
    public ResponseEntity<Disciplina> update(@PathVariable Integer id, @RequestBody Disciplina disciplina) {
        try {
            Disciplina disciplinaAtualizada = disciplinaService.update(id, disciplina);
            return ResponseEntity.ok(disciplinaAtualizada);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remover disciplina", description = "Remove uma disciplina do sistema")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        try {
            disciplinaService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{disciplinaId}/prerequisitos/{preRequisitoId}")
    @Operation(summary = "Adicionar pré-requisito", description = "Adiciona uma disciplina como pré-requisito de outra")
    public ResponseEntity<Void> addPreRequisito(
            @PathVariable Integer disciplinaId,
            @PathVariable Integer preRequisitoId) {
        try {
            disciplinaService.addPreRequisito(disciplinaId, preRequisitoId);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{disciplinaId}/prerequisitos/{preRequisitoId}")
    @Operation(summary = "Remover pré-requisito", description = "Remove uma disciplina como pré-requisito de outra")
    public ResponseEntity<Void> removePreRequisito(
            @PathVariable Integer disciplinaId,
            @PathVariable Integer preRequisitoId) {
        try {
            disciplinaService.removePreRequisito(disciplinaId, preRequisitoId);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
