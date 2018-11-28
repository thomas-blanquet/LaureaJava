package Graphic;

import DBManager.DBHandler;
import Recipe.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.text.Position;

/*
 *
 */
class Content extends JPanel{
    private Scraper scraper;

    private DBHandler dbHandler;

    private Recipe recipe;
    private Integer currentStep;

    private JButton recipeGetter;
    private JButton addFavorite;
    private JButton removeFavorite;
    private BasicArrowButton prevStep;
    private BasicArrowButton nextStep;

    private JLabel titleLabel;
    private JLabel infoLabel;
    private JLabel stepsLabel;
    private JLabel ingredientsLabel;

    private JLabel infos;
    private ImagePanel image;

    private JLabel step;
    private JList<String> ingredients;
    private JScrollPane ingredientsScrollPane;

    private JList<Recipe> favorites;
    private JScrollPane favoritesScrollPane;

    private JPanel subPan1 = new JPanel(new GridLayout(3,1));
    private JPanel subPanTitle = new JPanel(new GridLayout(1,3));
    private JPanel stepsChangerPan = new JPanel(new GridLayout(1,3));
    private JPanel subPan2 = new JPanel(new GridLayout(1,3));
    private JPanel subPanInfos = new JPanel(new GridLayout(2,1));
    private JPanel subPanFavorites = new JPanel(new GridLayout(1,3));
    private JPanel subPanFavoritesButton = new JPanel(new GridLayout(2,1));

