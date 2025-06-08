package com.javageorge.repositories;

import com.javageorge.entities.Disciplina;
import com.javageorge.entities.Professor;
import com.javageorge.entities.Turma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TurmaRepository extends JpaRepository<Turma, Integer> {
    List<Turma> findByPeriodoLetivo(String periodoLetivo);
    List<Turma> findByProfessor(Professor professor);
    List<Turma> findByDisciplina(Disciplina disciplina);
}
