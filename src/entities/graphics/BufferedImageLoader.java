package entities.graphics;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class BufferedImageLoader {
    private BufferedImage image;

    public BufferedImage loadImage(String path){
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResource(path)));
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        return image;
    }
}
