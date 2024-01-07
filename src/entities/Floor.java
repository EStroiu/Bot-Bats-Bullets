package entities;

import entities.graphics.SpriteSheet;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Floor extends GameObject {

    private final BufferedImage floor_image;

    public Floor(float x, float y, ID id, SpriteSheet ss) {
        super(x, y, id, ss);
        floor_image = ss.grabImage((int)(Math.random() * (4 - 2 + 1) + 2),1,57,57);
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {

        g.drawImage(floor_image,(int)x,(int)y,null);

    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int)x,(int)y,57 * ss.scale,57 * ss.scale);
    }
}
