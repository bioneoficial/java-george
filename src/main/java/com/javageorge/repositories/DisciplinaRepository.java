package com.javageorge.repositories;

import com.javageorge.entities.Disciplina;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DisciplinaRepository extends JpaRepository<Disciplina, Integer> {
    Optional<Disciplina> findByNome(String nome);
    List<Disciplina> findByCargaHoraria(Integer cargaHoraria);
}
