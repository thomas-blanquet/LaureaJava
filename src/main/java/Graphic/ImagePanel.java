package Graphic;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class ImagePanel extends JPanel{

    private BufferedImage image;

    public ImagePanel() {

    }

    /*
     * Change image of the panel.
     * Used when a new recipe is laoded
     */
    public void changeImage(String imageURL) {
        try {
            URL url = new URL(imageURL);
            BufferedImage imagetmp = ImageIO.read(url);
            double height = (double)imagetmp.getHeight() * (double)(this.getWidth() - 20) / (double)imagetmp.getWidth();
            image = this.toBufferedImage(imagetmp.getScaledInstance(this.getWidth() - 20, (int)height, Image.SCALE_SMOOTH));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
     * Change an Image to a BufferedImage.
     * Used because the resizing return an Image but BufferedImage is used by the Panel
     */
    private BufferedImage toBufferedImage(Image img)
    {
        if (img instanceof BufferedImage) {
            return (BufferedImage) img;
        }

        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();

        return bimage;
    }

    /*
     * Method called while the component is displayed to draw it.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null)
            g.drawImage(image, 5, 0 - image.getHeight() / 2 + this.getHeight() / 2, this);
    }

}