/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mario;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import static mario.Mario.assetsPath;

/**
 *
 * @author domin
 */
public class Entity {
    public int x, y;
    private Image image;
    public int width, height;
    public String name;
    public boolean canColidate;

    public boolean hit = false, deleted = false;
    private int hitCount = 1;

    public boolean moveAnimation = false;
    public int moveSpeed = 1, to;
    
    public Entity(int x, int y, int width, int height, String name, boolean canColidate){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.name = name;
        this.canColidate = canColidate;

        
        try{
            this.image = ImageIO.read(new File(assetsPath + (name + ".png")));
        }
        catch(Exception e){
            System.out.println(assetsPath + (name + ".png") + ", " + this.name);
            e.printStackTrace();
        }
    }
    public void move(int x, int y){
        this.x = x;
        this.y = y;
    }
    public void paint(JFrame frame, Graphics g){
        if(this.hit){
            if(this.hitCount == 11){
                this.hit = false;
                this.hitCount = 0;
            }
            else if(this.hitCount >= 6) {
                this.y += 2;
            }
            else this.y -= 2;

            this.hitCount++;
        }

        if(this.moveAnimation){
            if(this.y == this.to){
                this.deleted = true;
                Mario.addPoints(10);
            }
            this.y -= this.moveSpeed;
        }
        g.drawImage(this.image, this.x, this.y, this.width, this.height, frame);
    }
    public void mutate(String name){
        try{
            this.image = ImageIO.read(new File(assetsPath + (name + ".png")));
        }
        catch(Exception e){
            e.printStackTrace();
        }

        this.name = name;
    }
    public void hitAnimation(){
        this.hit = true;
    }
    public void moveAnimation(int to){
        if(this.moveAnimation) return;
        this.moveAnimation = true;
        this.to = to;
    }
}
