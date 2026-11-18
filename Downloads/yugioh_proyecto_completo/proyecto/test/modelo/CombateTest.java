package modelo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CombateTest {

    private Combate combate;
    private Jugador yugi;
    private Jugador kaiba;
    private Monstruo mago;
    private Monstruo dragon;

    @BeforeEach
    void preparar() {
        combate = new Combate();
        yugi = new Jugador("Yugi");
        kaiba = new Jugador("Kaiba");
        mago = new Monstruo("Mago Oscuro", 2500, 2100, Posicion.ATAQUE);
        dragon = new Monstruo("Dragón Blanco", 3000, 2500, Posicion.ATAQUE);
        yugi.invocar(mago);
        kaiba.invocar(dragon);
    }

    @Test
    void atacanteMasFuerte_bajaLpDelDefensorYLoRetira() {
        combate.atacar(kaiba, dragon, yugi, mago);
        assertEquals(7500, yugi.getLp());
        assertEquals(8000, kaiba.getLp());
        assertFalse(yugi.tieneMonstruos());
        assertTrue(kaiba.tieneMonstruos());
    }

    @Test
    void atacanteMasDebil_bajaSuPropioLpYSeRetira() {
        combate.atacar(yugi, mago, kaiba, dragon);
        assertEquals(7500, yugi.getLp());
        assertEquals(8000, kaiba.getLp());
        assertFalse(yugi.tieneMonstruos());
        assertTrue(kaiba.tieneMonstruos());
    }

    @Test
    void ataqueDirecto_sinDefensores_bajaLp() {
        kaiba.retirar(dragon);
        combate.atacarDirecto(yugi, mago, kaiba);
        assertEquals(5500, kaiba.getLp());
    }

    @Test
    void ataqueDirecto_conDefensores_lanzaExcepcion() {
        assertThrows(IllegalStateException.class, () -> combate.atacarDirecto(yugi, mago, kaiba));
    }

    @Test
    void monstruoAjeno_lanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> combate.atacar(yugi, dragon, kaiba, mago));
    }
}
