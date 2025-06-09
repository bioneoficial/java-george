package com.javageorge.services;

import com.javageorge.entities.Aluno;
import com.javageorge.repositories.AlunoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AlunoService {

    private final AlunoRepository alunoRepository;

    @Autowired
    public AlunoService(AlunoRepository alunoRepository) {
        this.alunoRepository = alunoRepository;
    }

    @Transactional(readOnly = true)
    public List<Aluno> findAll() {
        return alunoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Aluno findById(Integer id) {
        return alunoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Aluno não encontrado com ID: " + id));
    }

    @Transactional(readOnly = true)
    public Aluno findByMatriculaUnica(String matriculaUnica) {
        return alunoRepository.findByMatriculaUnica(matriculaUnica)
                .orElseThrow(() -> new EntityNotFoundException("Aluno não encontrado com matrícula: " + matriculaUnica));
    }

    @Transactional
    public Aluno save(Aluno aluno) {
        return alunoRepository.save(aluno);
    }

    @Transactional
    public Aluno update(Integer id, Aluno alunoAtualizado) {
        Aluno alunoExistente = findById(id);

        // Atualiza os campos do aluno existente
        alunoExistente.setNome(alunoAtualizado.getNome());
        alunoExistente.setCpf(alunoAtualizado.getCpf());
        alunoExistente.setEmail(alunoAtualizado.getEmail());
        alunoExistente.setEndereco(alunoAtualizado.getEndereco());
        alunoExistente.setTelefone(alunoAtualizado.getTelefone());
        alunoExistente.setMatriculaUnica(alunoAtualizado.getMatriculaUnica());
        alunoExistente.setDataIngresso(alunoAtualizado.getDataIngresso());

        return alunoRepository.save(alunoExistente);
    }

    @Transactional
    public void delete(Integer id) {
        Aluno aluno = findById(id);
        alunoRepository.delete(aluno);
    }
}
