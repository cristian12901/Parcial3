package modelo;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class HistorialCombateTest {

    @Test
    void registrar_agregaUnaLineaPorAtaque(@TempDir Path dir) throws IOException {
        Path archivo = dir.resolve("historial_combate.txt");
        HistorialCombate historial = new HistorialCombate(archivo);
        Monstruo a = new Monstruo("Mago", 2000, 1000, Posicion.ATAQUE);
        Monstruo b = new Monstruo("Dragón", 1500, 1000, Posicion.ATAQUE);

        historial.registrar(CalculadoraDanio.calcular(a, b));
        historial.registrar(CalculadoraDanio.calcular(a));

        List<String> lineas = Files.readAllLines(archivo);
        assertEquals(2, lineas.size());
        assertTrue(lineas.get(0).contains("Atacante: Mago"));
        assertTrue(lineas.get(0).contains("Daño: 500"));
        assertTrue(lineas.get(1).contains("Ataque directo"));
    }
}
