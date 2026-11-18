package controlador;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import modelo.Combate;
import modelo.HistorialCombate;
import modelo.Jugador;
import modelo.Monstruo;
import modelo.MonstruoEfecto;
import modelo.Posicion;
import modelo.ResultadoCombate;
import vista.VistaBatalla;

/** Conecta la vista (JFrame Form) con el modelo. Aquí vive todo el enlace de eventos. */
public class ControladorBatalla {

    private final VistaBatalla vista;
    private final Combate combate = new Combate();
    private final HistorialCombate historial = new HistorialCombate();
    private Jugador[] jugadores; // null hasta que se crean (RF1)

    public ControladorBatalla(VistaBatalla vista) {
        this.vista = vista;
        this.vista.setBatallaHabilitada(false);
        enlazarEventos();
    }

    private void enlazarEventos() {
        vista.addCrearJugadoresListener(e -> crearJugadores());
        vista.addInvocarListener(e -> invocarMonstruo());
        vista.addAtacarListener(e -> atacar());
        vista.addAtaqueDirectoListener(e -> atacarDirecto());
    }

    // ---------- RF1: jugadores ----------
    private void crearJugadores() {
        try {
            int lp = Integer.parseInt(vista.getLpInicial().trim());
            Jugador j1 = new Jugador(vista.getNombreJ1(), lp);
            Jugador j2 = new Jugador(vista.getNombreJ2(), lp);
            jugadores = new Jugador[] {j1, j2};
            vista.setBatallaHabilitada(true);
            refrescarVista();
        } catch (NumberFormatException ex) {
            vista.mostrarError("Los LP deben ser un número entero.");
        } catch (IllegalArgumentException ex) {
            vista.mostrarError(ex.getMessage());
        }
    }

    // ---------- RF2: monstruos ----------
    private void invocarMonstruo() {
        if (!hayJugadores()) {
            return;
        }
        try {
            String nombre = vista.getNombreMonstruo();
            int atq = Integer.parseInt(vista.getAtq().trim());
            int def = Integer.parseInt(vista.getDef().trim());
            Posicion pos = (vista.getIndicePosicion() == 0) ? Posicion.ATAQUE : Posicion.DEFENSA;
            String efecto = vista.getEfecto().trim();

            Monstruo m = efecto.isEmpty()
                    ? new Monstruo(nombre, atq, def, pos)
                    : new MonstruoEfecto(nombre, atq, def, pos, efecto);

            jugadores[vista.getIndiceJugadorInvocador()].invocar(m);
            vista.limpiarFormularioMonstruo();
            refrescarVista();
        } catch (NumberFormatException ex) {
            vista.mostrarError("ATQ y DEF deben ser números enteros.");
        } catch (IllegalArgumentException ex) {
            vista.mostrarError(ex.getMessage());
        }
    }

    // ---------- RF3 y RF4: combate e historial ----------
    private void atacar() {
        if (!hayJugadores()) {
            return;
        }
        int ia = vista.getIndiceAtacante();
        Jugador atacante = jugadores[ia];
        Jugador defensor = jugadores[1 - ia];
        int iMa = vista.getIndiceSeleccionado(ia);
        int iMd = vista.getIndiceSeleccionado(1 - ia);
        if (iMa < 0 || iMd < 0) {
            vista.mostrarError("Selecciona un monstruo atacante y un monstruo objetivo.");
            return;
        }
        try {
            ResultadoCombate r = combate.atacar(atacante, atacante.getCampo().get(iMa),
                    defensor, defensor.getCampo().get(iMd));
            terminarAtaque(r);
        } catch (IllegalStateException | IllegalArgumentException ex) {
            vista.mostrarError(ex.getMessage());
        }
    }

    private void atacarDirecto() {
        if (!hayJugadores()) {
            return;
        }
        int ia = vista.getIndiceAtacante();
        Jugador atacante = jugadores[ia];
        Jugador defensor = jugadores[1 - ia];
        int iMa = vista.getIndiceSeleccionado(ia);
        if (iMa < 0) {
            vista.mostrarError("Selecciona el monstruo que atacará.");
            return;
        }
        try {
            ResultadoCombate r = combate.atacarDirecto(atacante, atacante.getCampo().get(iMa), defensor);
            terminarAtaque(r);
        } catch (IllegalStateException | IllegalArgumentException ex) {
            vista.mostrarError(ex.getMessage());
        }
    }

    private void terminarAtaque(ResultadoCombate r) {
        try {
            historial.registrar(r);
        } catch (IOException ex) {
            vista.mostrarError("No se pudo escribir el historial: " + ex.getMessage());
        }
        vista.agregarHistorial(r.toString());
        refrescarVista();
        verificarGanador();
    }

    private void verificarGanador() {
        for (int i = 0; i < jugadores.length; i++) {
            if (jugadores[i].estaDerrotado()) {
                vista.mostrarMensaje("¡" + jugadores[1 - i].getNombre() + " gana el duelo!");
                vista.setBatallaHabilitada(false);
                return;
            }
        }
    }

    // ---------- utilidades ----------
    private boolean hayJugadores() {
        if (jugadores == null) {
            vista.mostrarError("Primero crea los jugadores.");
            return false;
        }
        return true;
    }

    private void refrescarVista() {
        for (int i = 0; i < jugadores.length; i++) {
            vista.setNombre(i, jugadores[i].getNombre());
            vista.setLp(i, jugadores[i].getLp());
            List<String> textos = new ArrayList<>();
            for (Monstruo m : jugadores[i].getCampo()) {
                textos.add(m.toString());
            }
            vista.setCampo(i, textos);
        }
    }
}
