package com.aula.biblioteca.controllers;

import com.aula.biblioteca.entities.Livro;
import com.aula.biblioteca.services.LivroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/livro")
public class LivroController {

    @Autowired
    private LivroService livroService;

    @GetMapping
    public List<Livro> getCadastrados() {
        return livroService.getCadastrados();
    }

    @GetMapping("/{id}")
    public Livro getById(@PathVariable Long id) {
        return livroService.getById(id);
    }

    @PostMapping
    public Livro addLivro(@RequestBody Livro livro) {
        return livroService.addLivro(livro);
    }

    @PutMapping("/{id}")
    public Livro updateLivro(@PathVariable Long id, @RequestBody Livro livro) {
        return livroService.updateLivro(id, livro);
    }

    @DeleteMapping("/{id}")
    public void deleteLivro(@PathVariable Long id) {
        livroService.deleteLivro(id);
    }
}
