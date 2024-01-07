package entities.moving;

import entities.GameObject;
import entities.ID;
import entities.graphics.SpriteSheet;

import java.awt.*;

public class Bullet extends GameObject {

    private final Handler handler;

    public Bullet(float x, float y, ID id, Handler handler, int mx, int my, SpriteSheet ss) {
        super(x, y, id, ss);
        this.handler = handler;
        velX = (mx - x)/9;
        velY = (my - y)/9;
    }

    public void tick() {
        x += velX;
        y += velY;

        for (int i = 0; i < handler.object.size(); i++) {
            GameObject tempObject = handler.object.get(i);

            if(tempObject.getId() == ID.Block){
                if(getBounds().intersects(tempObject.getBounds()))
                    handler.removeObject(this);
            }
        }
    }

    public void render(Graphics g) {
        g.setColor(Color.red);
        g.fillOval((int)x,(int)y,8 * ss.scale,8 * ss.scale);
    }

    public Rectangle getBounds() {
        return new Rectangle((int)x,(int)y,8 * ss.scale,8 * ss.scale);
    }
}
