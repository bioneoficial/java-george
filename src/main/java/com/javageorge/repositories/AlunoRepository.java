package com.javageorge.repositories;

import com.javageorge.entities.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Integer> {
    Optional<Aluno> findByMatriculaUnica(String matriculaUnica);
    Optional<Aluno> findByCpf(String cpf);
    Optional<Aluno> findByEmail(String email);
}
