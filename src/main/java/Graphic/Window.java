package Graphic;

import javax.swing.*;

/*
 *  Window object
 */
public class Window extends JFrame {
    /*
     *  Constructor with size and title.
     */
    public Window(Integer x, Integer y, String title) {
        this.setSize(x, y);
        this.setTitle(title);
        this.setLocationRelativeTo(null);
        this.setResizable(false);

        this.setContentPane(new Content());

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        this.setVisible(true);
    }
}
