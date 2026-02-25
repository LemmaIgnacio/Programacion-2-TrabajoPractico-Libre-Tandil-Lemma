package model.celda.estados;

public interface EstadoCelda {

    //interfaz para los estados de las celdas
    //definir metodos para calcular siguientes estados
    //devolver si una celda esta viva o muerta
    //devolver char representativo de la celda

    //calcular el estado que va a tener en la siguiente
    //generacion segun cantidad de vecinos
    EstadoCelda siguienteEstado(int vecinosVivos);

    //true -> celda viva
    //false -> celda muerta
    boolean estaViva();

    //'O' 'X' 'L' 'E' '.'
    char getRepresentacion();
}