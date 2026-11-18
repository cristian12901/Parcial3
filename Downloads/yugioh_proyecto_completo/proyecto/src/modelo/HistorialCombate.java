package modelo;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/** Persistencia del historial de ataques en un archivo de texto (RF4). */
public class HistorialCombate {

    public static final String ARCHIVO_POR_DEFECTO = "historial_combate.txt";

    private static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final Path archivo;

    public HistorialCombate() {
        this(Paths.get(ARCHIVO_POR_DEFECTO));
    }

    public HistorialCombate(Path archivo) {
        this.archivo = archivo;
    }

    public void registrar(ResultadoCombate resultado) throws IOException {
        try (BufferedWriter w = Files.newBufferedWriter(archivo, StandardCharsets.UTF_8,
                StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
            w.write("[" + LocalDateTime.now().format(FORMATO) + "] " + resultado);
            w.newLine();
        }
    }
}
