package com.javageorge.services;

import com.javageorge.entities.Professor;
import com.javageorge.repositories.ProfessorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProfessorService {

    private final ProfessorRepository professorRepository;

    @Autowired
    public ProfessorService(ProfessorRepository professorRepository) {
        this.professorRepository = professorRepository;
    }

    @Transactional(readOnly = true)
    public List<Professor> findAll() {
        return professorRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Professor findById(Integer id) {
        return professorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Professor não encontrado com ID: " + id));
    }

    @Transactional(readOnly = true)
    public List<Professor> findByEspecialidade(String especialidade) {
        return professorRepository.findByEspecialidade(especialidade);
    }

    @Transactional
    public Professor save(Professor professor) {
        return professorRepository.save(professor);
    }

    @Transactional
    public Professor update(Integer id, Professor professorAtualizado) {
        Professor professorExistente = findById(id);

        // Atualiza os campos do professor existente
        professorExistente.setNome(professorAtualizado.getNome());
        professorExistente.setCpf(professorAtualizado.getCpf());
        professorExistente.setEmail(professorAtualizado.getEmail());
        professorExistente.setEndereco(professorAtualizado.getEndereco());
        professorExistente.setTelefone(professorAtualizado.getTelefone());
        professorExistente.setEspecialidade(professorAtualizado.getEspecialidade());
        professorExistente.setTituloAcademico(professorAtualizado.getTituloAcademico());

        return professorRepository.save(professorExistente);
    }

    @Transactional
    public void delete(Integer id) {
        Professor professor = findById(id);
        professorRepository.delete(professor);
    }
}
