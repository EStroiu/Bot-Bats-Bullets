package entities.moving;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import entities.GameObject;

public class Camera {

    GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    int width = gd.getDisplayMode().getWidth(); 
    int height = gd.getDisplayMode().getHeight();
    private int XRes = 1000;
    private int scale = Math.max(1, width / XRes);

    private float x,y;

    public Camera(float x, float y){
        this.x = x;
        this.y = y;
    }

    public void tick(GameObject object){

        x += ((object.getX() - x) - (float) 1000* scale /2)*0.05f;
        y += ((object.getY() - y) - (float) 563* scale /2)*0.05f;

    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }


}
