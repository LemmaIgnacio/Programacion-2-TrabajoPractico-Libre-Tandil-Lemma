package model.celda.estados;

public class EstadoFactory {

    public static EstadoCelda crearEstado(char c) {
        switch (Character.toUpperCase(c)) {
            case 'O':
            case 'X':
                return new EstadoViva();
            case '.':
                return new EstadoMuerta();
            case 'E':
                return new EstadoEnferma();
            case 'L':
                return new EstadoLatente();
            default:
                throw new IllegalArgumentException("estado invalido " + c);
        }
    }
}