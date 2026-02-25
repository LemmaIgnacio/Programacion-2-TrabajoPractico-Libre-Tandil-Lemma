package cli;

import io.LectorArchivo;
import model.tablero.Tablero;
import utils.Utils;

public class GameCLI {

    //leer archivos de tablero
    //construir objeto tablero
    //cargar estado inicial del juego desde un archivo dado
    
    private Tablero tablero;

    public GameCLI() {}

    // interfaz CLI
    //mostrar menu
    //elegir inicializacion
    //pedir params de simulacion y ejecucion
    public void ejecutar() {
        System.out.println("insertar: 1 cargar archivo, 2 aleatorio");
        int opcion = Utils.leerInt();
        while (opcion != 1 && opcion != 2) {
            System.out.println("error elija 1 o 2)");
            opcion = Utils.leerInt();
        }

        if (opcion == 1) {
            cargarDesdeArchivo();
        } else if (opcion == 2) {
            inicializarAleatorio();
        } else {
            System.out.println("error");
            return;
        }

        System.out.print("insertar numero de simulaciones (0 para indefinidamente): ");
        int generaciones = Utils.leerInt();

        System.out.print("generacion en milisegundos: ");
        int intervalo = Utils.leerInt();

        boolean hayCambios = true;
        int generacion = 0;
        //mostrar generaciones hasta que no haya cambios (o alcance max generaciones indicadas por el usuario)
        while (hayCambios && (generaciones == 0 || generacion < generaciones)) {
            System.out.println("num: " + generacion);
            System.out.print(tablero);
            System.out.println();
            hayCambios = tablero.avanzarGeneracion();
            if (!hayCambios) {
                System.out.println("terminado");
            }
            generacion++;
            try {
                Thread.sleep(intervalo);
            } catch (InterruptedException e) {
                System.out.println("interrumpido");
            }
        }
    }

    //carga el tablero desde un archivo
    // ej: ejemplos/ejemplo1.txt
    // retorna el tablero construido o null si hay un error
    //manejo de errores, permite volver a ingresar la ruta del archivo en caso de error
    private void cargarDesdeArchivo() {
        LectorArchivo lector = new LectorArchivo();
        boolean cargado = false;
        while (!cargado) {
            System.out.print("ruta del archivo: ");
            String ruta = Utils.leerString();
            try {
                tablero = lector.cargarDesdeArchivo(ruta);
                if (tablero != null) {
                    cargado = true;
                } else {
                    System.out.println("error en formato del archivo.");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    //inicializar el tablero de forma aleatorio
    //el usuario inserta: filas, columnas, % de celda viva
    private void inicializarAleatorio() {
        System.out.print("filas: ");
        int filas = Utils.leerInt();
        System.out.print("columnas: ");
        int columnas = Utils.leerInt();
        System.out.println("probabilidad de celda viva (entre 0 y 1)");
        double prob = Utils.leerDouble();
        while (prob < 0 || prob > 1) {
            System.out.println("debe estar entre 0 y 1.");
            System.out.print("probabilidad de celda viva (entre 0 y 1): ");
            prob = Utils.leerDouble();
        }
        tablero = new Tablero(filas, columnas);
        tablero.inicializarAleatorio(prob);
    }
}