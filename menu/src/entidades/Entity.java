package entidades;
import java.awt.Color;
import java.awt.Graphics;

public abstract class Entity {
    
    public void paint(Graphics grafico){
        grafico.setColor(Color.red);
        grafico.fillOval(0, 0, 0, 0);

    }
}
