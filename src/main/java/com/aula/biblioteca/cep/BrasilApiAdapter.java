package com.aula.biblioteca.cep;

import com.aula.biblioteca.logging.ILogger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

@Component
public class BrasilApiAdapter implements ConsultaCep {

    private final RestTemplate restTemplate = new RestTemplate();
    private final ILogger logger;

    public BrasilApiAdapter(ILogger logger) {
        this.logger = logger;
    }

    @Override
    public Endereco buscar(String cep) {

        String url = "https://brasilapi.com.br/api/cep/v1/" + cep;

        logger.info("Consultando BrasilAPI: " + cep);

        try {
            Endereco endereco = restTemplate.getForObject(
                    url, Endereco.class
            );

            if (endereco == null) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_GATEWAY,
                        "Resposta vazia da BrasilAPI"
                );
            }

            return endereco;

        } catch (HttpClientErrorException.NotFound e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "CEP nao encontrado"
            );

        } catch (ResponseStatusException e) {
            throw e;

        } catch (RestClientException e) {
            logger.erro("[ADAPTER] Erro ao consultar BrasilAPI");

            throw new ResponseStatusException(
                    HttpStatus.BAD_GATEWAY,
                    "Erro ao consultar BrasilAPI", e
            );
        }
    }
}