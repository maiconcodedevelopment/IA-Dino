/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameredeneural;

import static gameredeneural.GameRedeNeural.GameScreen.GROUNDY;
import gameredeneural.GameObject;
import gameredeneural.GameRedeNeural.GameScreen;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

/**
 *
 * @author maico
 */
public class ObstaculeHith extends GameObject {
    
    public int live ;
    private HandlerListObjects handlerListObjects;
    private GameScreen gameRedeNeural;
    
    private boolean collisionActive;

    public ObstaculeHith(int x, int y, int w, int h, Indentification id,int idObject,GameScreen gameRedeNeural) {
        super(x, y, w, h, id,idObject);
        collisionActive = true;
        this.velX = 6;
        this.velY = 3;
        this.live = w;
        this.gameRedeNeural = gameRedeNeural;
    }

    @Override
    public void update() {
       
        if(this.live <= 0){
           this.gameRedeNeural.removeObjectHith(this);
        }
        
        this.x -= this.velX;
        this.y += this.velY;
        
        if (this.y >= GROUNDY - this.getBouds().height) {
            this.y = (int) GROUNDY - this.getBouds().height;
        }
        
    }

    @Override
    public void render(Graphics g) {
        
        Graphics2D g2 = (Graphics2D) g;
        
        g2.setColor(Color.RED);
        g2.fillRect(this.x, this.y - 40, this.live, 15);
        g.setColor(Color.BLACK);
        g.drawRect(this.x, this.y - 40, this.width, 15);
        
        
        g.setColor(Color.green);
        g.drawRect(this.x, this.y, this.width, this.height);
    }
    
    public void incrementLive(){
       this.live -= this.width / 4;
    }
    
    boolean getCollisionActive(){
        return collisionActive;
    }

    @Override
    public void keyEvent(KeyEvent event) {
    }

    @Override
    public void KeyRelease(KeyEvent event) {
    }
    

    @Override
    public Rectangle getBouds() {
        return new Rectangle(this.x,this.y,this.width,this.height);
    }

    @Override
    public void remove(int x, int i0, int i1, int i2, GameRedeNeural game, int nextInt) { this.x = x;    }

}
