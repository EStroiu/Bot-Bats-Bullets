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
            if (mouseOver(mx, my, 340, 175, 300, 64)) {
                game.gameState = Game.STATE.Game;

                game.camera = new Camera(0, 0);

                game.addMouseListener(new MouseInput(handler, game.camera, game, game.ss));

                game.enemyNum = 1;
                game.loadLevel(game.levels[(int) (Math.random() * ((7) + 1))]);
                game.enemyNum--;
            }

             //quit button
            if(mouseOver(mx,my,340,375,300,64)){
                System.exit(1);
            }

            //help button
            if(mouseOver(mx,my,340,275,300,64)){
                game.gameState = Game.STATE.Help;
            }

        }

        if(game.gameState == Game.STATE.Help){
            if(mouseOver(mx,my,335,400,300,64)){
                game.gameState = Game.STATE.Menu;
            }
        }

        if(game.gameState == Game.STATE.End){

            if (mouseOver(mx, my, 340,275,300,64)) {

                game.gameState = Game.STATE.Game;

                game.score = 0;
                game.ammo = 101;

                game.enemyNum = 1;
                game.loadLevel(game.levels[(int) (Math.random() * ((7) + 1))]);
                game.enemyNum--;
            }

            if(mouseOver(mx,my,340,375,300,64)){
                System.exit(1);
            }
        }

    }



    public void mouseReleased(MouseEvent e){

    }

    private boolean mouseOver(int mx, int my, int x, int y, int width, int height){
        if(mx > x && mx < x + width){
            if(my > y && my < y + height){
                return true;
            }else return false;
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


        pixelFont1 = pixelFont1.deriveFont(60F);

        if(game.gameState == Game.STATE.Menu) {
            g.setColor(Color.DARK_GRAY);
            g.fillRect(0,0,1000,563);
            g.setColor(Color.white);
            g.drawRect(340,175,300,64);
            g.drawRect(340,275,300,64);
            g.drawRect(340,375,300,64);

            g.setFont(pixelFont1);
            g.setColor(Color.white);
            g.drawString("Menu",420,115);

            pixelFont1 = pixelFont1.deriveFont(35F);

            g.setFont(pixelFont1);
            g.drawString("Start",450,220);
            g.drawString("Help",460,320);
            g.drawString("Quit",463,420);

        }else if(game.gameState == Game.STATE.Help){

            g.setColor(Color.DARK_GRAY);
            g.fillRect(0,0,1000,563);
            g.setColor(Color.white);

            pixelFont1 = pixelFont1.deriveFont(30F);
            g.setFont(pixelFont1);
            g.drawString("Controls:",50,50);
            g.drawString("W - Up ",50,100);
            g.drawString("S - Down ",50,150);
            g.drawString("A - Left ",50,200);
            g.drawString("D - Right ",50,250);
            g.drawString("P - Pause ",50,300);
            g.drawString("Aim with your mouse and shoot with left mouse button ",50,350);

            pixelFont1 = pixelFont1.deriveFont(35F);
            g.setFont(pixelFont1);
            g.setColor(Color.white);

            g.drawRect(340,400,300,64);
            g.drawString("Back",453,445);

        } else if(game.gameState == Game.STATE.End) {

            pixelFont1 = pixelFont1.deriveFont(21F);

            g.setFont(pixelFont1);
            g.setColor(Color.gray);
            g.fillRect(5, 5, 200, 32);
            g.setColor(Color.white);
            g.drawRect(5, 5, 200, 32);
            g.setColor(Color.white);
            g.drawString("0", 100, 30);


            pixelFont1 = pixelFont1.deriveFont(60F);
            g.setFont(pixelFont1);
            g.setColor(Color.RED);
            g.drawString("Game Over",340,150);

            g.setColor(Color.white);
            pixelFont1 = pixelFont1.deriveFont(35F);
            g.setFont(pixelFont1);
            g.drawString("Score: " + game.score,430,220);


            g.drawRect(340,275,300,64);
            g.drawString("Restart",435,320);

            g.drawRect(340,375,300,64);
            g.drawString("Quit",463,420);


        }

    }

}