    Content() {
        super(new GridLayout(3,1));

        this.scraper = new Scraper();

        this.dbHandler = new DBHandler();

        this.titleLabel = new JLabel("<html>Recipe</html>", SwingConstants.CENTER);
        this.titleLabel.setFont(new Font(this.titleLabel.getFont().getName(), Font.PLAIN, TITLEFONT));

        this.infoLabel = new JLabel("<html>About the recipe</html>", SwingConstants.CENTER);
        this.stepsLabel = new JLabel("<html>Steps</html>", SwingConstants.CENTER);
        this.ingredientsLabel = new JLabel("<html>Ingredients</html>", SwingConstants.CENTER);

        this.infos = new JLabel("<html>Time<br>Quantities</html>", SwingConstants.CENTER);

        this.step = new JLabel("<html>Current step</html>", SwingConstants.CENTER);
        this.step.setFont(new Font(this.titleLabel.getFont().getName(), Font.PLAIN, STEPFONT));

        this.ingredients = new JList<>(new String[]{"<html>Ingredients</html>"});
        this.ingredientsScrollPane = new JScrollPane(this.ingredients, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        this.recipeGetter = new JButton("<html>Find a recipe</html>");
        this.recipeGetter.setFont(new Font(this.recipeGetter.getFont().getName(), Font.PLAIN, TITLEFONT));
        this.setRecipeGetter();

        this.addFavorite = new JButton("<html>Add to favorite</html>");
        this.addFavorite.setFont(new Font(this.addFavorite.getFont().getName(), Font.PLAIN, TITLEFONT));
        this.addFavorite.setEnabled(false);
        this.setAddFavorite();

        this.removeFavorite = new JButton("<html>Remove from favorite</html>");
        this.removeFavorite.setFont(new Font(this.removeFavorite.getFont().getName(), Font.PLAIN, TITLEFONT));
        this.removeFavorite.setEnabled(false);
        this.setRemoveFavorite();

        this.image = new ImagePanel();

        this.prevStep = new BasicArrowButton(BasicArrowButton.WEST);
        this.prevStep.setEnabled(false);
        this.nextStep = new BasicArrowButton(BasicArrowButton.EAST);
        this.nextStep.setEnabled(false);
        this.setStepChanger(this.prevStep, this.nextStep, PREV);
        this.setStepChanger(this.nextStep, this.prevStep, NEXT);

        favorites = new JList<>();
        favorites.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                JList list = (JList)evt.getSource();
                if (evt.getClickCount() == 2) {
                    int index = list.locationToIndex(evt.getPoint());
                    recipe = favorites.getModel().getElementAt(index);
                    updateRecipe();
                }
            }
        });

        this.updateFavorites();
        this.favoritesScrollPane = new JScrollPane(this.favorites, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        this.assemble();
    }

    private void updateFavorites() {
        DefaultListModel<Recipe> favModel = new DefaultListModel<>();
        for (Recipe elem : this.dbHandler.getFavorites()) {
            favModel.addElement(elem);
        }
        if (favModel.size() >= 0) {
            this.favorites.setModel(favModel);
        }
    }

    private void setRecipeGetter() {
        recipeGetter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                recipe = scraper.generate();

                updateRecipe();
            }
        });
    }

    private void setAddFavorite() {
        addFavorite.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dbHandler.addFavorite(recipe);

                updateFavorites();

                int index = favorites.getNextMatch(recipe.getTitle(),0, Position.Bias.Forward);
                addFavorite.setEnabled(index == -1);
                removeFavorite.setEnabled(index != -1);
            }
        });
    }

    private void setRemoveFavorite() {
        removeFavorite.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = favorites.getNextMatch(recipe.getTitle(),0, Position.Bias.Forward);
                if (index != -1) {
                    dbHandler.removeFavorite(recipe);
                    updateFavorites();
                    addFavorite.setEnabled(true);
                    removeFavorite.setEnabled(false);
                }
            }
        });
    }

    private void setStepChanger(BasicArrowButton buttonClicked, BasicArrowButton otherButton, Integer type) {
        buttonClicked.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               if (recipe == null || recipe.getSteps() == null) return ;
               otherButton.setEnabled(true);
               if (type == PREV && currentStep > 0) {
                   currentStep -= 1;
                   buttonClicked.setEnabled(currentStep != 0);
               } else {
                   currentStep += 1;
                   buttonClicked.setEnabled(currentStep != recipe.getSteps().size() - 1);
               }
               step.setText("<html>" + recipe.getSteps().get(currentStep) + "</html>");
            }
        });
    }

    private void updateRecipe() {
        if (this.recipe == null) {
            return ;
        }

        this.titleLabel.setText("<html>" + this.recipe.getTitle() + "</html>");
        this.infos.setText("<html>" + this.recipe.getTime() + "<br>" + this.recipe.getQuantity() + " " + this.recipe.getQuantity_unit() + "</html>");

        DefaultListModel<String> ingrModel = new DefaultListModel<>();
        for (String elem : this.recipe.getIngredients()) {
            ingrModel.addElement(elem);
        }
        this.ingredients.setModel(ingrModel);

        this.currentStep = 0;
        this.step.setText("<html>" + recipe.getSteps().get(currentStep) + "</html>");

        this.prevStep.setEnabled(false);
        if (this.recipe.getSteps().size() > 1) this.nextStep.setEnabled(true); else this.nextStep.setEnabled(false);

        int index;
        if (favorites.getModel().getSize() > 0) {
            index = favorites.getNextMatch(recipe.getTitle(), 0, Position.Bias.Forward);
        } else {
            index = -1;
        }
        addFavorite.setEnabled(index == -1);
        removeFavorite.setEnabled(index != -1);

        image.changeImage(recipe.getImage());
        image.repaint();
    }

    private void assemble() {
        this.stepsChangerPan.add(this.prevStep);
        this.stepsChangerPan.add(this.stepsLabel);
        this.stepsChangerPan.add(this.nextStep);

        this.subPanTitle.add(this.infoLabel);
        this.subPanTitle.add(this.stepsChangerPan);
        this.subPanTitle.add(this.ingredientsLabel);

        this.subPanInfos.add(image);
        this.subPanInfos.add(this.infos);

        this.subPan1.add(this.recipeGetter);
        this.subPan1.add(this.titleLabel);
        this.subPan1.add(this.subPanTitle);

        this.subPan2.add(this.subPanInfos);

        this.subPanFavoritesButton.add(this.addFavorite);
        this.subPanFavoritesButton.add(this.removeFavorite);

        this.subPanFavorites.add(this.favoritesScrollPane);
        this.subPanFavorites.add(this.subPanFavoritesButton);

        ///////////////////////////////////

        this.subPan2.add(this.step);
        this.subPan2.add(this.ingredientsScrollPane);

        ///////////////////////////////////

        this.add(this.subPan1);
        this.add(this.subPan2);
        this.add(this.subPanFavorites);
    }

    private final static Integer TITLEFONT = 20;
    private final static Integer STEPFONT = 15;

    private final static Integer PREV = -1;
    private final static Integer NEXT = 1;
}
