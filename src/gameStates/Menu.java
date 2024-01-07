package gameStates;

import input.MouseInput;
import entities.moving.Camera;
import entities.moving.Handler;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.io.IOException;

public class Menu extends MouseAdapter {

    private final Game game;
    private final Handler handler;

    public Font pixelFont1;
    public Menu(Game game, Handler handler){
        this.game = game;
        this.handler = handler;
    }

    public void mousePressed(MouseEvent e){
        int mx = e.getX();
        int my = e.getY();

        if(game.gameState == Game.STATE.Menu) {

            // play button
            if (mouseOver(mx, my, 340 * game.scale, 175 * game.scale, 300 * game.scale, 64 * game.scale)) {
                game.camera = new Camera(0, 0);
                game.gameState = Game.STATE.Game;

                game.addMouseListener(new MouseInput(handler, game.camera, game, game.ss));

                game.enemyNum = 1;
                game.loadLevel(game.levels[(int) (Math.random() * ((7) + 1))]);
                game.enemyNum--;
            }

             //quit button
            if(mouseOver(mx,my,340 * game.scale,375 * game.scale,300 * game.scale,64 * game.scale)){
                System.exit(1);
            }

            //help button
            if(mouseOver(mx,my,340 * game.scale,275 * game.scale,300 * game.scale,64 * game.scale)){
                game.gameState = Game.STATE.Help;
            }

        }

        if(game.gameState == Game.STATE.Help){
            if(mouseOver(mx,my,335 * game.scale,400 * game.scale,300 * game.scale,64 * game.scale)){
                game.gameState = Game.STATE.Menu;
            }
        }

        if(game.gameState == Game.STATE.End){

            if (mouseOver(mx, my, 340 * game.scale,275 * game.scale,300 * game.scale,64 * game.scale)) {

                game.gameState = Game.STATE.Game;

                game.score = 0;
                game.ammo = 101;

                game.enemyNum = 1;
                game.loadLevel(game.levels[(int) (Math.random() * ((7) + 1))]);
                game.enemyNum--;
            }

            if(mouseOver(mx,my,340 * game.scale,375 * game.scale,300 * game.scale,64 * game.scale)){
                System.exit(1);
            }
        }

    }

    private boolean mouseOver(int mx, int my, int x, int y, int width, int height){
        if(mx > x && mx < x + width){
            return my > y && my < y + height;
        }else return false;
    }

    public void tick(){

    }

    public void render(Graphics g){

        try {
            pixelFont1 = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream("src/fonts/VPPixel-Simplified.ttf"));
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }


        pixelFont1 = pixelFont1.deriveFont(60F * game.scale);

        if(game.gameState == Game.STATE.Menu) {
            // bg
            g.setColor(Color.DARK_GRAY);
            g.fillRect(0,0,game.XRes * game.scale, game.YRes * game.scale);
            g.setColor(Color.white);


            g.drawRect(340 * game.scale,175 * game.scale,300 * game.scale,64 * game.scale);
            g.drawRect(340 * game.scale,275 * game.scale,300 * game.scale,64 * game.scale);
            g.drawRect(340 * game.scale,375 * game.scale,300* game.scale,64 * game.scale);

            g.setFont(pixelFont1);
            g.setColor(Color.white);
            g.drawString("Menu",420 * game.scale, 115 * game.scale);

            pixelFont1 = pixelFont1.deriveFont(35F * game.scale);

            g.setFont(pixelFont1);
            g.drawString("Start",450 * game.scale, 220 * game.scale);
            g.drawString("Help",460 * game.scale, 320 * game.scale);
            g.drawString("Quit",463 * game.scale, 420 * game.scale);

        }else if(game.gameState == Game.STATE.Help){

            g.setColor(Color.DARK_GRAY);
            g.fillRect(0,0,1000 * game.scale, 563 * game.scale);
            g.setColor(Color.white);

            pixelFont1 = pixelFont1.deriveFont(30F * game.scale);
            g.setFont(pixelFont1);
            g.drawString("Controls:",50 * game.scale,50 * game.scale);
            g.drawString("W - Up ",50 * game.scale,100 * game.scale);
            g.drawString("S - Down ",50 * game.scale, 150 * game.scale);
            g.drawString("A - Left ",50 * game.scale,200 * game.scale);
            g.drawString("D - Right ",50 * game.scale,250 * game.scale);
            g.drawString("P - Pause ",50 * game.scale,300 * game.scale);
            g.drawString("Aim with your mouse and shoot with left mouse button ",50 * game.scale,350 * game.scale);

            pixelFont1 = pixelFont1.deriveFont(35F * game.scale);
            g.setFont(pixelFont1);
            g.setColor(Color.white);

            g.drawRect(340* game.scale,400 * game.scale,300 * game.scale,64* game.scale);
            g.drawString("Back",453 * game.scale,445 * game.scale);

        } else if(game.gameState == Game.STATE.End) {

            pixelFont1 = pixelFont1.deriveFont(21F * game.scale);

            g.setFont(pixelFont1);
            g.setColor(Color.gray);
            g.fillRect(5 * game.scale, 5 * game.scale, 200 * game.scale, 32* game.scale);
            g.setColor(Color.white);
            g.drawRect(5 * game.scale, 5 * game.scale, 200 * game.scale, 32 * game.scale);
            g.setColor(Color.white);
            g.drawString("0", 100 * game.scale, 30 * game.scale);


            pixelFont1 = pixelFont1.deriveFont(60F * game.scale);
            g.setFont(pixelFont1);
            g.setColor(Color.RED);
            g.drawString("Game Over",340 * game.scale,150 * game.scale);

            g.setColor(Color.white);
            pixelFont1 = pixelFont1.deriveFont(35F * game.scale);
            g.setFont(pixelFont1);
            g.drawString("Score: " + game.score,430 * game.scale,220 * game.scale);


            g.drawRect(340 * game.scale,275 * game.scale,300 * game.scale,64 * game.scale);
            g.drawString("Restart",435 * game.scale,320 * game.scale);

            g.drawRect(340 * game.scale,375 * game.scale,300 * game.scale,64 * game.scale);
            g.drawString("Quit",463 * game.scale,420 * game.scale);


        }

    }

}
