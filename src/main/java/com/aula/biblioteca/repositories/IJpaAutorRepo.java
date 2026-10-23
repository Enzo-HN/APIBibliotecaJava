package com.aula.biblioteca.repositories;

import com.aula.biblioteca.entities.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IJpaAutorRepo extends JpaRepository<Autor, Long> {
}
