package com.aula.biblioteca.repositories;

import com.aula.biblioteca.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IJpaUsuarioRepo extends JpaRepository<Usuario, Long> {
}
