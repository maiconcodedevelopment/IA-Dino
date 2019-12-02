/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameredeneural;

import static gameredeneural.GameRedeNeural.GameScreen.GRAVITY;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
/**
 *
 * @author py-md
 */

public class NeuralNetworkObject extends GameObject {
    
    private float sppedX = 1f;
    
    public NeuralNetworkObject(int x, int y, int w, int h, Indentification id) {
        super(x, y, w, h, id);
    }

    @Override
    public void update() {
        this.x += this.sppedX;
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.PINK);
        
        int lineHeight = 30;
        
        for(int i = 1;i < 5;i++){
          neuralOval(g, 10, (i * lineHeight), width, height);
        }
        
        for(int i= 1;i < 5;i++){
          neuralOval(g,200,(i*lineHeight),width,height); 
        }
        
        for(int i=1;i<5;i++){
           for(int a=1;a<5;a++){
              g.drawLine(10 + 25, (i * lineHeight) + (25 / 2), 200, (a * lineHeight) + (25 / 2));
           }
        }
    }
    
    public void neuralOval(Graphics g,int x,int y,int width,int height){
        g.drawOval(x, y, width, height);
        g.drawString("10", x + (5), y + (25 / 2) + 5);
    }

    @Override
    public void keyEvent(KeyEvent event) {
        
    }

    @Override
    public void KeyRelease(KeyEvent event) {
    }

    @Override
    public Rectangle getBouds() {
        return new Rectangle(x, y, width, height);
    }

    @Override
    public void remove(int i, int i0, int i1, int i2, GameRedeNeural game, int nextInt) {
    }
    
}
