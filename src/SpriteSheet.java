import java.awt.image.BufferedImage;

public class SpriteSheet {

    private final BufferedImage image;

    public SpriteSheet(BufferedImage image){
        this.image = image;
    }

    public BufferedImage grabImage(int num, int col, int width, int height){
        return image.getSubimage((num*57) - 57,(col*57) - 57, width, height);
    }

}
