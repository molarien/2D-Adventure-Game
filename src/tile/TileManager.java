package tile;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class TileManager {

    GamePanel gamePanel;
    Tile[] tile;
    int mapTileNum[][];



    public TileManager(GamePanel gamePanel){

        this.gamePanel = gamePanel;
        tile = new Tile[10];
        mapTileNum = new int[gamePanel.maxScreenCol][gamePanel.maxScreenRow];

        getTileImage();
        loadMap("/maps/map01.txt", "res/maps/map01.txt");

    }


    public void getTileImage(){

        try{
            tile[0] = new Tile();
            tile[0].image = loadTile("/tiles/grass.png", "res/tiles/grass.png");

            tile[1] = new Tile();
            tile[1].image = loadTile("/tiles/wall.png", "res/tiles/wall.png");

            tile[2] = new Tile();
            tile[2].image = loadTile("/tiles/water.png", "res/tiles/water.png");



        }catch (IOException e){
            throw new RuntimeException("Tile gorselleri yuklenemedi.", e);
        }





    }

    private BufferedImage loadTile(String classpathPath, String filePath) throws IOException {
        try (InputStream stream = getClass().getResourceAsStream(classpathPath)) {
            if (stream != null) {
                return ImageIO.read(stream);
            }
        }
        return ImageIO.read(new File(filePath));
    }

    public void loadMap(String classPath, String filePath){

        try (BufferedReader bufferedReader = openMapReader(classPath, filePath)) {

            int col = 0;
            int row = 0;

            while(col < gamePanel.maxScreenCol && row < gamePanel.maxScreenRow){

                String line = bufferedReader.readLine();
                if (line == null) {
                    throw new IOException("Map satir sayisi beklenenden az.");
                }


                while(col < gamePanel.maxScreenCol){
                    String numbers[] = line.trim().split("\\s+");

                    int num = Integer.parseInt(numbers[col]);

                    mapTileNum[col][row] = num;
                    col++;
                }
                if(col == gamePanel.maxScreenCol){
                    col = 0;
                    row++;
                }
            }
        }
        catch (IOException e){
            throw new RuntimeException("Map dosyasi yuklenemedi.", e);
        }

    }

    private BufferedReader openMapReader(String classpathPath, String filePath) throws IOException {
        InputStream stream = getClass().getResourceAsStream(classpathPath);
        if (stream != null) {
            return new BufferedReader(new InputStreamReader(stream));
        }
        return new BufferedReader(new FileReader(filePath));
    }



    public void draw(Graphics2D g2){


       int col = 0;
       int row = 0;
       int x = 0;
       int y = 0;

       while(col < gamePanel.maxScreenCol && row < gamePanel.maxScreenRow){


           int tileNum = mapTileNum[col][row];


           g2.drawImage(tile[tileNum].image, x,y,gamePanel.tileSize, gamePanel.tileSize,  null);
            col++;
            x += gamePanel.tileSize;

            if(col == gamePanel.maxScreenCol){
                col = 0;
                x = 0;
                row++;
                y += gamePanel.tileSize;
            }


       }


    }




}
