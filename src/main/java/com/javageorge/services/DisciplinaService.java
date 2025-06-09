package com.javageorge.services;

import com.javageorge.entities.Disciplina;
import com.javageorge.repositories.DisciplinaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DisciplinaService {

    private final DisciplinaRepository disciplinaRepository;

    @Autowired
    public DisciplinaService(DisciplinaRepository disciplinaRepository) {
        this.disciplinaRepository = disciplinaRepository;
    }

    @Transactional(readOnly = true)
    public List<Disciplina> findAll() {
        return disciplinaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Disciplina findById(Integer id) {
        return disciplinaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Disciplina não encontrada com ID: " + id));
    }

    @Transactional(readOnly = true)
    public Disciplina findByNome(String nome) {
        return disciplinaRepository.findByNome(nome)
                .orElseThrow(() -> new EntityNotFoundException("Disciplina não encontrada com nome: " + nome));
    }

    @Transactional
    public Disciplina save(Disciplina disciplina) {
        return disciplinaRepository.save(disciplina);
    }

    @Transactional
    public Disciplina update(Integer id, Disciplina disciplinaAtualizada) {
        Disciplina disciplinaExistente = findById(id);

        // Atualiza os campos da disciplina existente
        disciplinaExistente.setCodigo(disciplinaAtualizada.getCodigo());
        disciplinaExistente.setNome(disciplinaAtualizada.getNome());
        disciplinaExistente.setCargaHoraria(disciplinaAtualizada.getCargaHoraria());
        disciplinaExistente.setEmenta(disciplinaAtualizada.getEmenta());

        return disciplinaRepository.save(disciplinaExistente);
    }

    @Transactional
    public void delete(Integer id) {
        Disciplina disciplina = findById(id);
        disciplinaRepository.delete(disciplina);
    }

    @Transactional
    public void addPreRequisito(Integer disciplinaId, Integer preRequisitoId) {
        Disciplina disciplina = findById(disciplinaId);
        Disciplina preRequisito = findById(preRequisitoId);

        disciplina.addPreRequisito(preRequisito);
        disciplinaRepository.save(disciplina);
    }

    @Transactional
    public void removePreRequisito(Integer disciplinaId, Integer preRequisitoId) {
        Disciplina disciplina = findById(disciplinaId);
        Disciplina preRequisito = findById(preRequisitoId);

        disciplina.removePreRequisito(preRequisito);
        disciplinaRepository.save(disciplina);
    }
}
