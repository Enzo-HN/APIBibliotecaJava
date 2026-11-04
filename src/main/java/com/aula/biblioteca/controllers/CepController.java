package com.aula.biblioteca.controllers;

import com.aula.biblioteca.cep.ConsultaCep;
import com.aula.biblioteca.cep.Endereco;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
// eu fiz um controller mas depois coloquei no usuario então ficou meio sem sentido. ficou aqui só para teste mesmo
@RestController
@RequestMapping("/cep")
public class CepController {

    private final ConsultaCep consultaCep;

    public CepController(ConsultaCep consultaCep) {
        this.consultaCep = consultaCep;
    }

    @GetMapping("/{cep}")
    public Endereco buscarCep(@PathVariable String cep) {
        return consultaCep.buscar(cep);
    }
}