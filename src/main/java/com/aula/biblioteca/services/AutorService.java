package com.aula.biblioteca.services;

import com.aula.biblioteca.entities.Autor;
import com.aula.biblioteca.repositories.IJpaAutorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AutorService {

    @Autowired
    private IJpaAutorRepo autorRepo;

    public List<Autor> getCadastrados() {
        return autorRepo.findAll();
    }

    public Autor getById(Long id) {
        return autorRepo.findById(id)
                .orElseThrow();
    }

    public Autor addAutor(Autor autor) {
        autor.setId(null);
        return autorRepo.save(autor);
    }

    public Autor updateAutor(Long id, Autor autor) {
        Autor autorDoBanco = getById(id);
        autorDoBanco.setNome(autor.getNome());
        autorDoBanco.setNacionalidade(autor.getNacionalidade());
        return autorRepo.save(autorDoBanco);
    }

    public void deleteAutor(Long id) {
        getById(id);
        autorRepo.deleteById(id);
    }
}
