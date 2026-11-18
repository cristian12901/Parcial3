package modelo;

/** Aplica el resultado de CalculadoraDanio sobre los jugadores y sus campos. */
public class Combate {

    public ResultadoCombate atacar(Jugador jugadorAtacante, Monstruo atacante,
            Jugador jugadorDefensor, Monstruo defensor) {
        verificarPropietario(jugadorAtacante, atacante);
        verificarPropietario(jugadorDefensor, defensor);

        ResultadoCombate r = CalculadoraDanio.calcular(atacante, defensor);
        aplicar(r, jugadorAtacante, atacante, jugadorDefensor, defensor);
        return r;
    }

    public ResultadoCombate atacarDirecto(Jugador jugadorAtacante, Monstruo atacante,
            Jugador jugadorDefensor) {
        verificarPropietario(jugadorAtacante, atacante);
        if (jugadorDefensor.tieneMonstruos()) {
            throw new IllegalStateException(
                    "No se puede atacar directo: el oponente tiene monstruos en el campo.");
        }
        ResultadoCombate r = CalculadoraDanio.calcular(atacante);
        aplicar(r, jugadorAtacante, atacante, jugadorDefensor, null);
        return r;
    }

    private void aplicar(ResultadoCombate r, Jugador jA, Monstruo mA, Jugador jD, Monstruo mD) {
        switch (r.getObjetivoDanio()) {
            case ATACANTE:
                jA.recibirDanio(r.getDanio());
                break;
            case DEFENSOR:
                jD.recibirDanio(r.getDanio());
                break;
            default:
                break;
        }
        if (r.isAtacanteDestruido()) {
            jA.retirar(mA);
        }
        if (r.isDefensorDestruido() && mD != null) {
            jD.retirar(mD);
        }
    }

    private void verificarPropietario(Jugador jugador, Monstruo monstruo) {
        if (jugador == null || monstruo == null || !jugador.getCampo().contains(monstruo)) {
            throw new IllegalArgumentException("El monstruo no pertenece al campo de ese jugador.");
        }
    }
}
