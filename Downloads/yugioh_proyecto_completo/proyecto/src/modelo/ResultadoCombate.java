package modelo;

/** Resultado inmutable de un ataque; es lo que se guarda en el historial (RF4). */
public class ResultadoCombate {

    private final String atacante;
    private final String defensor;
    private final int danio;
    private final Objetivo objetivoDanio;
    private final boolean atacanteDestruido;
    private final boolean defensorDestruido;
    private final String descripcion;

    public ResultadoCombate(String atacante, String defensor, int danio, Objetivo objetivoDanio,
            boolean atacanteDestruido, boolean defensorDestruido, String descripcion) {
        this.atacante = atacante;
        this.defensor = defensor;
        this.danio = danio;
        this.objetivoDanio = objetivoDanio;
        this.atacanteDestruido = atacanteDestruido;
        this.defensorDestruido = defensorDestruido;
        this.descripcion = descripcion;
    }

    public String getAtacante() { return atacante; }
    public String getDefensor() { return defensor; }
    public int getDanio() { return danio; }
    public Objetivo getObjetivoDanio() { return objetivoDanio; }
    public boolean isAtacanteDestruido() { return atacanteDestruido; }
    public boolean isDefensorDestruido() { return defensorDestruido; }
    public String getDescripcion() { return descripcion; }

    @Override
    public String toString() {
        return "Atacante: " + atacante + " | Defensor: " + defensor
                + " | Daño: " + danio + " | Resultado: " + descripcion;
    }
}
