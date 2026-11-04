package com.aula.biblioteca.cep;

import com.aula.biblioteca.logging.ILogger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import java.util.HashMap;
import java.util.Map;
@Component
@Primary
public class ConsultaCepProxy implements ConsultaCep {

    private final ConsultaCep provedor;
    private final ILogger logger;

    private final Map<String, Endereco> cache = new HashMap<>();

    public ConsultaCepProxy(
            @Qualifier("viaCepAdapter") ViaCepAdapter viaCep,
            @Qualifier("brasilApiAdapter") BrasilApiAdapter brasilApi,
            @Value("${cep.provedor:viacep}") String tipoProvedor,
            ILogger logger) {

        this.logger = logger;

        if (tipoProvedor.equalsIgnoreCase("viacep")) {
            this.provedor = viaCep;

        } else if (tipoProvedor.equalsIgnoreCase("brasilapi")) {
            this.provedor = brasilApi;

        } else {
            throw new IllegalArgumentException(
                    "Provedor invalido: " + tipoProvedor
            );
        }

        logger.info("PROXY Provedor selecionado: " + tipoProvedor);
    }

    @Override
    public Endereco buscar(String cep) {

        if (cep == null || !cep.matches("\\d{8}")) {
            logger.erro("PROXY CEP invalido: " + cep);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O CEP deve conter exatamente 8 digitos");
        }
        if (cache.containsKey(cep)) {

            logger.info("PROXY CEP encontrado no cache: " + cep);

            return cache.get(cep);
        }

        logger.info("PROXY Consultando provedor: " + cep);
        Endereco endereco = provedor.buscar(cep);
        cache.put(cep, endereco);
        logger.info("PROXY CEP armazenado no cache: " + cep);

        return endereco;
    }
}