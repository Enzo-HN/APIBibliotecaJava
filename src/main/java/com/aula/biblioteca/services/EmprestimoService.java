package com.aula.biblioteca.services;

import com.aula.biblioteca.entities.Emprestimo;
import com.aula.biblioteca.entities.Livro;
import com.aula.biblioteca.entities.Usuario;
import com.aula.biblioteca.repositories.IJpaEmprestimoRepo;
import com.aula.biblioteca.repositories.IJpaLivroRepo;
import com.aula.biblioteca.repositories.IJpaUsuarioRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class EmprestimoService {

    private static final int PRAZO_EM_DIAS = 14;

    @Autowired
    private IJpaEmprestimoRepo emprestimoRepo;

    @Autowired
    private IJpaLivroRepo livroRepo;

    @Autowired
    private IJpaUsuarioRepo usuarioRepo;

    public List<Emprestimo> getCadastrados() {
        return emprestimoRepo.findAll();
    }

    public List<Emprestimo> getEmAberto() {
        return emprestimoRepo.findByDataDevolucaoIsNull();
    }

    public Emprestimo getById(Long id) {
        return emprestimoRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Empréstimo não encontrado"));
    }


    @Transactional
    public Emprestimo addEmprestimo(Emprestimo pedido) {
        if (pedido.getLivro() == null || pedido.getLivro().getId() == null
                || pedido.getUsuario() == null || pedido.getUsuario().getId() == null) {
            throw new IllegalArgumentException("Informe livro.id e usuario.id");
        }

        Livro livro = livroRepo.findById(pedido.getLivro().getId())
                .orElseThrow();
        Usuario usuario = usuarioRepo.findById(pedido.getUsuario().getId())
                .orElseThrow();

        if (!livro.isDisponivel()) {
            throw new IllegalArgumentException("Livro indisponível: já está emprestado");
        }

        livro.setDisponivel(false);
        livroRepo.save(livro);

        Emprestimo emprestimo = new Emprestimo();
        emprestimo.setLivro(livro);
        emprestimo.setUsuario(usuario);
        emprestimo.setDataEmprestimo(LocalDate.now());
        emprestimo.setDataPrevistaDevolucao(LocalDate.now().plusDays(PRAZO_EM_DIAS));
        return emprestimoRepo.save(emprestimo);
    }

    @Transactional
    public Emprestimo devolver(Long id) {
        Emprestimo emprestimo = getById(id);
        if (emprestimo.getDataDevolucao() != null) {
            throw new IllegalArgumentException("Este empréstimo já foi devolvido");
        }

        emprestimo.setDataDevolucao(LocalDate.now());

        Livro livro = emprestimo.getLivro();
        livro.setDisponivel(true);
        livroRepo.save(livro);

        return emprestimoRepo.save(emprestimo);
    }

    @Transactional
    public void deleteEmprestimo(Long id) {
        Emprestimo emprestimo = getById(id);
        if (emprestimo.getDataDevolucao() == null) {
            Livro livro = emprestimo.getLivro();
            livro.setDisponivel(true);
            livroRepo.save(livro);
        }
        emprestimoRepo.deleteById(id);
    }
}
