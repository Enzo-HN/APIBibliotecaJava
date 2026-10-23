package com.aula.biblioteca.services;

import com.aula.biblioteca.entities.Usuario;
import com.aula.biblioteca.repositories.IJpaUsuarioRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private IJpaUsuarioRepo usuarioRepo;

    public List<Usuario> getCadastrados() {
        return usuarioRepo.findAll();
    }

    public Usuario getById(Long id) {
        return usuarioRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
    }

    public Usuario addUsuario(Usuario usuario) {
        usuario.setId(null);
        return usuarioRepo.save(usuario);
    }

    public Usuario updateUsuario(Long id, Usuario usuario) {
        Usuario usuarioDoBanco = getById(id);
        usuarioDoBanco.setNome(usuario.getNome());
        usuarioDoBanco.setCpf(usuario.getCpf());
        usuarioDoBanco.setEmail(usuario.getEmail());
        return usuarioRepo.save(usuarioDoBanco);
    }

    public void deleteUsuario(Long id) {
        getById(id);
        usuarioRepo.deleteById(id);
    }
}
