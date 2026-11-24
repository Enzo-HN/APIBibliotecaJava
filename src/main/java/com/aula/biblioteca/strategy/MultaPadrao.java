package com.aula.biblioteca.strategy;

import java.math.BigDecimal;

public class MultaPadrao implements CalculadoraMulta {

    @Override
    public BigDecimal calcular(long diasAtraso) {
        return BigDecimal.valueOf(2).multiply(BigDecimal.valueOf(diasAtraso));
    }
}
