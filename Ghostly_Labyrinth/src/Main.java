import controlador.MenuController;
import javax.swing.SwingUtilities;



public class Main {
    public static void main(String[] args) throws Exception {
        SwingUtilities.invokeLater(() -> {
            System.out.println("arrancó main");
            MenuController mc = new MenuController();
        });

    }
}
