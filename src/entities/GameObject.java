package entities;

import entities.graphics.SpriteSheet;

import java.awt.*;

public abstract class GameObject {

    protected float x,y;
    protected float velX = 0, velY = 0;
    protected ID id;
    protected SpriteSheet ss;


    public GameObject(float x, float y, ID id, SpriteSheet ss){
        this.x = x;
        this.y = y;
        this.id = id;
        this.ss = ss;
    }

    public abstract void tick();
    public abstract void render(Graphics g);
    public abstract Rectangle getBounds();

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

}
