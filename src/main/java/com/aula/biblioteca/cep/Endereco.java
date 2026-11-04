package com.aula.biblioteca.cep;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@Embeddable
@JsonIgnoreProperties(ignoreUnknown = true)
public class Endereco {

    private String cep;

    @JsonAlias("street")
    private String logradouro;

    private String complemento;

    @JsonAlias("neighborhood")
    private String bairro;

    @JsonAlias({"localidade", "city"})
    private String cidade;

    @JsonAlias({"uf", "state"})
    private String estado;

    @Transient
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Boolean erro;
}