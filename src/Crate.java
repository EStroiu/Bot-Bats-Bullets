import java.awt.*;
import java.awt.image.BufferedImage;

public class Crate extends GameObject {

    private final BufferedImage battery;

    public Crate(float x, float y, ID id, SpriteSheet ss) {
        super(x, y, id, ss);

        battery = ss.grabImage(1,3,57,57);
    }

    public void tick() {

    }

    public void render(Graphics g) {
       g.drawImage(battery,(int)x,(int)y,null);
    }

    public Rectangle getBounds() {
        return new Rectangle((int)x,(int)y+10,32,32);
    }
}
