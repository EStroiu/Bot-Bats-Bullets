package entities;

import entities.graphics.SpriteSheet;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Block extends GameObject {

    private final BufferedImage block;

    public Block(float x, float y, ID id, SpriteSheet ss) {
        super(x, y, id, ss);

        block = ss.grabImage(1,1,57,57);
    }

    public void tick() {

    }

    public void render(Graphics g) {
        g.drawImage(block,(int)x,(int)y,null);
    }

    public Rectangle getBounds() {
        return new Rectangle((int)x,(int)y,57,57);
    }
}
