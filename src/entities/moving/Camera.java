package entities.moving;

import entities.GameObject;

public class Camera {

    private float x,y;

    public Camera(float x, float y){
        this.x = x;
        this.y = y;
    }

    public void tick(GameObject object){

        x += ((object.getX() - x) - (float) 1000 /2)*0.05f;
        y += ((object.getY() - y) - (float) 563 /2)*0.05f;

    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }


}
