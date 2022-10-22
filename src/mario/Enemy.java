/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mario;

import java.awt.*;
import java.util.Map;
import javax.swing.JFrame;

/**
 *
 * @author domin
 */
public class Enemy {
    public int x, y, width, height;
    public String name;
    public boolean canColidate, direction = true;
    public double gravity = 0, gravityAcceleration = 0.1;

    private Map<String, Image> images;

    private int counter = 0, step = 30;
    private String currentImage;
    private boolean dead = false;
    public boolean end = false;

    public Enemy(int x, int y, int width, int height, String name, Map<String, Image> images, boolean canColidate, double gravityAcceleration){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.name = name;
        this.canColidate = canColidate;
        this.gravityAcceleration = gravityAcceleration;

        this.images = images;
        this.currentImage = name + "_right";
    }
    public void update(Entity [] e, Player p){
        if(dead) return;
        boolean[] collision = this.collision(e, p);

        if(!collision[0]) {
            this.gravity += this.gravityAcceleration;
            this.y += (int)gravity;
        }
        else {
            this.gravity = 0;
        }

        if(collision[2] || collision[3]) direction = !direction;

        //if(collision[1]) this.die();

        x += direction ? 1: -1;
        counter++;
    }
    public void paint(JFrame frame, Graphics g){
        if(this.counter % this.step == 0 && !dead){
            this.currentImage = (this.currentImage.equals(name + "_right"))? name + "_left": name + "_right";
        }

        g.drawImage(images.get(this.currentImage), this.x, this.y, this.width, this.height, frame);
    }
    private boolean[] collision(Entity[] e, Player player) {
        //top bot left right
        boolean[] collision = {false, false, false, false};

        for (int i = 0; i < e.length; i++) {
            //if (e[i].x < this.x && this.x - e[i].x - e[i].width > 500) continue;
            //if (e[i].x > this.x && e[i].x - e[i].x > 500) continue;


            if (this.x + this.height > e[i].x && this.x < e[i].x + e[i].width && this.y < e[i].y + e[i].height && this.y + this.height > e[i].y + e[i].height) {
                this.y = e[i].y + e[i].height + 1;
                this.gravity = 0;
                collision[1] = true;

            }
            if (this.x + this.height > e[i].x && this.x < e[i].x + e[i].width && this.y + this.height >= e[i].y && this.y < e[i].y) {
                this.y = e[i].y - this.height;
                collision[0] = true;
            }

            if (this.x + this.height >= e[i].x && this.x < e[i].x && this.y + this.height > e[i].y && this.y < e[i].y + e[i].height) {
                collision[2] = true;
                System.out.println("left");
            }
            if (this.x <= e[i].x + e[i].width && this.x + this.height > e[i].x && this.y + this.height > e[i].y && this.y < e[i].y + e[i].height) {
                collision[3] = true;
                System.out.println("right" + " " + e[i].name);
            }


        }

        if (this.x + this.height > player.x && this.x < player.x + player.height && this.y < player.y + player.height && this.y + this.height > player.y + player.height) {
            this.die();
            player.jump();
            Mario.addPoints(30);
        }
        else if (this.x + this.height >= player.x && this.x < player.x && this.y + this.height > player.y && this.y < player.y + player.height) {
            player.die();
        }
        else if (this.x <= player.x + player.height && this.x + this.height > player.x && this.y + this.height > player.y && this.y < player.y + player.height) {
            player.die();
        }

        return collision;
    }
    public void die(){
        Thread tt = new Thread(() -> {
            dead = true;
            this.currentImage = name + "_dead";
            this.height = 25;
            this.y += 25;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            end = true;
            //System.out.println("dead");
        });

        tt.start();
    }
}
