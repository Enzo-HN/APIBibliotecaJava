package com.aula.biblioteca.controllers;

import com.aula.biblioteca.entities.Emprestimo;
import com.aula.biblioteca.services.EmprestimoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/emprestimo")
public class EmprestimoController {

    @Autowired
    private EmprestimoService emprestimoService;

    @GetMapping
    public List<Emprestimo> getCadastrados() {
        return emprestimoService.getCadastrados();
    }

    @GetMapping("/em-aberto")
    public List<Emprestimo> getEmAberto() {
        return emprestimoService.getEmAberto();
    }

    @GetMapping("/{id}")
    public Emprestimo getById(@PathVariable Long id) {
        return emprestimoService.getById(id);
    }

    @PostMapping
    public Emprestimo addEmprestimo(@RequestBody Emprestimo emprestimo) {
        return emprestimoService.addEmprestimo(emprestimo);
    }

    @PutMapping("/{id}/devolver")
    public Emprestimo devolver(@PathVariable Long id) {
        return emprestimoService.devolver(id);
    }

    @DeleteMapping("/{id}")
    public void deleteEmprestimo(@PathVariable Long id) {
        emprestimoService.deleteEmprestimo(id);
    }
}
