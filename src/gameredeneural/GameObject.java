/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameredeneural;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

/**
 *
 * @author py-md
 */
public abstract class GameObject {
    protected int x = 0 , y = 0;
    protected int velX = 0 , velY = 0;
    protected int width , height;
    protected Indentification id;
    
    public GameObject(int x,int y,int w,int h,Indentification id){
       this.x = x;
       this.y = y;
       
       this.width = w;
       this.height = h;

       this.id = id;
    }
    
    public abstract void update();
    public abstract void render(Graphics g);
    public abstract void keyEvent(KeyEvent event);
    public abstract void KeyRelease(KeyEvent event);
    
    
    public abstract Rectangle getBouds();
    
    public void setX(int x){ this.x = x; };
    public void setY(int y){ this.y = y; };
    
    public void setVelX(int velx){ this.velX = velx;}
    public void setVelY(int vely){ this.velY = vely;}
    
    public void setWidth(int w){ this.width = w; }
    public void setHeight(int h){ this.height = h; }
    
    public int getWidth(){ return this.width;}
    public int getHeight(){ return this.height;}

    public abstract void remove(int i, int i0, int i1, int i2, GameRedeNeural game, int nextInt);
}
