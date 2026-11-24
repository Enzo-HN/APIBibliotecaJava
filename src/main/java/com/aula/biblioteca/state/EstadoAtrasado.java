package com.aula.biblioteca.state;

import com.aula.biblioteca.entities.Emprestimo;
import com.aula.biblioteca.strategy.CalculadoraMulta;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class EstadoAtrasado implements EstadoEmprestimo {

    @Override
    public void devolver(Emprestimo emprestimo, CalculadoraMulta calculadora, LocalDate hoje) {
        long diasAtraso = ChronoUnit.DAYS.between(
                emprestimo.getDataPrevistaDevolucao(), hoje);

        emprestimo.setMulta(calculadora.calcular(diasAtraso));
        emprestimo.setDataDevolucao(hoje);
    }
}
