package com.aula.biblioteca.controllers;

import com.aula.biblioteca.entities.Livro;
import com.aula.biblioteca.logging.ILogger;
import com.aula.biblioteca.services.LivroService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/livro")
@RequiredArgsConstructor
public class LivroController {
    @Autowired
    private LivroService livroService;

    private final ILogger log;

    @GetMapping
    public List<Livro> getCadastrados() {
        log.debug("GET /api/livro recebido");
        List<Livro> livros = livroService.getCadastrados();
        log.info("GET /api/livro -> 200 (" + livros.size() + " livros)");
        return livros;
    }

    @GetMapping("/{id}")
    public Livro getById(@PathVariable Long id) {
        log.debug("GET /api/livro/" + id + " recebido");
        Livro livro = livroService.getById(id);
        log.info("GET /api/livro/" + id + " -> 200");
        return livro;
    }

    @PostMapping
    public ResponseEntity<Livro> addLivro(@RequestBody Livro livro) {
        log.debug("POST /api/livro recebido");
        Livro criado = livroService.addLivro(livro);
        log.info("Livro cadastrado: id=" + criado.getId());

        return ResponseEntity
                .created(URI.create("/api/livro/" + criado.getId()))
                .body(criado);
    }

    @PutMapping("/{id}")
    public Livro updateLivro(@PathVariable Long id, @RequestBody Livro livro) {
        log.debug("PUT /api/livro/" + id + " recebido");
        Livro atualizado = livroService.updateLivro(id, livro);
        log.info("PUT /api/livro/" + id + " -> 200");
        return atualizado;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLivro(@PathVariable Long id) {
        log.debug("DELETE /api/livro/" + id);
        livroService.deleteLivro(id);
        log.info("Livro excluído: id=" + id);

        return ResponseEntity.noContent().build();
    }
}