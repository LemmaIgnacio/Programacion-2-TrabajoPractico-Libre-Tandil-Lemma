package model.celda.estados;

public class EstadoMuerta implements EstadoCelda {

    @Override
    public EstadoCelda siguienteEstado(int vecinosVivos) {
        //revive si tiene 3 vecinos vivos
        if (vecinosVivos == 3) {
            return new EstadoViva();
        }

        return new EstadoMuerta();
    }

    @Override
    public boolean estaViva() {
        return false;
    }

    @Override
    public char getRepresentacion() {
        return '.';
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