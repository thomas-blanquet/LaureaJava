import javax.swing.JFrame;

/*
 *  Make the GUI part easier to handle.
 */
public class GUIHandler {
    JFrame window = null;

    GUIHandler(Integer x, Integer y, String title) {
        this.window = new JFrame();
        this.window.setSize(x, y);
        this.window.setTitle(title);
        this.window.setLocationRelativeTo(null);
        this.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.window.setVisible(true);
    }
}
