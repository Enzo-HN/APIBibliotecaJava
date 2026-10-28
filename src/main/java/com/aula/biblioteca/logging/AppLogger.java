package com.aula.biblioteca.logging;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class AppLogger implements ILogger {

    private static class Holder {
        private static final AppLogger INSTANCIA = new AppLogger();
    }

    private AppLogger() {
    }

    public static AppLogger getInstance() {
        return Holder.INSTANCIA;
    }

    @Override
    public void debug(String mensagem) {
        escrever("DEBUG", mensagem);
    }

    @Override
    public void info(String mensagem) {
        escrever("INFO", mensagem);
    }

    @Override
    public void erro(String mensagem) {
        escrever("ERRO", mensagem);
    }

    // synchronized: uma thread escreve por vez, as linhas não se misturam.
    private synchronized void escrever(String nivel, String mensagem) {
        String linha = LocalDateTime.now() + " [" + nivel + "] " + mensagem;
        System.out.println(linha);
        try (FileWriter arquivo = new FileWriter("app.log", true)) {
            arquivo.write(linha + System.lineSeparator());
        } catch (IOException e) {
            System.out.println("Não foi possível gravar no app.log: " + e.getMessage());
        }
    }
}