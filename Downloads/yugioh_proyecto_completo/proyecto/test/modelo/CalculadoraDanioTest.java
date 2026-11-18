package modelo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CalculadoraDanioTest {

    private Monstruo atk(String n, int atq, int def) {
        return new Monstruo(n, atq, def, Posicion.ATAQUE);
    }

    private Monstruo def(String n, int atq, int def) {
        return new Monstruo(n, atq, def, Posicion.DEFENSA);
    }

    @Test
    void ataqueVsAtaque_atacanteMayor_destruyeDefensorYDanaAlDefensor() {
        ResultadoCombate r = CalculadoraDanio.calcular(atk("Dragón", 2500, 2100), atk("Mago", 2000, 1700));
        assertEquals(500, r.getDanio());
        assertEquals(Objetivo.DEFENSOR, r.getObjetivoDanio());
        assertTrue(r.isDefensorDestruido());
        assertFalse(r.isAtacanteDestruido());
    }

    @Test
    void ataqueVsAtaque_atacanteMenor_destruyeAtacanteYDanaAlAtacante() {
        ResultadoCombate r = CalculadoraDanio.calcular(atk("Mago", 2000, 1700), atk("Dragón", 2500, 2100));
        assertEquals(500, r.getDanio());
        assertEquals(Objetivo.ATACANTE, r.getObjetivoDanio());
        assertTrue(r.isAtacanteDestruido());
        assertFalse(r.isDefensorDestruido());
    }

    @Test
    void ataqueVsAtaque_empate_destruyeAmbosSinDanio() {
        ResultadoCombate r = CalculadoraDanio.calcular(atk("A", 1800, 1000), atk("B", 1800, 1000));
        assertEquals(0, r.getDanio());
        assertEquals(Objetivo.NINGUNO, r.getObjetivoDanio());
        assertTrue(r.isAtacanteDestruido());
        assertTrue(r.isDefensorDestruido());
    }

    @Test
    void ataqueVsDefensa_atqMayorQueDef_destruyeDefensorSinDanio() {
        ResultadoCombate r = CalculadoraDanio.calcular(atk("A", 2000, 0), def("B", 500, 1500));
        assertEquals(0, r.getDanio());
        assertEquals(Objetivo.NINGUNO, r.getObjetivoDanio());
        assertTrue(r.isDefensorDestruido());
        assertFalse(r.isAtacanteDestruido());
    }

    @Test
    void ataqueVsDefensa_atqMenorQueDef_danaAlAtacanteYNadieSeDestruye() {
        ResultadoCombate r = CalculadoraDanio.calcular(atk("A", 1000, 0), def("B", 500, 1800));
        assertEquals(800, r.getDanio());
        assertEquals(Objetivo.ATACANTE, r.getObjetivoDanio());
        assertFalse(r.isAtacanteDestruido());
        assertFalse(r.isDefensorDestruido());
    }

    @Test
    void ataqueVsDefensa_empate_nadaPasa() {
        ResultadoCombate r = CalculadoraDanio.calcular(atk("A", 1500, 0), def("B", 500, 1500));
        assertEquals(0, r.getDanio());
        assertEquals(Objetivo.NINGUNO, r.getObjetivoDanio());
        assertFalse(r.isAtacanteDestruido());
        assertFalse(r.isDefensorDestruido());
    }

    @Test
    void ataqueDirecto_danoIgualAlAtq() {
        ResultadoCombate r = CalculadoraDanio.calcular(atk("A", 1900, 0));
        assertEquals(1900, r.getDanio());
        assertEquals(Objetivo.DEFENSOR, r.getObjetivoDanio());
    }

    @Test
    void atacanteEnDefensa_noPuedeAtacar() {
        assertThrows(IllegalStateException.class,
                () -> CalculadoraDanio.calcular(def("A", 1000, 1000), atk("B", 500, 500)));
        assertThrows(IllegalStateException.class,
                () -> CalculadoraDanio.calcular(def("A", 1000, 1000)));
    }

    @Test
    void parametrosNulos_lanzanExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> CalculadoraDanio.calcular(null, atk("B", 1, 1)));
        assertThrows(IllegalArgumentException.class, () -> CalculadoraDanio.calcular(atk("A", 1, 1), null));
    }
}
