package com.aula.biblioteca.logging;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

final class LogEscritor {

    private static final DateTimeFormatter DATA_HORA = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    private final Path arquivo;

    LogEscritor(Path arquivo) {
        this.arquivo = arquivo;
    }
    synchronized void escrever(NivelLog nivel, String mensagem, Throwable causa) {
        String linha = formatar(nivel, mensagem, causa);

        PrintStream console = (nivel == NivelLog.ERRO) ? System.err : System.out;
        console.println(linha);

        try {
            Files.writeString(arquivo, linha + System.lineSeparator(), StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.err.println("Falha ao gravar no arquivo de log " + arquivo + ": " + e.getMessage());
        }
    }

    private String formatar(NivelLog nivel, String mensagem, Throwable causa) {
        String texto = String.valueOf(mensagem).replaceAll("[\\r\\n]+", " ");
        String linha = String.format("%s [%-5s] [%s] %s",
                LocalDateTime.now().format(DATA_HORA), nivel.name(), Thread.currentThread().getName(), texto);
        if (causa != null) {
            linha += " | " + causa.getClass().getSimpleName() + ": "
                    + String.valueOf(causa.getMessage()).replaceAll("[\\r\\n]+", " ");
        }
        return linha;
    }
}