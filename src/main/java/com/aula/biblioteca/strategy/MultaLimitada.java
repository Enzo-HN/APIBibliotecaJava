package com.aula.biblioteca.strategy;

import java.math.BigDecimal;

public class MultaLimitada implements CalculadoraMulta {

    @Override
    public BigDecimal calcular(long diasAtraso) {
        BigDecimal multa = BigDecimal.valueOf(2).multiply(BigDecimal.valueOf(diasAtraso));
        return multa.min(BigDecimal.valueOf(20));
    }
}
