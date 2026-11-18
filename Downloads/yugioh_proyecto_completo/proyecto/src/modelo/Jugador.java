package modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Jugador con Puntos de Vida y monstruos en el campo (RF1). */
public class Jugador {

    public static final int LP_INICIALES = 8000;

    private final String nombre;
    private int lp;
    private final List<Monstruo> campo = new ArrayList<>();

    public Jugador(String nombre) {
        this(nombre, LP_INICIALES);
    }

    public Jugador(String nombre, int lpIniciales) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del jugador no puede estar vacío.");
        }
        if (lpIniciales < 0) {
            throw new IllegalArgumentException("Los LP iniciales no pueden ser negativos.");
        }
        this.nombre = nombre.trim();
        this.lp = lpIniciales;
    }

    public String getNombre() {
        return nombre;
    }

    public int getLp() {
        return lp;
    }

    public List<Monstruo> getCampo() {
        return Collections.unmodifiableList(campo);
    }

    public void invocar(Monstruo monstruo) {
        if (monstruo == null) {
            throw new IllegalArgumentException("El monstruo no puede ser nulo.");
        }
        campo.add(monstruo);
    }

    public boolean retirar(Monstruo monstruo) {
        return campo.remove(monstruo);
    }

    public boolean tieneMonstruos() {
        return !campo.isEmpty();
    }

    /** Resta LP sin bajar de cero. */
    public void recibirDanio(int danio) {
        if (danio < 0) {
            throw new IllegalArgumentException("El daño no puede ser negativo.");
        }
        lp = Math.max(0, lp - danio);
    }

    public boolean estaDerrotado() {
        return lp == 0;
    }

    @Override
    public String toString() {
        return nombre + " (LP: " + lp + ")";
    }
}
