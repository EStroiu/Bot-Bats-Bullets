package gameStates;

import entities.graphics.SpriteSheet;
import entities.graphics.Window;
import entities.moving.Camera;
import entities.moving.Enemy;
import entities.moving.Handler;
import entities.moving.Player;
import input.KeyInput;
import entities.*;
import entities.graphics.BufferedImageLoader;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

public class Game extends Canvas implements Runnable {
    private boolean isRunning = false;
    private Thread thread;
    private final Handler handler;
    public Camera camera;
    public SpriteSheet ss;

    public static boolean paused = false;

    public BufferedImage[] levels;

    public int ammo = 100;
    public int hp = 100;
    public int XRes = 1000, YRes = 563;
    public int enemyNum, score = 0;
    public Font pixelFont;
    private final Menu menu;


    public enum STATE {
        Menu,
        Game,
        Help,
        End
    }

    public STATE gameState = STATE.Menu;


    public Game() {

        try {
            pixelFont = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream("src/fonts/VPPixel-Simplified.ttf"));
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }

        pixelFont = pixelFont.deriveFont(20F);

        enemyNum = 1;
        new Window(XRes, YRes, "Bot Bats Bullets", this);
        start();

        handler = new Handler();

        this.addKeyListener(new KeyInput(handler, this));

        menu = new Menu(this, handler);
        this.addMouseListener(menu);

        BufferedImageLoader loader = new BufferedImageLoader();
        BufferedImage level1 = loader.loadImage("../../res/maps/level1.png");
        BufferedImage level2 = loader.loadImage("../../res/maps/level2.png");
        BufferedImage level3 = loader.loadImage("../../res/maps/level3.png");
        BufferedImage level4 = loader.loadImage("../../res/maps/level4.png");
        BufferedImage level5 = loader.loadImage("../../res/maps/level5.png");
        BufferedImage level6 = loader.loadImage("../../res/maps/level6.png");
        BufferedImage level7 = loader.loadImage("../../res/maps/level7.png");
        BufferedImage level8 = loader.loadImage("../../res/maps/level8.png");
        levels = new BufferedImage[]{level1, level2, level3, level4, level5, level6, level7, level8};
        BufferedImage spritesheet = loader.loadImage("../../res/assets/spritesheet.png");

        ss = new SpriteSheet(spritesheet);

    }

    private void start() {
        isRunning = true;
        thread = new Thread(this);
        thread.start();
    }

    private void stop() {
        isRunning = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void tick() {


        if (gameState == STATE.Game) {
            if (!paused) {
                handler.tick();
                for (int i = 0; i < handler.getObject().size(); i++) {
                    if (handler.getObject().get(i).getId() == ID.Player) {
                        camera.tick(handler.getObject().get(i));
                    }
                }

                if (hp <= 0) {
                    hp = 100;
                    ammo = 100;

                    gameState = STATE.End;
                }

                if (enemyNum == 0) {
                    enemyNum = 1;
                    loadLevel(levels[(int) (Math.random() * ((7) + 1))]);
                    enemyNum--;
                }
            }

        } else if (gameState == STATE.Menu || gameState == STATE.End || gameState == STATE.Help) {
            handler.tick();
            menu.tick();
        }

    }

    public void render() {

        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();
        Graphics2D g2d = (Graphics2D) g;
        //------------------------------------------
        if (gameState == STATE.Game) {

            g.setColor(Color.DARK_GRAY);
            g.fillRect(0, 0, 1000, 563);

            g2d.translate(-camera.getX(), -camera.getY());

            handler.render(g);

            g2d.translate(camera.getX(), camera.getY());

            if (hp > 0)
                g.setColor(Color.gray);
            g.fillRect(5, 5, 200, 32);
            g.setColor(Color.green);
            g.fillRect(5, 5, hp * 2, 32);
            g.setColor(Color.white);
            g.drawRect(5, 5, 200, 32);
            g.setFont(pixelFont);
            g.setColor(Color.white);
            g.drawString(String.valueOf(hp), 100, 30);


            g.setColor(Color.yellow);
            g.drawString("Ammo: " + ammo, 5, 60);
            g.drawString("Score: " + score, 880, 25);
            g.setColor(Color.RED);
            g.drawString("Enemies left: " + enemyNum, 5, 85);

            if (paused) {
                g.setColor(Color.white);
                g.drawString("Paused", 455, 250);
            }

        } else if (gameState == STATE.Menu || gameState == STATE.End || gameState == STATE.Help) {
            menu.render(g);
        }
        //------------------------------------------
        g.dispose();
        bs.show();

    }

    public void loadLevel(BufferedImage image) {

        handler.getObject().clear();
        repaint();

        int w = image.getWidth();
        int h = image.getHeight();

        for (int xx1 = 0; xx1 < w; xx1++) {
            for (int yy1 = 0; yy1 < h; yy1++) {
                int pixel1 = image.getRGB(xx1, yy1);
                int red1 = (pixel1 >> 16) & 0xff;
                int green1 = (pixel1 >> 8) & 0xff;
                int blue1 = (pixel1) & 0xff;

                if (green1 == 0 && blue1 == 0 && red1 == 0 ||
                        green1 == 255 && blue1 == 0 && red1 == 0 ||
                        blue1 == 255 && green1 == 0 & red1 == 0 ||
                        green1 == 255 && blue1 == 255 && red1 == 0)
                    handler.addObject(new Floor(xx1 * 57, yy1 * 57, ID.Floor, ss));
            }
        }

        for (int xx = 0; xx < w; xx++) {
            for (int yy = 0; yy < h; yy++) {
                int pixel = image.getRGB(xx, yy);
                int red = (pixel >> 16) & 0xff;
                int green = (pixel >> 8) & 0xff;
                int blue = (pixel) & 0xff;

                if (red == 255 & blue == 0 & green == 0)
                    handler.addObject(new Block(xx * 57, yy * 57, ID.Block, ss));

                if (blue == 255 && green == 0 & red == 0)
                    handler.addObject(new Player(xx * 57, yy * 57, ID.Player, handler, this, ss));

                if (green == 255 && blue == 0 && red == 0) {
                    handler.addObject(new Enemy(xx * 57, yy * 57, ID.Enemy, handler, this, ss));
                    enemyNum++;
                }

                if (green == 255 && blue == 255 && red == 0)
                    handler.addObject(new Crate(xx * 57, yy * 57, ID.Crate, ss));


            }
        }

    }

    public void run() {

        this.requestFocus();
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        while (isRunning) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                tick();
                delta--;
            }
            render();

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
            }
        }
        stop();
    }

//    public static void main(String[] args){
//        new classes.Game();
//    }

}