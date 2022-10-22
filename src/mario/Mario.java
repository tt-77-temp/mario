/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package mario;

import java.awt.*;
import java.io.IOException;
import java.util.List;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
//import javax.swing.Timer;

/**
 *
 * @author domin
 */
public class Mario extends JPanel {
    
    static JFrame frame;

    //game
    static boolean game_started = false;
    Boolean keyboard[] = {false, false, false, false};
    static int speed = 6;
    static public String assetsPath = "F:\\Mario\\src\\assets\\";
    double gravity = 0, gravityAcceleration = 1;
    
    //images
    int imageHeight = 50;
    int groundCount = 2, groundHeight;
    
    Map<String, int[]> blockBounds = new HashMap<String, int[]>();
    
    //bg(H- hill h-small hill B - big bush, b-small bush, c-small cloud, C-big cloud, a-castle)
    //h4411  - h-what, 44-x * 50 from last 11-y * 50 from window size up - height
    
    Object [][] lvlbg = {{"hill", 500, 100}, {"smallcloud", 200, 480}, {"bigbush", 100, 100}, {"smallhill", 0, 100}, {"smallcloud", 100, 480}, {"smallbush", 300, 100}, {"bigcloud", 100, 480}, {"bigcloud", 200, 480}, {"smallbush", 100, 100}};                       
    Object [][] lvlb = {{"question", 1300, 200}, {"brick", 150, 200}, {"question", 0, 200}, {"brick", 0, 200}, {"question", 0, 400, true}, {"question", 0, 200}, {"brick", 0, 200}, {"green", 150, -100}, {"green", 350, -50}, {"green", 250, 0}, {"green", 400, 0}, {"brick", 900, 200}, {"question", 0, 200}, {"brick", 0, 200}, {"brick", 0, 400}, {"brick", 0, 400}, {"brick", 0, 400}, {"brick", 0, 400}, {"brick", 0, 400}, {"brick", 0, 400}, {"brick", 0, 400}, {"brick", 0, 400}, {"brick", 0, 400}, {"brick", 0, 400}, {"brick", 200, 400}, {"brick", 0, 400}, {"brick", 0, 400}, {"brick", 0, 400}, {"question", 0, 400}, {"brick", 0, 200, true}, {"brick", 500, 200}, {"question", 0, 200}, {"question", 300, 200}, {"question", 150, 200}, {"question", 0, 400, true}, {"question", 150, 200}, {"brick", 350, 200}, {"brick", 100, 400}, {"brick", 0, 400}, {"brick", 0, 400}, {"brick", 250, 400}, {"question", 0, 400}, {"brick", 0, 200, true}, {"question", 0, 400}, {"brick", 0, 200, true}, {"brick", 0, 400}, {"stone", 150, 100}, {"stone", 0, 100}, {"stone", 0, 150, true}, {"stone", 0, 100}, {"stone", 0, 150, true}, {"stone", 0, 200, true}, {"stone", 0, 100}, {"stone", 0, 150, true}, {"stone", 0, 200, true}, {"stone", 0, 250, true}, {"stone", 100, 100}, {"stone", 0, 150, true}, {"stone", 0, 200, true}, {"stone", 0, 250, true}, {"stone", 0, 100}, {"stone", 0, 150, true}, {"stone", 0, 200, true}, {"stone", 0, 100}, {"stone", 0, 150, true}, {"stone", 0, 100}, {"stone", 200, 100}, {"stone", 0, 100}, {"stone", 0, 150, false}, {"stone", 0, 100}, {"stone", 0, 150, false}, {"stone", 0, 200, false}, {"stone", 0, 100}, {"stone", 0, 150, false}, {"stone", 0, 200, true}, {"stone", 0, 250, false}, {"stone", 0, 100}, {"stone", 0, 150, false}, {"stone", 0, 200, true}, {"stone", 0, 250, false}, {"stone", 100, 100}, {"stone", 0, 150, false}, {"stone", 0, 200, false}, {"stone", 0, 250, false}, {"stone", 0, 100}, {"stone", 0, 150, false}, {"stone", 0, 200, false}, {"stone", 0, 100}, {"stone", 0, 150, false}, {"stone", 0, 100}, {"green", 250, -100}, {"brick", 300, 200}, {"brick", 0, 200}, {"question", 0, 200}, {"brick", 0, 200}, {"green", 550, -100}, {"stone", 0, 100}, {"stone", 0, 100}, {"stone", 0, 150, false}, {"stone", 0, 100}, {"stone", 0, 150, false}, {"stone", 0, 200, false}, {"stone", 0, 100}, {"stone", 0, 150, false}, {"stone", 0, 200, false}, {"stone", 0, 250, false}, {"stone", 0, 100}, {"stone", 0, 150, false}, {"stone", 0, 200, false}, {"stone", 0, 250, false}, {"stone", 0, 300, false}, {"stone", 0, 100}, {"stone", 0, 150, false}, {"stone", 0, 200, false}, {"stone", 0, 250, false}, {"stone", 0, 300, false}, {"stone", 0, 350, false}, {"stone", 0, 100}, {"stone", 0, 150, false}, {"stone", 0, 200, false}, {"stone", 0, 250, false}, {"stone", 0, 300, false}, {"stone", 0, 350, false}, {"stone", 0, 400, false}, {"stone", 0, 100}, {"stone", 0, 150, false}, {"stone", 0, 200, false}, {"stone", 0, 250, false}, {"stone", 0, 300, false}, {"stone", 0, 350, false}, {"stone", 0, 400, false}, {"flag", 300, 100}, {"castle", 200, 100}};
    Object [][] lvlg = {
            {"floor", 0, 0, false}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0},  {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0},  {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0},  {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0},  {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0},  {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 100, 0}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 150, 0}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0},{"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0}, {"floor", 0, 0},{"floor", 0, 0},{"floor", 100, 0},{"floor", 0, 0},{"floor", 0, 0},{"floor", 0, 0},{"floor", 0, 0},{"floor", 0, 0},{"floor", 0, 0},{"floor", 0, 0},{"floor", 0, 0},{"floor", 0, 0},{"floor", 0, 0},{"floor", 0, 0},{"floor", 0, 0},{"floor", 0, 0},{"floor", 0, 0},{"floor", 0, 0},{"floor", 0, 0},{"floor", 0, 0},{"floor", 0, 0},{"floor", 0, 0},{"floor", 0, 0},{"floor", 0, 0},{"floor", 0, 0},{"floor", 0, 0},{"floor", 0, 0},{"floor", 0, 0},{"floor", 0, 0},{"floor", 0, 0},{"floor", 0, 0},{"floor", 0, 0},{"floor", 0, 0},{"floor", 0, 0},{"floor", 0, 0},{"floor", 0, 0},{"floor", 0, 0},{"floor", 0, 0},{"floor", 0, 0},{"floor", 0, 0},{"floor", 0, 0},{"floor", 0, 0},{"floor", 0, 0},{"floor", 0, 0},{"floor", 0, 0},{"floor", 0, 0},{"floor", 0, 0},{"floor", 0, 0},{"floor", 0, 0},{"floor", 0, 0},{"floor", 0, 0},{"floor", 0, 0},{"floor", 0, 0},{"floor", 0, 0},{"floor", 0, 0},{"floor", 0, 0},{"floor", 0, 0},{"floor", 0, 0},{"floor", 0, 0},{"floor", 0, 0},{"floor", 0, 0},{"floor", 0, 0},{"floor", 0, 0},{"floor", 0, 0},
            {"floor", 0, 50, false}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 100, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 150, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 100, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}, {"floor", 0, 50}

    };

    Entity[] lvlbgp, lvlblocks, lvlground, all;
    static List<Entity> coins = new ArrayList<Entity>();
    //static List<Enemy> enemies = new ArrayList<Enemy>();
    Enemy [] enemies;

    Player player;

    //Font font = new Font(Font.MONOSPACED, Font.BOLD, 30);
    File font_file;
    Font font;
    Image coin;

    static int points = 0, mone = 0;
    static int height;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        frame = new JFrame();
        frame.setSize(1000, 562);
        frame.getContentPane().add(new Mario());
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setUndecorated(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    public void paint(Graphics g){
        if(!game_started){
            height = getHeight();
            setup();
        }
        int c = 0;
        g.setColor(new Color(148, 148, 255));
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(Color.white);

        //text


        
        //controls
        int moveVec = 0;

            if(keyboard[0] == true) if(!player.jumped) player.jump();
            if(keyboard[2] == true) moveVec = speed;
            if(keyboard[3] == true) moveVec = -speed;


            moveVec = player.move(moveVec, all);



        for(int i = 0; i<lvlbgp.length; i++){

            lvlbgp[i].x += moveVec;
            if(lvlbgp[i].x < player.x && player.x - lvlbgp[i].x - lvlbgp[i].width > 500 && !player.win) continue;
            if(lvlbgp[i].x > player.x && lvlbgp[i].x - player.x > 500) continue;
           
            lvlbgp[i].paint(frame, g);
            c++;
            
        }
        for(int i = 0; i<lvlblocks.length; i++){
            lvlblocks[i].x += moveVec;
            if(lvlblocks[i].x < player.x && player.x - lvlblocks[i].x - lvlblocks[i].width > 500 && !player.win) continue;
            if(lvlblocks[i].x > player.x && lvlblocks[i].x - player.x > 500) continue;
            lvlblocks[i].paint(frame, g);
            c++;
        }
        for(int i = 0; i<lvlground.length; i++){
            lvlground[i].x += moveVec;
            if(lvlground[i].x < player.x && player.x - lvlground[i].x - lvlground[i].width > 500 && !player.win) continue;
            if(lvlground[i].x > player.x &&  lvlground[i].x - player.x > 500) continue;
            lvlground[i].paint(frame, g);
            c++;
        }

        for(int i = 0; i<coins.toArray().length; i++){
            if(coins.get(i).deleted) continue;
            coins.get(i).x += moveVec;
            if(coins.get(i).x < player.x && player.x - coins.get(i).x - coins.get(i).width > 500 && !player.win) continue;
            if(coins.get(i).x > player.x &&  coins.get(i).x - player.x > 500) continue;
            coins.get(i).paint(frame, g);
            c++;
        }
        for(int i = 0; i<enemies.length; i++){
            if(enemies[i].end){
                enemies[i] = null;
                Enemy[] anotherArray = new Enemy[enemies.length - 1];
                System.arraycopy(enemies, 0, anotherArray, 0, i);
                System.arraycopy(enemies, i + 1, anotherArray, i, enemies.length - i - 1);

                enemies = anotherArray;
                continue;
            }
            enemies[i].x += moveVec;

            if(enemies[i].x < player.x && player.x - enemies[i].x - enemies[i].width > 500 && !player.win) continue;
            if(enemies[i].x > player.x &&  enemies[i].x - player.x > 500) continue;
            enemies[i].update(all, player);
            enemies[i].paint(frame, g);
            c++;
        }

        //System.out.println(c);


        if(!player.end) player.draw(frame, g);
        
        //System.out.println("Painted: " + c++);
        /*

        //gravity
        if(player.y + player.height < groundHeight){
            gravity += gravityAcceleration;
            player.y+=gravity;
        }
        else{
            gravity = 0;
            player.y = groundHeight - player.height;
            player.jumped = false;
        } 
        player.draw(frame, g);
        
        for(int i = 0; i<level.length; i++){
            level[i][1] = (int)level[i][1] + moveVec;
            blocks.get(level[i][0].toString()).draw(frame, g, (int)level[i][1], (int)level[i][2]);
        }
       
        */

        g.setFont(font);
        g.drawString("Mario", 20, 30);
        g.drawString(score(points), 20, 60);

        g.drawImage(coin, 200, 20, 32, 32, frame);
        g.drawString("X " + mone, 250, 50);

        if(!game_started) {
            try {
                int w = 528, h = 264;
                Image i = ImageIO.read(new File(assetsPath + "logo.png"));
                g.drawImage(i, getWidth() / 2 - (w / 2), getHeight() / 2 - h + 70, w, h, frame);

                /*g.setFont(font);
                g.drawString("Mario", 20, 30);
                g.drawString(score(points), 20, 60);

                g.drawImage(coin, 200, 20, 32, 32, frame);
                g.drawString("X " + mone, 250, 50);*/

                g.drawString("Press enter to start", getWidth() / 2 - 130, getHeight() / 2 + 130);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try{Thread.sleep(8);}
        catch(Exception e){}
        
        repaint();
    }
    public String score(int score){
        String s = String.valueOf(score);
        char[] c = s.toCharArray();
        String res = "";

        for(int i = 0; i<5-c.length; i++){
            res = "0"+res;
        }

        res = res + s;
        return res;
    }
    public Entity[] readPattern(Object [][] str){
        Entity[] e = new Entity[str.length * 5];
        int prev = 0;
        for(int j = 0; j<5; j++){
            for(int i = 0; i<str.length; i++){


                int width = blockBounds.get(str[i][0].toString())[0];
                int height = blockBounds.get(str[i][0].toString())[1];

                e[i + str.length * j] = new Entity(prev + (int)str[i][1], getHeight() - (int)str[i][2] - height, width, height, str[i][0].toString(), true);
                prev = (int)str[i][1] + prev + width;
            }
        }

        return e;
    }
    public Entity[] readBlocks(Object [][] arr){
        Entity[] e = new Entity[arr.length];
        int prev = 0;
        for(int i = 0; i<arr.length; i++){

            int width = blockBounds.get(arr[i][0].toString())[0];
            int height = blockBounds.get(arr[i][0].toString())[1];
            
            if(arr[i].length > 3) prev -= width;


            
            e[i] = new Entity(prev + (int)arr[i][1], getHeight() - (int)arr[i][2] - height, width, height, arr[i][0].toString(), false);
            prev = (int)arr[i][1] + prev + width;
        }
        return e;
    }
    public Entity[] readGround(Object [][] arr){
        Entity[] e = new Entity[arr.length];
        int prev = 0;
        for(int i = 0; i<arr.length; i++){

            int width = blockBounds.get(arr[i][0].toString())[0];
            int height = blockBounds.get(arr[i][0].toString())[1];
            
            if(arr[i].length > 3) prev = 0;
            
            e[i] = new Entity(prev + (int)arr[i][1], getHeight() - (int)arr[i][2] - height, width, height, arr[i][0].toString(), false);
            prev = (int)arr[i][1] + prev + width;
        }
        return e;
    }
    public void setup(){
        frame.addKeyListener(new KeyboardInputManager());
        groundHeight = getHeight() - (2 * imageHeight);

        points = 0;
        mone = 0;

        font_file = new File(assetsPath + "Font.otf");

        {
            try {
                font = Font.createFont(Font.TRUETYPE_FONT, font_file);
                font = font.deriveFont(30f);
            } catch (FontFormatException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        

        try{
            //bg setup
            //background = ImageIO.read(new File(assetsPath + "bg1.png"));

            coin = ImageIO.read(new File(assetsPath + "coin.png"));
            
            //player setup
            Map<String, Image> mario_images = new HashMap<String, Image>();
            mario_images.put("mario_right", ImageIO.read(new File(assetsPath + "mario_right.png")));
            mario_images.put("mario_left", ImageIO.read(new File(assetsPath + "mario_left.png")));
            mario_images.put("mario_walk_right", ImageIO.read(new File(assetsPath + "mario_walk_right.png")));
            mario_images.put("mario_walk_left", ImageIO.read(new File(assetsPath + "mario_walk_left.png")));
            mario_images.put("mario_jump_left", ImageIO.read(new File(assetsPath + "mario_jump_left.png")));
            mario_images.put("mario_jump_right", ImageIO.read(new File(assetsPath + "mario_jump_right.png")));
            mario_images.put("mario_dead", ImageIO.read(new File(assetsPath + "mario_dead.png")));
            player = new Player(getWidth()/2, getHeight()/2, 50, mario_images, 20);
            
            
            //map generation
            
            blockBounds.put("brick", new int[]{50, 50});
            blockBounds.put("floor", new int[]{50, 50});
            blockBounds.put("stone", new int[]{50, 50});
            blockBounds.put("question", new int[]{50, 50});
            blockBounds.put("green", new int[]{100, 300});
            blockBounds.put("flag", new int[]{50, 381});
            blockBounds.put("castle", new int[]{250, 250});

            blockBounds.put("hill", new int[]{250, 110});
            blockBounds.put("smallhill",  new int[]{144, 57});
            blockBounds.put("smallbush",  new int[]{96, 32});
            blockBounds.put("bigbush",  new int[]{200, 40});
            blockBounds.put("smallcloud",  new int[]{96, 64});
            blockBounds.put("bigcloud",  new int[]{128, 64});
            
            lvlbgp = readPattern(lvlbg);
            lvlblocks = readBlocks(lvlb);
            lvlground = readGround(lvlg);

            Map<String, Image> mushroom_images = new HashMap<String, Image>();
            mushroom_images.put("mushroom_right", ImageIO.read(new File(assetsPath + "mushroom_right.png")));
            mushroom_images.put("mushroom_left", ImageIO.read(new File(assetsPath + "mushroom_left.png")));
            mushroom_images.put("mushroom_dead", ImageIO.read(new File(assetsPath + "mushroom_dead.png")));
            enemies = new Enemy[]{
                //new Enemy(200, getHeight() - 100 - 50, 50, 50, "mushroom", mushroom_images, true, 1),
                new Enemy(1500, getHeight() - 100 - 50, 50, 50, "mushroom", mushroom_images, true, 1),
                new Enemy(2200, getHeight() - 100 - 50, 50, 50, "mushroom", mushroom_images, true, 1),
                new Enemy(2900, getHeight() - 100 - 50, 50, 50, "mushroom", mushroom_images, true, 1),
                new Enemy(3000, getHeight() - 100 - 50, 50, 50, "mushroom", mushroom_images, true, 1),
                new Enemy(4200, getHeight() - 250 - 50, 50, 50, "mushroom", mushroom_images, true, 1),
                new Enemy(4500, getHeight() - 450 - 50, 50, 50, "mushroom", mushroom_images, true, 1),
                new Enemy(5400, getHeight() - 100 - 50, 50, 50, "mushroom", mushroom_images, true, 1),
                new Enemy(5500, getHeight() - 100 - 50, 50, 50, "mushroom", mushroom_images, true, 1),
                new Enemy(6700, getHeight() - 100 - 50, 50, 50, "mushroom", mushroom_images, true, 1),
                new Enemy(6800, getHeight() - 100 - 50, 50, 50, "mushroom", mushroom_images, true, 1),
                new Enemy(6900, getHeight() - 100 - 50, 50, 50, "mushroom", mushroom_images, true, 1),
                new Enemy(7000, getHeight() - 100 - 50, 50, 50, "mushroom", mushroom_images, true, 1),
                new Enemy(9800, getHeight() - 100 - 50, 50, 50, "mushroom", mushroom_images, true, 1),
                new Enemy(9900, getHeight() - 100 - 50, 50, 50, "mushroom", mushroom_images, true, 1),

            };


            all = Stream.concat(Arrays.stream(lvlblocks), Arrays.stream(lvlground)).toArray(Entity[]::new);
            /*
            blocks.add(new Block(900, 200, 50, 50, "question.png", false));
            
            blocks.add(new Block(1000, 200, 50, 50, "brick.png", false));
            blocks.add(new Block(1050, 200, 50, 50, "question.png", false));
            blocks.add(new Block(1100, 200, 50, 50, "brick.png", false));
            blocks.add(new Block(1150, 200, 50, 50, "question.png", false));
            blocks.add(new Block(1200, 200, 50, 50, "brick.png", false));
            blocks.add(new Block(1100, 50, 50, 50, "question.png", false));
            
            blocks.add(new Block(1350, 275, 75, 150, "green.png", false));
            blocks.add(new Block(1750, 250, 75, 150, "green.png", false));
            blocks.add(new Block(1950, 200, 75, 150, "green.png", false));
            blocks.add(new Block(2250, 200, 75, 150, "green.png", false));
            
            blocks.add(new Block(2750, 200, 50, 50, "brick.png", false));
            blocks.add(new Block(2800, 200, 50, 50, "question.png", false));
            blocks.add(new Block(2850, 200, 50, 50, "brick.png", false));
            
            blocks.add(new Block(2870, 50, 50, 50, "brick.png", false));
            blocks.add(new Block(2920, 50, 50, 50, "brick.png", false));
            blocks.add(new Block(2970, 50, 50, 50, "brick.png", false));
            blocks.add(new Block(3020, 50, 50, 50, "brick.png", false));
            blocks.add(new Block(3070, 50, 50, 50, "brick.png", false));
            blocks.add(new Block(3120, 50, 50, 50, "brick.png", false));
            blocks.add(new Block(3170, 50, 50, 50, "brick.png", false));
            blocks.add(new Block(3320, 50, 50, 50, "brick.png", false));
            blocks.add(new Block(3370, 50, 50, 50, "brick.png", false));
            blocks.add(new Block(3420, 50, 50, 50, "brick.png", false));
            blocks.add(new Block(3470, 50, 50, 50, "question.png", false));
            blocks.add(new Block(3470, 200, 50, 50, "brick.png", false));
            
            blocks.add(new Block(3670, 200, 50, 50, "brick.png", false));
            blocks.add(new Block(3720, 200, 50, 50, "brick.png", false));
            
            
            //floor setup
            for(int i = 0; i<2; i++){
                int y = getHeight() - (imageHeight * (1+i));
                for(int j = 0; j<5000/imageHeight;j++){
                    if(50*j == 2500 || 50*j == 2550) continue;
                    blocks.add(new Block(50 * j, y, 50, 50, "floor.png", (i == 0)));
                }
            }
            
            mod = blocks.toArray(new Block[blocks.size()]);*/
        }
        catch(Exception e) {
            System.out.println("sie wysypaly zdjencia" + e);
        }
    }
    public static void addPoints(int add){
        points += add;
        if(add == 10) mone++;
    }
    public static void endGame(){
        Thread tt = new Thread(() -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            game_started = false;
            //System.out.println("Game ended");
        });

        tt.start();

    }
    class KeyboardInputManager implements KeyListener{
        public void keyTyped(KeyEvent e) {

        }
        @Override
        public void keyPressed(KeyEvent e) {
            if(e.getKeyCode() == KeyEvent.VK_ENTER && !game_started){
                game_started = true;

            }
            if(!game_started) return;
            if(e.getKeyCode() == KeyEvent.VK_S) keyboard[1] = true;
            else if(e.getKeyCode() == KeyEvent.VK_W) keyboard[0] = true;
            if(e.getKeyCode() == KeyEvent.VK_D) keyboard[3] = true;
            else if(e.getKeyCode() == KeyEvent.VK_A) keyboard[2] = true;
        }
        @Override
        public void keyReleased(KeyEvent e) {
            if(e.getKeyCode() == KeyEvent.VK_S) keyboard[1] = false;
            else if(e.getKeyCode() == KeyEvent.VK_W) keyboard[0] = false;
            if(e.getKeyCode() == KeyEvent.VK_D) keyboard[3] = false;
            else if(e.getKeyCode() == KeyEvent.VK_A) keyboard[2] = false;
       }   
    }   
}
