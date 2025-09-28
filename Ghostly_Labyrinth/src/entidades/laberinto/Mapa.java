package entidades.laberinto;

public class Mapa {
    int alto;
    int ancho;
    // todavía no se usa, hay que pensarlo bien
    public int getMapaAlto(int nivel){
        return switch (nivel) {
            case 0 -> 10;
            case 1 -> 15;
            default -> 10;
        };
    }
    public int getMapaAncho(int nivel){
        return ancho;
    }

    int [][] matriz;
}
