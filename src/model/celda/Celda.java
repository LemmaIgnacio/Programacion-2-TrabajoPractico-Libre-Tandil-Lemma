package model.celda;

import model.celda.estados.EstadoCelda;

public class Celda {
    //celdas individuales del talero
    //cada uno tiene un estado
    private EstadoCelda estado;

    //crear celda dado un estado
    public Celda(EstadoCelda estadoInicial) {
        this.estado = estadoInicial;
    }

    //devolver si la celda esta viva segun su estado
    public boolean estaViva() {
        return estado.estaViva();
    }

    //devolvere el char que representa el estado
    public char getRepresentacion() {
        return estado.getRepresentacion();
    }

    //devolver el siguiente estado de la celda segun los vecinos vivos
    public EstadoCelda calcularSiguienteEstado(int vecinosVivos) {
        return estado.siguienteEstado(vecinosVivos);
    }

    //devolver el estado actualizado de la celda
    public void actualizarEstado(EstadoCelda nuevoEstado) {
        this.estado = nuevoEstado;
    }

    //devolver estado actual
    public EstadoCelda getEstado() {
        return estado;
    }
}
