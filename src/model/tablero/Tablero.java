package model.tablero;

import java.util.Random;
import model.celda.Celda;
import model.celda.estados.EstadoCelda;
import model.celda.estados.EstadoMuerta;
import model.celda.estados.EstadoViva;

public class Tablero {

 // representa el tablero
 // Mantiene la matriz de celdas, gestiona la evolución generacional,
  //calcula vecinos vivos y detecta sino hubo cambios

    private Celda[][] celdas;
    private int filas;
    private int columnas;

    // crea un tablero con la cantidad de filas y columnas por parametro
    public Tablero(int filas, int columnas) {
        this.filas = filas;
        this.columnas = columnas;
        this.celdas = new Celda[filas][columnas];
    }

    public void setCelda(int fila, int columna, Celda celda) {
        celdas[fila][columna] = celda;
    }

    public Celda getCelda(int fila, int columna) {
        return celdas[fila][columna];
    }

    public int getFilas() {
        return filas;
    }

    public int getColumnas() {
        return columnas;
    }

    //  calcilar el numero de vecinos vivos en una celda
    private int contarVecinosVivos(int fila, int columna) {
        int vivos = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (!(i == 0 && j == 0)) {
                    int nuevaFila = fila + i;
                    int nuevaCol = columna + j;
                    if (esPosicionValida(nuevaFila, nuevaCol)) {
                        if (celdas[nuevaFila][nuevaCol].estaViva()) {
                            vivos++;
                        }
                    }
                }
            }
        }
        return vivos;
    }

    //verifica si la posicion se encuentra en el tablero
    private boolean esPosicionValida(int fila, int columna) {
        return fila >= 0 && fila < filas &&
                columna >= 0 && columna < columnas;
    }

    // avanzar con la generacion del tablero
    //calcular nuevo estado de las celdas 
    //actualizar el tablero
    // hubo cambios -> true
    // no hubo cambios -> false
    public boolean avanzarGeneracion() {
        EstadoCelda[][] nuevosEstados = new EstadoCelda[filas][columnas];
        boolean huboCambios = false;
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                int vecinos = contarVecinosVivos(i, j);
                EstadoCelda nuevoEstado = celdas[i][j].calcularSiguienteEstado(vecinos);
                nuevosEstados[i][j] = nuevoEstado;
                if (!celdas[i][j].getEstado().equals(nuevosEstados[i][j])) {
                    huboCambios = true;
                }
            }
        }
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                celdas[i][j].actualizarEstado(nuevosEstados[i][j]);
            }
        }
        return huboCambios;
    }

    //crear un tablero aleatorio
    //toma encuenta % de celdas vivas
    public void inicializarAleatorio(double probabilidadViva) {
        Random r = new Random();
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                if (r.nextDouble() < probabilidadViva) {
                    celdas[i][j] = new Celda(new EstadoViva());
                } else {
                    celdas[i][j] = new Celda(new EstadoMuerta());
                }
            }
        }
    }   

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                sb.append(celdas[i][j].getRepresentacion());
            }
            sb.append("\n");
        }

        return sb.toString();
    }
}