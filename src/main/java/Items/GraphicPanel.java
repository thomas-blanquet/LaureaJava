package Items;

import java.awt.*;
import javax.imageio.ImageIO;
import javax.swing.*;

public class GraphicPanel extends JPanel {
    private JLabel titleLabel;
    private Recipe recipe;

    public GraphicPanel() {
        super(new GridLayout(2,1));

        this.recipe = null;

        JPanel subPan1 = new JPanel(new GridLayout(3,1));
        JPanel subPan1_1 = new JPanel(new GridLayout(1,3));
        JPanel subPan2 = new JPanel(new GridLayout(1,3));

        this.titleLabel = new JLabel("Recipe", SwingConstants.CENTER);
        this.titleLabel.setFont(new Font(this.titleLabel.getFont().getName(), Font.PLAIN, TITLEFONT));

        JLabel infoLabel = new JLabel("About the recipe", SwingConstants.CENTER);
        JLabel stepsLabel = new JLabel("Steps", SwingConstants.CENTER);
        JLabel ingredientsLabel = new JLabel("Ingredients", SwingConstants.CENTER);

        JButton recipeGetter = new JButton("Find a recipe");
        recipeGetter.setPreferredSize(new Dimension(100, 100));

        subPan1_1.add(infoLabel);
        subPan1_1.add(stepsLabel);
        subPan1_1.add(ingredientsLabel);
        subPan1.add(recipeGetter);
        subPan1.add(titleLabel);
        subPan1.add(subPan1_1);
        this.add(subPan1);
    }

    private static Integer TITLEFONT = 20;
}
