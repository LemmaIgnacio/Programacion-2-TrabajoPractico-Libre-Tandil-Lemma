package model.celda.estados;

public class EstadoEnferma implements EstadoCelda {

    @Override
    public EstadoCelda siguienteEstado(int vecinosVivos) {
        return new EstadoMuerta();
    }

    @Override
    public boolean estaViva() {
        return true;
    }

    @Override
    public char getRepresentacion() {
        return 'E';
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
