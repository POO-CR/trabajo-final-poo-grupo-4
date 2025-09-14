import controller.MenuController;
import javax.swing.SwingUtilities;



public class Main {
    public static void main(String[] args) throws Exception {

        /*try {
            UIManager.setLookAndFeel(new FlatDarkLaf());

        } catch (Exception e) {
            System.err.println("Failed to initialize LaF");
        } */

        SwingUtilities.invokeLater(() -> {
            new MenuController();
        });

    }
}
