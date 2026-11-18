package modelo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class JugadorTest {

    @Test
    void lpPorDefecto_es8000() {
        assertEquals(8000, new Jugador("Yugi").getLp());
    }

    @Test
    void lpInicialPersonalizado() {
        assertEquals(4000, new Jugador("Kaiba", 4000).getLp());
    }

    @Test
    void recibirDanio_restaLp() {
        Jugador j = new Jugador("Yugi");
        j.recibirDanio(1500);
        assertEquals(6500, j.getLp());
        assertFalse(j.estaDerrotado());
    }

    @Test
    void recibirDanio_noBajaDeCero() {
        Jugador j = new Jugador("Yugi", 1000);
        j.recibirDanio(5000);
        assertEquals(0, j.getLp());
        assertTrue(j.estaDerrotado());
    }

    @Test
    void danioNegativo_lanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> new Jugador("Yugi").recibirDanio(-1));
    }

    @Test
    void lpInicialNegativo_lanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> new Jugador("Yugi", -10));
    }

    @Test
    void nombreVacio_lanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> new Jugador("  "));
    }

    @Test
    void invocarYRetirarMonstruos() {
        Jugador j = new Jugador("Yugi");
        Monstruo m = new Monstruo("Mago Oscuro", 2500, 2100, Posicion.ATAQUE);
        assertFalse(j.tieneMonstruos());
        j.invocar(m);
        assertTrue(j.tieneMonstruos());
        assertTrue(j.retirar(m));
        assertFalse(j.tieneMonstruos());
    }
}
