package model.celda.estados;

import java.util.Random;

public class EstadoViva implements EstadoCelda {

    private static final Random random = new Random();

    @Override
    public EstadoCelda siguienteEstado(int vecinosVivos) {

        if (random.nextDouble() < 0.25) {
            return new EstadoEnferma();
        }

        if (vecinosVivos < 2) {
            return new EstadoMuerta();
        }

        if (vecinosVivos == 2 || vecinosVivos == 3) {
            return new EstadoViva();
        }

        return new EstadoMuerta();
    }

    @Override
    public boolean estaViva() {
        return true;
    }

    @Override
    public char getRepresentacion() {
        return 'O';
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && this.getClass() == obj.getClass();
    }

    @Override
    public int hashCode() {
        return this.getClass().hashCode();
    }
}

