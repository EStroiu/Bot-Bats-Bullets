package input;

import entities.graphics.SpriteSheet;
import entities.moving.Bullet;
import entities.moving.Camera;
import entities.moving.Handler;
import gameStates.Game;
import entities.*;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseInput extends MouseAdapter {

    private final Handler handler;
    private final Camera camera;
    private final Game game;
    private final SpriteSheet ss;

    public MouseInput(Handler handler, Camera camera, Game game, SpriteSheet ss){
        this.handler = handler;
        this.camera = camera;
        this.game = game;
        this.ss = ss;
    }

    public void mousePressed(MouseEvent e){
        int mx = (int) (e.getX() + camera.getX());
        int my = (int) (e.getY() + camera.getY());

        for (int i = 0; i < handler.getObject().size(); i++) {
            GameObject tempObject = handler.getObject().get(i);

            if(tempObject.getId() == ID.Player && game.ammo >= 1){
                handler.addObject(new Bullet(tempObject.getX()+16, tempObject.getY() + 24, ID.Bullet, handler, mx, my,ss));
                game.ammo--;
            }
        }
    }

}
