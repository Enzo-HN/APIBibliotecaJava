
package com.aula.biblioteca.services;

import com.aula.biblioteca.entities.Autor;
import com.aula.biblioteca.entities.Livro;
import com.aula.biblioteca.logging.ILogger;
import com.aula.biblioteca.repositories.IJpaAutorRepo;
import com.aula.biblioteca.repositories.IJpaLivroRepo;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class LivroService {

    private final IJpaLivroRepo livroRepo;
    private final IJpaAutorRepo autorRepo;
    private final ILogger log;

    public LivroService(
            IJpaLivroRepo livroRepo,
            IJpaAutorRepo autorRepo,
            ILogger log) {
        this.livroRepo = livroRepo;
        this.autorRepo = autorRepo;
        this.log = log;
    }

    public List<Livro> getCadastrados() {
        
        return livroRepo.findAll();
    }

    public Livro getById(Long id) {
        return livroRepo.findById(id)
                .orElseThrow(() -> {
                    log.erro("Livro não encontrado. ID: " + id);
                    return new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            "Livro não encontrado"
                    );
                });
    }

    public Livro addLivro(Livro livro) {
        try {
            livro.setId(null);
            livro.setDisponivel(true);
            livro.setAutor(buscarAutor(livro.getAutor()));

            Livro livroSalvo = livroRepo.save(livro);

            return livroSalvo;

        } catch (ResponseStatusException | IllegalArgumentException e) {
            throw e;
        } catch (RuntimeException e) {
            log.erro("Erro ao cadastrar livro: " + e.getMessage());
            throw e;
        }
    }

    public Livro updateLivro(Long id, Livro livro) {
        try {
            Livro livroDoBanco = getById(id);

            livroDoBanco.setTitulo(livro.getTitulo());
            livroDoBanco.setIsbn(livro.getIsbn());
            livroDoBanco.setAnoPublicacao(livro.getAnoPublicacao());
            livroDoBanco.setAutor(buscarAutor(livro.getAutor()));

            Livro livroAtualizado = livroRepo.save(livroDoBanco);

            return livroAtualizado;

        } catch (ResponseStatusException | IllegalArgumentException e) {
            throw e;
        } catch (RuntimeException e) {
            log.erro("Erro ao atualizar livro ID " + id
                    + ": " + e.getMessage());
            throw e;
        }
    }

    public void deleteLivro(Long id) {
        getById(id);
        livroRepo.deleteById(id);
    }

    private Autor buscarAutor(Autor autor) {
        if (autor == null || autor.getId() == null) {
            log.erro("Tentativa de cadastrar livro sem informar o autor.");
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Informe o autor do livro (autor.id)"
            );
        }

        return autorRepo.findById(autor.getId())
                .orElseThrow(() -> {
                    log.erro("Autor não encontrado. ID: "
                            + autor.getId());
                    return new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            "Autor não encontrado"
                    );
                });
    }
}
