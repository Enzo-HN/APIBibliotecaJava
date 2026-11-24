package com.aula.biblioteca.state;

import com.aula.biblioteca.entities.Emprestimo;
import com.aula.biblioteca.strategy.CalculadoraMulta;

import java.math.BigDecimal;
import java.time.LocalDate;

public class EstadoAtivo implements EstadoEmprestimo {

    @Override
    public void devolver(Emprestimo emprestimo, CalculadoraMulta calculadora, LocalDate hoje) {
        emprestimo.setDataDevolucao(hoje);
        emprestimo.setMulta(BigDecimal.ZERO);
    }
}
