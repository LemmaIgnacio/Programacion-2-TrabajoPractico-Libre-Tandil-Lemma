package model.celda.estados;

public class EstadoFactory {

    public static EstadoCelda crearEstado(char c) {
        //crea un estado de la celda segun un caracter (instancias de EstadoCelda)
        //facilita agregar nuevos estados
        //manejo de errores
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