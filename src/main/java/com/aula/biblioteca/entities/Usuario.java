package com.aula.biblioteca.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.aula.biblioteca.cep.Endereco;
import jakarta.persistence.Embedded;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nome;

    @Column(unique = true)
    private String cpf;

    private String email;

    @Embedded
    private Endereco endereco;
}
