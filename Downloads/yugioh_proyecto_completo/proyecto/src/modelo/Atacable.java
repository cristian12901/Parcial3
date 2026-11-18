package modelo;

/** Contrato que debe cumplir cualquier carta que pueda participar en combate. */
public interface Atacable {
    int getAtq();
    int getDef();
    Posicion getPosicion();
}
