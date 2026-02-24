package model.celda.estados;

public class EstadoLatente implements EstadoCelda {

    @Override
    public EstadoCelda siguienteEstado(int vecinosVivos) {

        if (vecinosVivos == 1) {
            return new EstadoViva();
        }

        return new EstadoLatente();
    }

    @Override
    public boolean estaViva() {
        return false;
    }

    @Override
    public char getRepresentacion() {
        return 'L';
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