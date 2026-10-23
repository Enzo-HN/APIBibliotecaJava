package com.aula.biblioteca.repositories;

import com.aula.biblioteca.entities.Emprestimo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IJpaEmprestimoRepo extends JpaRepository<Emprestimo, Long> {

    List<Emprestimo> findByDataDevolucaoIsNull();
}
