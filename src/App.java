import cli.GameCLI;

public class App {
    public static void main(String[] args) {
        //iniciar la interfaz del juego
        GameCLI juego = new GameCLI();
        juego.ejecutar();
    }
}