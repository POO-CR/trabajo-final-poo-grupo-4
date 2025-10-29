import javax.swing.SwingUtilities;

import controller.ControladorPrincipal;


public class Main {

    public static void main(String[] args) throws Exception {
        SwingUtilities.invokeLater(() -> {
            System.out.println("arrancó main");
            ControladorPrincipal controladorPrincipal = new ControladorPrincipal();
        });

    }
}
