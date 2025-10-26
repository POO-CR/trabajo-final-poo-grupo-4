import javax.swing.SwingUtilities;

import controller.menu.MenuController;




public class Main {
    public static void main(String[] args) throws Exception {

        SwingUtilities.invokeLater(() -> {
            System.out.println("arrancó main");
            MenuController mc = new MenuController();
        });

        

    }
}
