package com.aula.biblioteca.state;

import com.aula.biblioteca.entities.Emprestimo;
import com.aula.biblioteca.strategy.CalculadoraMulta;

import java.time.LocalDate;

public interface EstadoEmprestimo {
    void devolver(Emprestimo emprestimo, CalculadoraMulta calculadora, LocalDate hoje);
}
