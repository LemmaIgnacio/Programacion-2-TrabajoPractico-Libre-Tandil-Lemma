package io;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import model.celda.Celda;
import model.celda.estados.EstadoCelda;
import model.celda.estados.EstadoFactory;
import model.tablero.Tablero;

public class LectorArchivo {

    public Tablero cargarDesdeArchivo(String ruta) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            String primeraLinea = br.readLine();
            if (primeraLinea == null) {
                System.out.println("archivo vacio");
                return null;
            }
            String[] dimensiones = primeraLinea.split(" ");
            if (dimensiones.length != 2) {
                System.out.println("formato invalido se esperaba filas y columnas.");
                return null;
            }
            int filas, columnas;
            try {
                filas = Integer.parseInt(dimensiones[0]);
                columnas = Integer.parseInt(dimensiones[1]);
            } catch (NumberFormatException e) {
                System.out.println("deben ser numeros enteros.");
                return null;
            }
            Tablero tablero = new Tablero(filas, columnas);
            for (int i = 0; i < filas; i++) {
                String linea = br.readLine();
                if (linea == null || linea.length() != columnas) {
                    throw new IOException("Formato de archivo inválido en la fila " + (i + 1));
                }
                for (int j = 0; j < columnas; j++) {
                    char c = linea.charAt(j);
                    EstadoCelda estado = EstadoFactory.crearEstado(c);
                    tablero.setCelda(i, j, new Celda(estado));
                }
            }
            return tablero;
        }
    }
}