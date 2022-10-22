/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mario;

import java.awt.Graphics;
import java.awt.Image;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JFrame;

/**
 *
 * @author domin
 */
public class Player {
    public int x, y, height, jumpforce, moveVector = 0;
    public Map<String, Image> images;
    public boolean jumped = false, direction = true;
    public double gravity = 0, gravityAcceleration = 0.7;
    private int globalPos = 0;
    
    private Image currentImage;
    public boolean dead = false, win = false;
    public boolean end = false;

    //death animation
    private int count = 0, countLimit = 20, speed = 5;

    //win animation
    private int castleDistance, heightDistance;

    
    Player(int x, int y, int height, Map<String, Image> images, int jumpforce){
        this.x = x;
        this.y = y;
        this.height = height;
        this.images = images;
        this.jumpforce = jumpforce;
        
        this.currentImage = this.images.get("mario_right");
    }
    public void jump(){
        this.y -= 2;
        this.gravity = -this.jumpforce;
        this.jumped = true;
    }
    public void draw(JFrame frame, Graphics g){
        if(this.dead){
            this.y -= speed;
            count++;
            if(count == countLimit){
                speed = -speed;
            }
            if(this.y > Mario.height){
                this.end = true;
                Mario.endGame();
            }
        }

        if(this.win){

            if(this.y >= this.heightDistance) {
                this.y = this.heightDistance;
                if(this.x > this.castleDistance){
                    Mario.endGame();
                }
                else{
                    this.x += Mario.speed;
                }
            }
            else{
                this.y += Mario.speed;
            }
        }
        g.drawImage(this.currentImage, this.x, this.y, this.height, this.height, frame);
    }
    public int move(int moveVec, Entity[] ground){
        if(this.dead) {
            changeImage();
            return 0;
        }
        if(this.win){
            changeImage();
            this.jumped = false;
            this.gravity = 0;
            return 0;
        };

        if(moveVec < 0) this.direction = true;
        else if(moveVec > 0) this.direction = false;




        boolean[] collision = this.collision(ground);
        //if(!collision.equals("")) moveVec = 0;



        if(!collision[0]) {
            this.gravity += this.gravityAcceleration;
            this.y += (int)gravity;
            this.jumped = true;
        }
        else {
            this.gravity = 0;
            this.jumped = false;
        }

        if(collision[2]){
            moveVec += Mario.speed;
        }
        else if(collision[3]){
            moveVec -= Mario.speed;
        }

        this.changeImage();

        globalPos += moveVec;
        if(globalPos > 0){
            globalPos = 0;
            return 0;
        }

        this.moveVector = moveVec;
        return moveVec;
    }
    private void changeImage(){
        String name = "mario";
        if(this.dead){
            name += "_dead";
            this.currentImage = images.get(name);
            return;
        }
        if(this.win){
            name += "_walk_right";
            this.currentImage = images.get(name);
            return;
        }

        if(this.jumped == true) name += "_jump";
        else if(this.moveVector != 0) name += "_walk";
        
        if(this.direction == true) name += "_right";
        else if(this.direction == false) name += "_left";
        
        this.currentImage = images.get(name);
    }
    private boolean[] collision(Entity[] e) {
        //top bot left right
        boolean[] collision = {false, false, false, false};

        if(this.y > Mario.height) Mario.endGame();

        for (int i = 0; i < e.length; i++) {
            if (e[i].x < this.x && this.x - e[i].x - e[i].width > 500) continue;
            if (e[i].x > this.x && e[i].x - e[i].x > 500) continue;

            if (this.x + this.height > e[i].x && this.x < e[i].x + e[i].width && this.y < e[i].y + e[i].height && this.y + this.height > e[i].y + e[i].height) {
                this.y = e[i].y + e[i].height + 1;
                this.gravity = 0;
                collision[1] = true;
                if(!e[i].hit && !e[i].name.equals("clear")) e[i].hitAnimation();
                if(e[i].name == "question"){
                    Mario.coins.add(new Entity(e[i].x + 10, e[i].y, 30, 30, "coin", false));
                    Mario.coins.get(Mario.coins.toArray().length - 1).moveAnimation(e[i].y - 30);
                    e[i].mutate("clear");
                }

            }

            if (this.x + this.height >= e[i].x && this.x < e[i].x && this.y + this.height > e[i].y && this.y < e[i].y + e[i].height) {
                collision[2] = true;

                if(e[i].name == "flag"){
                    this.castleDistance = e[i+1].x;
                    this.heightDistance = Mario.height - 100 - this.height;
                    this.win = true;
                }
            }
            if (this.x <= e[i].x + e[i].width && this.x + this.height > e[i].x && this.y + this.height > e[i].y && this.y < e[i].y + e[i].height) {
               collision[3] = true;
            }

            if (this.x + this.height > e[i].x && this.x < e[i].x + e[i].width && this.y + this.height >= e[i].y && this.y < e[i].y) {
                this.y = e[i].y - this.height;
                collision[0] = true;

                if(e[i].name == "flag" && !this.win){
                    this.castleDistance = e[i+1].x;
                    this.heightDistance = Mario.height - 100 - this.height;
                    this.win = true;
                }

            }

        }
        return collision;
    }
    public void die(){
        System.out.println("Mario dead again");
        dead = true;
    }
}
