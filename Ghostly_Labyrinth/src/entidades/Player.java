package entidades;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.swing.ImageIcon;

import entidades.laberinto.Labyrinth;

import javax.imageio.ImageIO;

public class Player extends Entity{

    //este objeto de la clase laberinto permite obtener la matriz laberinto 
    private Labyrinth lab;

    //variable que indica si el jugador agarro la llave
    private boolean tieneLlave = false;


    public Player (String nombre, Labyrinth lab) {
        super();
        this.lab = lab;
        setNombre(nombre);
        setValoresDefault();
        getImagen();
    }

    public Player (Labyrinth lab) {
        super();
        this.lab = lab;
        setValoresDefault();
        getImagen();
    }

    public void setValoresDefault () {
        x = 50;
        y = 50;
        movimiento = 5;
        direccion = "down";
        ultimaDireccion = "down";
    }

    public boolean getTieneLlave(){
        return tieneLlave;
    }

    public void setTieneLlave(boolean tieneLlave){
        this.tieneLlave = tieneLlave;
    }

    public void setDireccion(String direccion){
        this.direccion = direccion;
    }

    public String getDireccion(){
        return direccion;
    }


    public void getImagen(){
        try {
            System.out.println(getClass().getResourceAsStream("/assets/img/player/espalda_0.png"));
            up0 = ImageIO.read(getClass().getResourceAsStream("/assets/img/player/espalda_0.png"));
            up1 = ImageIO.read(getClass().getResourceAsStream("/assets/img/player/espalda_1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/assets/img/player/espalda_2.png"));
            down0 = ImageIO.read(getClass().getResourceAsStream("/assets/img/player/frente_0.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/assets/img/player/frente_1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/assets/img/player/frente_2.png"));
            left0 = ImageIO.read(getClass().getResourceAsStream("/assets/img/player/izq_0.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/assets/img/player/izq_1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/assets/img/player/izq_2.png"));
            right0 = ImageIO.read(getClass().getResourceAsStream("/assets/img/player/der_0.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/assets/img/player/der_1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/assets/img/player/der_2.png"));
            
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D grafico){
        BufferedImage imagen = null;

        switch(direccion) {
        case "up":
            if (spriteNum == 1){
                imagen = up1;
            }
            if (spriteNum == 2){
                imagen = up2;
            }
            break;
        case "down":
            if (spriteNum == 1){
                imagen = down1;
            }
            if (spriteNum == 2){
                imagen = down2;
            }
            break;
        case "left":
            if (spriteNum == 1){
                imagen = left1;
            }
            if (spriteNum == 2){
                imagen = left2;
            }
            break;
        case "right":
            if (spriteNum == 1){
                imagen = right1;
            }
            if (spriteNum == 2){
                imagen = right2;
            }   
            break;
        case "quieto":
            if (ultimaDireccion.equals("up")){
                imagen = up0;
            }
            else if (ultimaDireccion.equals("down")){
                imagen = down0;
            }
            else if (ultimaDireccion.equals("left")){
                imagen = left0;
            }
            else if (ultimaDireccion.equals("right")){
                imagen = right0;
            }
            else {
                imagen = down0;
            }
            break;
        default:
            imagen = down1;
            break;
        
        }

        grafico.drawImage(imagen, x, y, (int) (lab.anchoBloque*1.5), (int) (lab.altoBloque*1.5), null);
    }

    
}


