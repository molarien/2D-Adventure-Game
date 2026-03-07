package main;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

    final int originalTileSize = 16;
    final int scale = 3;
    final int tileSize = originalTileSize * scale;  // 48x48 pixel
    final int maxScreenCol = 16;
    final int maxScreenRow = 12;
    final int screenWidth = tileSize * maxScreenCol;  // 768 pixel
    final int screenHeight = tileSize * maxScreenRow;  // 576 pixel

    int FPS = 60;


    KeyHandler keyHandler = new KeyHandler();
    Thread gameThread;


    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 4;


    public GamePanel(){

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);  // bu panel klavyeden input alabilsin


    }

    public  void startGameThread(){

        gameThread = new Thread(this);
        gameThread.start();

    }



    @Override
    public void run() {

        double drawInterval = (double) 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        long drawCount = 0;

        while(gameThread != null){

            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if(delta >= 1){

                update();
                repaint();
                delta--;
                drawCount++;

            }

            if(timer >= 1000000000){
                System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            }


        }

    }


    public void update(){

        if (keyHandler.upPressed){
            playerY -= playerSpeed;

        }
        else if (keyHandler.downPressed) {
            playerY += playerSpeed;

        }

        else if(keyHandler.rightPressed){
            playerX += playerSpeed;

        }
        else if (keyHandler.leftPressed){
            playerX -= playerSpeed;

        }


    }





    public void paintComponent(Graphics g){

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(Color.WHITE);
        g2.fillRect(playerX,playerY,tileSize,tileSize);
        g2.dispose();   // dispose(), belleği ve işlemciyi yormamak için "grafik kalemini" çöpe atmak yerine yerine koymaktır.





    }



}
