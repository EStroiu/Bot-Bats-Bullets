package entities.moving;

import entities.GameObject;
import entities.ID;
import entities.graphics.SpriteSheet;
import gameStates.Game;
import entities.graphics.Animation;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends GameObject {

    private final Handler handler;
    Game game;

    private final BufferedImage[] player_image = new BufferedImage[22];

    Animation running_right,running_left, running_up_and_down;

    public Player(float x, float y, ID id, Handler handler, Game game, SpriteSheet ss) {
        super(x, y, id, ss);
        this.handler = handler;
        this.game = game;

        player_image[0] = ss.grabImage(4,2,57,57);

        player_image[1] = ss.grabImage(5,2,57,57);
        player_image[2] = ss.grabImage(6,2,57,57);
        player_image[3] = ss.grabImage(7,2,57,57);
        player_image[4] = ss.grabImage(8,2,57,57);
        player_image[5] = ss.grabImage(9,2,57,57);
        player_image[6] = ss.grabImage(10,2,57,57);
        player_image[7] = ss.grabImage(11,2,57,57);

        player_image[8] = ss.grabImage(5,3,57,57);
        player_image[9] = ss.grabImage(6,3,57,57);
        player_image[10] = ss.grabImage(7,3,57,57);
        player_image[11] = ss.grabImage(8,3,57,57);
        player_image[12] = ss.grabImage(9,3,57,57);
        player_image[13] = ss.grabImage(10,3,57,57);
        player_image[14] = ss.grabImage(11,3,57,57);

        player_image[15] = ss.grabImage(5,4,57,57);
        player_image[16] = ss.grabImage(6,4,57,57);
        player_image[17] = ss.grabImage(7,4,57,57);
        player_image[18] = ss.grabImage(8,4,57,57);
        player_image[19] = ss.grabImage(9,4,57,57);
        player_image[20] = ss.grabImage(10,4,57,57);
        player_image[21] = ss.grabImage(11,4,57,57);



        running_right = new Animation(3,player_image[1],
        player_image[2],
        player_image[3],
        player_image[4],
        player_image[5],
        player_image[6],
        player_image[7]);

        running_left = new Animation(3,player_image[8],
                player_image[9],
                player_image[10],
                player_image[11],
                player_image[12],
                player_image[13],
                player_image[14]);

        running_up_and_down = new Animation(3,player_image[15],
                player_image[16],
                player_image[17],
                player_image[18],
                player_image[19],
                player_image[20],
                player_image[21]);
    }

    public void tick() {
        x += velX;
        y += velY;

        //Vertical Movement
        float _acc = 1f;
        float _dcc = 0.5f;
        if(handler.isUp()) velY -= _acc;
        else if(handler.isDown()) velY += _acc;
        else if(!handler.isUp() && !handler.isDown()){
            if(velY > 0) velY -= _dcc;
            else if(velY < 0) velY += _dcc;
        }
        //Horizontal Movement
        if(handler.isLeft()) velX -= _acc;
        else if(handler.isRight()) velX += _acc;
        else if(!handler.isLeft() && !handler.isRight()){
            if(velX > 0) velX -= _dcc;
            else if(velX < 0) velX += _dcc;
        }

        velX = clamp(velX, -5,5);
        velY = clamp(velY, -5,5);

        collision();

        running_right.runAnimation();
        running_left.runAnimation();
        running_up_and_down.runAnimation();

        if(game.hp <= 0){
            handler.removeObject(this);
        }

    }

    private void collision(){
        for (int i = 0; i < handler.getObject().size(); i++) {

            GameObject tempObject = handler.getObject().get(i);

            if(tempObject.getId() == ID.Block){

                if(getBounds().intersects(tempObject.getBounds())){

                    if(velX > 0){ //Right

                        velX = 0;
                        x = tempObject.getX() - 36 * game.scale;

                    }else if(velX < 0) { //Left

                        velX = 0;
                        x = tempObject.getX() + 57 * game.scale; //Width of the block

                    }

                }

                if(getBounds2().intersects(tempObject.getBounds())){

                    if(velY > 0){ //Down

                        velY = 0;
                        y = tempObject.getY() - 50 * game.scale;


                    }else if(velY < 0) { //Up

                        velY = 0;
                        y = tempObject.getY() + 57 * game.scale; //Width of the block

                    }

                }
            }

            if(tempObject.getId() == ID.Crate){

                if(getBoundsNormal().intersects(tempObject.getBounds())){
                    game.ammo+=10;
                    handler.removeObject(tempObject);
                }
            }

            if(tempObject.getId() == ID.Enemy){

                if(getBoundsNormal().intersects(tempObject.getBounds())){
                    game.hp--;

                }
            }
        }
    }



    public void render(Graphics g) {

        if(velX > 0) {
            running_right.drawAnimation(g, x, y, 0);
        }
        else if(velX < 0) {
            running_left.drawAnimation(g, x, y, 0);
        }
        else if(velY != 0) {
            running_up_and_down.drawAnimation(g, x, y, 0);
        }
        else {
            g.drawImage(player_image[0],(int)x,(int)y,null);
        }

    }

    public Rectangle getBoundsNormal(){
        return new Rectangle((int)x,(int)y,36 * game.scale,50 * game.scale);
    }

    public Rectangle getBounds(){

        float bx = x + velX;
        float by = y;
        float bw = 36 * game.scale + velX/2;
        float bh = 50 * game.scale;


        return new Rectangle((int)bx,(int)by,(int)bw,(int)bh);
    }

    public Rectangle getBounds2(){

        float bx = x;
        float by = y + velY;
        float bw = 36 * game.scale;
        float bh = 50 * game.scale + velY/2;

        return new Rectangle((int)bx,(int)by,(int)bw,(int)bh);
    }

    public static float clamp(float val, float min, float max) {
        return Math.max(min, Math.min(max, val));
    }
}
