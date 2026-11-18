package modelo;

/** Subclase de Monstruo que además tiene un texto de efecto. */
public class MonstruoEfecto extends Monstruo {

    private final String efecto;

    public MonstruoEfecto(String nombre, int atq, int def, Posicion posicion, String efecto) {
        super(nombre, atq, def, posicion);
        this.efecto = (efecto == null) ? "" : efecto;
    }

    public String getEfecto() {
        return efecto;
    }

    @Override
    public String getDescripcion() {
        return "Monstruo de efecto: " + efecto;
    }
}
