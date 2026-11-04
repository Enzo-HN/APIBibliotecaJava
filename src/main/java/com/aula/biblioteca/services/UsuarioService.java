package com.aula.biblioteca.services;

import com.aula.biblioteca.cep.ConsultaCep;
import com.aula.biblioteca.cep.Endereco;
import com.aula.biblioteca.entities.Usuario;
import com.aula.biblioteca.repositories.IJpaUsuarioRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private IJpaUsuarioRepo usuarioRepo;
    @Autowired
    private ConsultaCep consultaCep;

    public List<Usuario> getCadastrados() {
        return usuarioRepo.findAll();
    }

    public Usuario getById(Long id) {
        return usuarioRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Usuário não encontrado"
                ));
    }

    public Usuario addUsuario(Usuario usuario) {

        usuario.setId(null);

        if (usuario.getEndereco() == null ||
                usuario.getEndereco().getCep() == null) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Informe o CEP do usuario"
            );
        }

        String cep = usuario.getEndereco().getCep();
        Endereco endereco = consultaCep.buscar(cep);
        usuario.setEndereco(endereco);
        return usuarioRepo.save(usuario);
    }

    public Usuario updateUsuario(Long id, Usuario usuario) {

        Usuario usuarioDoBanco = getById(id);
        usuarioDoBanco.setNome(usuario.getNome());
        usuarioDoBanco.setCpf(usuario.getCpf());
        usuarioDoBanco.setEmail(usuario.getEmail());

        if (usuario.getEndereco() != null) {
            String cep = usuario.getEndereco().getCep();
            Endereco endereco = consultaCep.buscar(cep);
            usuarioDoBanco.setEndereco(endereco);
        }
        return usuarioRepo.save(usuarioDoBanco);
    }

    public void deleteUsuario(Long id) {
        getById(id);
        usuarioRepo.deleteById(id);
    }
}