package com.javageorge.repositories;

import com.javageorge.entities.Prova;
import com.javageorge.entities.Turma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProvaRepository extends JpaRepository<Prova, Integer> {
    List<Prova> findByTurma(Turma turma);
}
