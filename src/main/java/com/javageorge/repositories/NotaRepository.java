package com.javageorge.repositories;

import com.javageorge.entities.Matricula;
import com.javageorge.entities.Nota;
import com.javageorge.entities.Prova;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotaRepository extends JpaRepository<Nota, Integer> {
    List<Nota> findByMatricula(Matricula matricula);
    List<Nota> findByProva(Prova prova);
}
