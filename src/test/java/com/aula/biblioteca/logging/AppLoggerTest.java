package com.aula.biblioteca.logging;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AppLoggerTest {

    @Test
    void construtorDeveSerPrivado() throws Exception {
        Constructor<AppLogger> construtor = AppLogger.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(construtor.getModifiers()));
    }

    @Test
    void getInstanceDeveRetornarSempreAMesmaInstancia() {
        assertSame(AppLogger.getInstance(), AppLogger.getInstance());
    }

    @Test
    void getInstanceDeveSerThreadSafe() {
        Set<AppLogger> instancias = ConcurrentHashMap.newKeySet();
        IntStream.range(0, 50).parallel().forEach(i -> instancias.add(AppLogger.getInstance()));
        assertEquals(1, instancias.size());
    }
}