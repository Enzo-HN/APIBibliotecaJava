package com.aula.biblioteca.services;

import com.aula.biblioteca.entities.Emprestimo;
import com.aula.biblioteca.entities.Livro;
import com.aula.biblioteca.entities.Usuario;
import com.aula.biblioteca.logging.ILogger;
import com.aula.biblioteca.repositories.IJpaEmprestimoRepo;
import com.aula.biblioteca.repositories.IJpaLivroRepo;
import com.aula.biblioteca.repositories.IJpaUsuarioRepo;
import com.aula.biblioteca.state.EstadoAtivo;
import com.aula.biblioteca.state.EstadoAtrasado;
import com.aula.biblioteca.state.EstadoDevolvido;
import com.aula.biblioteca.state.EstadoEmprestimo;
import com.aula.biblioteca.strategy.CalculadoraMulta;
import com.aula.biblioteca.strategy.MultaLimitada;
import com.aula.biblioteca.strategy.MultaPadrao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

@Service
public class EmprestimoService {

    private static final int PRAZO_EM_DIAS = 14;

    @Autowired
    private IJpaEmprestimoRepo emprestimoRepo;

    @Autowired
    private IJpaLivroRepo livroRepo;

    @Autowired
    private IJpaUsuarioRepo usuarioRepo;

    @Autowired
    private ILogger log;

    public List<Emprestimo> getCadastrados() {
        return emprestimoRepo.findAll();
    }

    public List<Emprestimo> getEmAberto() {
        return emprestimoRepo.findByDataDevolucaoIsNull();
    }

    public Emprestimo getById(Long id) {
        return emprestimoRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Empréstimo não encontrado"));
    }

    @Transactional
    public Emprestimo addEmprestimo(Emprestimo pedido) {
        if (pedido.getLivro() == null || pedido.getLivro().getId() == null
                || pedido.getUsuario() == null || pedido.getUsuario().getId() == null) {
            log.erro("Empréstimo sem livro.id ou usuario.id");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Informe livro.id e usuario.id");
        }

        Livro livro = livroRepo.findById(pedido.getLivro().getId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Livro não encontrado"));

        Usuario usuario = usuarioRepo.findById(pedido.getUsuario().getId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        if (!livro.isDisponivel()) {
            log.erro("Livro indisponível para empréstimo. ID: " + livro.getId());
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Livro indisponível: já está emprestado");
        }

        String tipoMulta = pedido.getTipoMulta();
        if (tipoMulta == null || tipoMulta.isBlank()) {
            tipoMulta = "PADRAO";
        }
        tipoMulta = tipoMulta.trim().toUpperCase(Locale.ROOT);
        escolherCalculadora(tipoMulta);

        livro.setDisponivel(false);
        livroRepo.save(livro);

        Emprestimo emprestimo = new Emprestimo();
        emprestimo.setLivro(livro);
        emprestimo.setUsuario(usuario);
        emprestimo.setDataEmprestimo(LocalDate.now());
        emprestimo.setDataPrevistaDevolucao(LocalDate.now().plusDays(PRAZO_EM_DIAS));
        emprestimo.setTipoMulta(tipoMulta);
        emprestimo.setMulta(BigDecimal.ZERO);

        Emprestimo salvo = emprestimoRepo.save(emprestimo);
        log.info("Empréstimo realizado. ID: " + salvo.getId()
                + ", livro: " + livro.getId() + ", multa: " + tipoMulta);
        return salvo;
    }

    @Transactional
    public Emprestimo devolver(Long id) {
        Emprestimo emprestimo = getById(id);

        EstadoEmprestimo estado = obterEstado(emprestimo);
        CalculadoraMulta calculadora = escolherCalculadora(emprestimo.getTipoMulta());

        try {
            estado.devolver(emprestimo, calculadora, LocalDate.now());
        } catch (IllegalStateException e) {
            log.erro("Devolução recusada. Empréstimo ID: " + id);
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }

        Livro livro = emprestimo.getLivro();
        livro.setDisponivel(true);
        livroRepo.save(livro);

        Emprestimo salvo = emprestimoRepo.save(emprestimo);
        log.info("Empréstimo devolvido. ID: " + id
                + ", multa: R$ " + salvo.getMulta());
        return salvo;
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
        log.info("Empréstimo excluído. ID: " + id);
    }

    private EstadoEmprestimo obterEstado(Emprestimo emprestimo) {
        String situacao = emprestimo.getSituacao();
        if (situacao.equals("DEVOLVIDO")) {
            return new EstadoDevolvido();
        }
        if (situacao.equals("ATRASADO")) {
            return new EstadoAtrasado();
        }
        return new EstadoAtivo();
    }

    private CalculadoraMulta escolherCalculadora(String tipoMulta) {
        if (tipoMulta == null || tipoMulta.isBlank()
                || tipoMulta.equalsIgnoreCase("PADRAO")) {
            return new MultaPadrao();
        }
        if (tipoMulta.equalsIgnoreCase("LIMITADA")) {
            return new MultaLimitada();
        }

        log.erro("Tipo de multa inválido: " + tipoMulta);
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                "Tipo de multa inválido. Use PADRAO ou LIMITADA");
    }
}
