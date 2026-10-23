package com.aula.biblioteca.repositories;

import com.aula.biblioteca.entities.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IJpaLivroRepo extends JpaRepository<Livro, Long> {
}
