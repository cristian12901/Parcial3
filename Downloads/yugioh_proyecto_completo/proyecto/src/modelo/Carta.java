package modelo;

/** Clase base abstracta de todas las cartas. */
public abstract class Carta {

    private final String nombre;

    protected Carta(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la carta no puede estar vacío.");
        }
        this.nombre = nombre.trim();
    }

    public String getNombre() {
        return nombre;
    }

    /** Cada tipo de carta describe a sí misma (sobreescritura). */
    public abstract String getDescripcion();
}
