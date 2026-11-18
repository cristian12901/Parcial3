package modelo;

/**
 * Lógica pura de cálculo de daño (RF3 / RNF3). No modifica jugadores ni monstruos,
 * por eso se puede probar con JUnit sin ninguna dependencia.
 */
public final class CalculadoraDanio {

    private CalculadoraDanio() {
    }

    /** Ataque contra un monstruo defensor (Ataque vs Ataque o Ataque vs Defensa). */
    public static ResultadoCombate calcular(Monstruo atacante, Monstruo defensor) {
        validarAtacante(atacante);
        if (defensor == null) {
            throw new IllegalArgumentException("El defensor no puede ser nulo.");
        }
        String nA = atacante.getNombre();
        String nD = defensor.getNombre();

        if (defensor.getPosicion() == Posicion.ATAQUE) {
            int diferencia = atacante.getAtq() - defensor.getAtq();
            if (diferencia > 0) {
                return new ResultadoCombate(nA, nD, diferencia, Objetivo.DEFENSOR, false, true,
                        "Defensor destruido; el defensor pierde " + diferencia + " LP");
            } else if (diferencia < 0) {
                return new ResultadoCombate(nA, nD, -diferencia, Objetivo.ATACANTE, true, false,
                        "Atacante destruido; el atacante pierde " + (-diferencia) + " LP");
            }
            return new ResultadoCombate(nA, nD, 0, Objetivo.NINGUNO, true, true,
                    "Ambos monstruos destruidos; sin daño");
        }

        int diferencia = atacante.getAtq() - defensor.getDef();
        if (diferencia > 0) {
            return new ResultadoCombate(nA, nD, 0, Objetivo.NINGUNO, false, true,
                    "Defensor destruido; sin daño a los LP");
        } else if (diferencia < 0) {
            return new ResultadoCombate(nA, nD, -diferencia, Objetivo.ATACANTE, false, false,
                    "Ataque rechazado; el atacante pierde " + (-diferencia) + " LP");
        }
        return new ResultadoCombate(nA, nD, 0, Objetivo.NINGUNO, false, false,
                "Empate; no pasa nada");
    }

    /** Ataque directo (sobrecarga): el defensor no tiene monstruos. */
    public static ResultadoCombate calcular(Monstruo atacante) {
        validarAtacante(atacante);
        return new ResultadoCombate(atacante.getNombre(), "Ataque directo", atacante.getAtq(),
                Objetivo.DEFENSOR, false, false,
                "Ataque directo; el defensor pierde " + atacante.getAtq() + " LP");
    }

    private static void validarAtacante(Monstruo atacante) {
        if (atacante == null) {
            throw new IllegalArgumentException("El atacante no puede ser nulo.");
        }
        if (atacante.getPosicion() != Posicion.ATAQUE) {
            throw new IllegalStateException("Solo un monstruo en posición de ataque puede atacar.");
        }
    }
}
