package entities.graphics;

import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;

public class SpriteSheet {
    GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    int width = gd.getDisplayMode().getWidth(); 
    int height = gd.getDisplayMode().getHeight();

    private final BufferedImage image;
    private int XRes = 1000;
    public int scale = Math.max(1, width / XRes);

    public SpriteSheet(BufferedImage image){
        this.image = image;
    }

    public BufferedImage grabImage(int num, int col, int width, int height){
        BufferedImage original = image.getSubimage((num*57) - 57,(col*57) - 57, width, height);
        if(scale > 1){
            BufferedImage resized = new BufferedImage(width * scale, height * scale, original.getType());
            Graphics2D g2d = resized.createGraphics();
            g2d.drawImage(original, 0, 0, width * scale, height * scale, null);
            g2d.dispose();
            return resized;
        }
        return image.getSubimage((num*57) - 57,(col*57) - 57, width, height);
    }

}
