package com.aula.biblioteca.services;

import com.aula.biblioteca.entities.Autor;
import com.aula.biblioteca.entities.Livro;
import com.aula.biblioteca.repositories.IJpaAutorRepo;
import com.aula.biblioteca.repositories.IJpaLivroRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LivroService {

    @Autowired
    private IJpaLivroRepo livroRepo;

    @Autowired
    private IJpaAutorRepo autorRepo;

    public List<Livro> getCadastrados() {
        return livroRepo.findAll();
    }

    public Livro getById(Long id) {
        return livroRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Livro não encontrado"));
    }

    public Livro addLivro(Livro livro) {
        livro.setId(null);
        livro.setDisponivel(true);
        livro.setAutor(buscarAutor(livro.getAutor()));
        return livroRepo.save(livro);
    }

    public Livro updateLivro(Long id, Livro livro) {
        Livro livroDoBanco = getById(id);
        livroDoBanco.setTitulo(livro.getTitulo());
        livroDoBanco.setIsbn(livro.getIsbn());
        livroDoBanco.setAnoPublicacao(livro.getAnoPublicacao());
        livroDoBanco.setAutor(buscarAutor(livro.getAutor()));
        return livroRepo.save(livroDoBanco);
    }

    public void deleteLivro(Long id) {
        getById(id);
        livroRepo.deleteById(id);
    }

    private Autor buscarAutor(Autor autor) {
        if (autor == null || autor.getId() == null) {
            throw new IllegalArgumentException("Informe o autor do livro (autor.id)");
        }
        return autorRepo.findById(autor.getId())
                .orElseThrow(() -> new IllegalArgumentException("Autor não encontrado"));
    }
}
