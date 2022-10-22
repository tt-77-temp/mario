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
public class Movable extends Entity {
    public double gravity = 0, gravityAcceleration = 0.1;
    
    public Movable(int x, int y, int width, int height, String name, Map<String, Image> images, boolean canColidate, double gravityAcceleration){
        super(x, y, width, height, name, canColidate);
        this.gravityAcceleration = gravityAcceleration;
    }
    @Override
    public void paint(JFrame frame, Graphics g){
        //
    }
}
