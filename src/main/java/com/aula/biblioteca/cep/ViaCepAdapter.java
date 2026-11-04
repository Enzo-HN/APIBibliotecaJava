package com.aula.biblioteca.cep;

import com.aula.biblioteca.logging.ILogger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

@Component
public class ViaCepAdapter implements ConsultaCep {

    private final RestTemplate restTemplate = new RestTemplate();
    private final ILogger logger;

    public ViaCepAdapter(ILogger logger) {
        this.logger = logger;
    }

    @Override
    public Endereco buscar(String cep) {

        String url = "https://viacep.com.br/ws/" + cep + "/json/";

        logger.info("ADAPTER Consultando ViaCEP: " + cep);

        try {
            Endereco endereco = restTemplate.getForObject(url, Endereco.class);
            if (endereco == null ||
                    Boolean.TRUE.equals(endereco.getErro())) {

                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "CEP nao encontrado");
            }
            return endereco;

        } catch (ResponseStatusException e) {
            throw e;

        } catch (RestClientException e) {
            logger.erro("ADAPTER Erro ao consultar ViaCEP");

            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Erro ao consultar ViaCEP", e);
        }
    }
}