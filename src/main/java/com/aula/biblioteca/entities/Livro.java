package com.aula.biblioteca.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Livro {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String titulo;

    @Column(unique = true)
    private String isbn;

    private Integer anoPublicacao;

    private boolean disponivel = true;

    @ManyToOne
    @JoinColumn(name = "autor_id")
    private Autor autor;
}
