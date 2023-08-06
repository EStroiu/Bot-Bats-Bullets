package entities.moving;

import entities.GameObject;
import entities.ID;
import entities.graphics.SpriteSheet;
import gameStates.Game;
import entities.graphics.Animation;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Enemy extends GameObject {

    private final Handler handler;
    Random r = new Random();
    int choose = 0;
    int hp = 100;
    Game game;

    Animation anim;

    public Enemy(float x, float y, ID id, Handler handler, Game game, SpriteSheet ss) {
        super(x, y, id, ss);
        this.handler = handler;
        this.game = game;

        BufferedImage[] bat = new BufferedImage[3];
        bat[0] = ss.grabImage(1,2,57,57);
        bat[1] = ss.grabImage(2,2,57,57);
        bat[2] = ss.grabImage(3,2,57,57);

        anim = new Animation(3, bat[0], bat[1], bat[2]);
    }

    public void tick() {

        if (game.hp > 0){
            x += velX;
        y += velY;

        choose = r.nextInt(15);

        for (int i = 0; i < handler.getObject().size(); i++) {
            GameObject tempObject = handler.getObject().get(i);

            if (tempObject.getId() == ID.Block) {
                if (getBoundsBig().intersects(tempObject.getBounds())) {

                    if (getBoundsUR().intersects(tempObject.getBounds())) {
                        velX *= -1;
                        velY *= -1;
                        x -= 2;
                        y += 2;
                    } else if (getBoundsUL().intersects(tempObject.getBounds())) {
                        velX *= -1;
                        velY *= -1;
                        x += 2;
                        y += 2;
                    } else if (getBoundsLR().intersects(tempObject.getBounds())) {
                        velX *= -1;
                        velY *= -1;
                        x -= 2;
                        y -= 2;
                    } else if (getBoundsLL().intersects(tempObject.getBounds())) {
                        velX *= -1;
                        velY *= -1;
                        x += 2;
                        y -= 2;
                    } else if (getBoundsUR().intersects(tempObject.getBounds()) && getBoundsUL().intersects(tempObject.getBounds())) {
                        velY *= -1;
                        velX *= -1;
                        y += 2;
                    } else if (getBoundsLR().intersects(tempObject.getBounds()) && getBoundsLL().intersects(tempObject.getBounds())) {
                        velY *= -1;
                        velX *= -1;
                        y -= 2;
                    } else if (getBoundsUR().intersects(tempObject.getBounds()) && getBoundsLR().intersects(tempObject.getBounds())) {
                        velX *= -1;
                        velX *= -1;
                        x -= 2;
                    } else if (getBoundsUL().intersects(tempObject.getBounds()) && getBoundsLL().intersects(tempObject.getBounds())) {
                        velX *= -1;
                        velX *= -1;
                        x += 2;
                    }

                } else if (choose == 0) {
                    velX = (r.nextInt(3 - -3) + -3);
                    velY = (r.nextInt(3 - -3) + -3);
                }
            }

            if (tempObject.getId() == ID.Player) {
                if (chasePlayer().intersects(tempObject.getBounds())) {
                    if (tempObject.getX() > x) x += 2;
                    else x -= 2;
                    if (tempObject.getY() > y) y += 2;
                    else y -= 2;
                    velX = 1;
                    velY = 1;
                }
            }

            if (tempObject.getId() == ID.Bullet) {
                if (getBounds().intersects(tempObject.getBounds())) {
                    hp -= 50;
                    handler.removeObject(tempObject);
                }
            }

        }

        if (hp <= 0) {
            handler.removeObject(this);
            game.enemyNum--;
            game.score++;
        }

        anim.runAnimation();

    }
    }

    public void render(Graphics g) {

        if(game.hp>0) {
            anim.drawAnimation(g, x - 12, y - 12, 0);
        }

    }

    public Rectangle getBounds() {
        return new Rectangle((int)x,(int)y,38,38);
    }

    public Rectangle getBoundsUL() {
        return new Rectangle((int)x-12,(int)y-12,30,30);
    }

    public Rectangle getBoundsUR() {
        return new Rectangle((int)x+18,(int)y-12,30,30);
    }

    public Rectangle getBoundsLL() {
        return new Rectangle((int)x-12,(int)y+18,30,30);
    }

    public Rectangle getBoundsLR() {
        return new Rectangle((int)x+18,(int)y+18,30,30);
    }

    public Rectangle getBoundsBig() {
        return new Rectangle((int)x-12,(int)y-12,60,60);
    }

    public Rectangle chasePlayer() {
        return new Rectangle((int)x-180,(int)y-180,400,400);
    }
}
