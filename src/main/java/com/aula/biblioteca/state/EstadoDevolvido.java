package com.aula.biblioteca.state;

import com.aula.biblioteca.entities.Emprestimo;
import com.aula.biblioteca.strategy.CalculadoraMulta;

import java.time.LocalDate;

public class EstadoDevolvido implements EstadoEmprestimo {

    @Override
    public void devolver(Emprestimo emprestimo, CalculadoraMulta calculadora, LocalDate hoje) {
        throw new IllegalStateException("Este empréstimo já foi devolvido");
    }
}
