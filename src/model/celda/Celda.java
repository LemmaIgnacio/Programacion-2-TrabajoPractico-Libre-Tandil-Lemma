package model.celda;

import model.celda.estados.EstadoCelda;

public class Celda {

    private EstadoCelda estado;

    public Celda(EstadoCelda estadoInicial) {
        this.estado = estadoInicial;
    }

    public boolean estaViva() {
        return estado.estaViva();
    }

    public char getRepresentacion() {
        return estado.getRepresentacion();
    }

    public EstadoCelda calcularSiguienteEstado(int vecinosVivos) {
        return estado.siguienteEstado(vecinosVivos);
    }

    public void actualizarEstado(EstadoCelda nuevoEstado) {
        this.estado = nuevoEstado;
    }

    public EstadoCelda getEstado() {
        return estado;
    }
}
