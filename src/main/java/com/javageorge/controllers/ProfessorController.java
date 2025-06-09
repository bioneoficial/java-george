package com.javageorge.controllers;

import com.javageorge.entities.Professor;
import com.javageorge.services.ProfessorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/professores")
@Tag(name = "Professores", description = "Endpoints para gerenciamento de professores")
public class ProfessorController {

    private final ProfessorService professorService;

    @Autowired
    public ProfessorController(ProfessorService professorService) {
        this.professorService = professorService;
    }

    @GetMapping
    @Operation(summary = "Listar todos os professores", description = "Retorna uma lista com todos os professores cadastrados")
    public ResponseEntity<List<Professor>> findAll() {
        List<Professor> professores = professorService.findAll();
        return ResponseEntity.ok(professores);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar professor por ID", description = "Retorna um professor pelo seu ID")
    public ResponseEntity<Professor> findById(@PathVariable Integer id) {
        try {
            Professor professor = professorService.findById(id);
            return ResponseEntity.ok(professor);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/especialidade/{especialidade}")
    @Operation(summary = "Buscar professores por especialidade", description = "Retorna uma lista de professores de uma determinada especialidade")
    public ResponseEntity<List<Professor>> findByEspecialidade(@PathVariable String especialidade) {
        List<Professor> professores = professorService.findByEspecialidade(especialidade);
        return ResponseEntity.ok(professores);
    }

    @PostMapping
    @Operation(summary = "Cadastrar novo professor", description = "Cadastra um novo professor no sistema")
    public ResponseEntity<Professor> create(@RequestBody Professor professor) {
        Professor novoProfessor = professorService.save(professor);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoProfessor);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar professor", description = "Atualiza os dados de um professor existente")
    public ResponseEntity<Professor> update(@PathVariable Integer id, @RequestBody Professor professor) {
        try {
            Professor professorAtualizado = professorService.update(id, professor);
            return ResponseEntity.ok(professorAtualizado);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remover professor", description = "Remove um professor do sistema")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        try {
            professorService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
