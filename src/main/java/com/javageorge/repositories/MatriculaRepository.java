package com.javageorge.repositories;

import com.javageorge.entities.Aluno;
import com.javageorge.entities.Matricula;
import com.javageorge.entities.Turma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MatriculaRepository extends JpaRepository<Matricula, Integer> {
    List<Matricula> findByAluno(Aluno aluno);
    List<Matricula> findByTurma(Turma turma);
    Optional<Matricula> findByAlunoCodigoPessoaAndTurmaCodigoTurma(Integer alunoId, Integer turmaId);
}
