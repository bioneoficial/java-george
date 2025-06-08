package com.javageorge.repositories;

import com.javageorge.entities.Professor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Integer> {
    Optional<Professor> findByCpf(String cpf);
    Optional<Professor> findByEmail(String email);
    List<Professor> findByEspecialidade(String especialidade);
}
