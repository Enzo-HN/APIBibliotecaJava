package com.aula.biblioteca.controllers;

import com.aula.biblioteca.entities.Autor;
import com.aula.biblioteca.services.AutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/autor")
public class AutorController {
    
    @Autowired
    private AutorService autorService;

    @GetMapping
    public List<Autor> getCadastrados() {
        return autorService.getCadastrados();
    }

    @GetMapping("/{id}")
    public Autor getById(@PathVariable Long id) {
        return autorService.getById(id);
    }

    @PostMapping
    public Autor addAutor(@RequestBody Autor autor) {
        return autorService.addAutor(autor);
    }

    @PutMapping("/{id}")
    public Autor updateAutor(@PathVariable Long id, @RequestBody Autor autor) {
        return autorService.updateAutor(id, autor);
    }

    @DeleteMapping("/{id}")
    public void deleteAutor(@PathVariable Long id) {
        autorService.deleteAutor(id);
    }
}