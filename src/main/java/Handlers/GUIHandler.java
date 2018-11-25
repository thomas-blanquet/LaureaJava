package Handlers;

import Items.GraphicPanel;

import javax.swing.*;

/*
 *  Make the GUI part easier to handle.
 */
public class GUIHandler {
    private JFrame window = null;

    /*
     *  Constructor with size and title.
     */
    public GUIHandler(Integer x, Integer y, String title) {
        this.window = new JFrame();
        this.window.setSize(x, y);
        this.window.setTitle(title);
        this.window.setLocationRelativeTo(null);
        this.window.setResizable(false);

        this.window.setContentPane(new GraphicPanel());

        this.window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        this.window.setVisible(true);
    }
}
