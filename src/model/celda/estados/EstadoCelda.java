package model.celda.estados;

public interface EstadoCelda {

    EstadoCelda siguienteEstado(int vecinosVivos);

    boolean estaViva();

    char getRepresentacion();
}