package gameredeneural;

import static gameredeneural.GameRedeNeural.GameScreen.GROUNDY;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;
import java.util.Random;
import java.util.Arrays;

public class Obstacule extends GameObject {
    
    private GameRedeNeural gameRedeNeural;
    private float sppedX ;
    private List<BufferedImage> obstacules;
    private Random randomObtacule;
    
    public static final int SPINE = 0;
    public static final int SPINE_SMALL = 1;
    public static final int FLYING = 2;
    public int obstacule;
    
    public int idobject;
    
    public double[][] target = {{1,0}};
    
    public Obstacule(int x, int y,int w,int h, GameRedeNeural game,int obstacule,Indentification id,int idobject) {
        super(x, y,w,h,id);
        this.velX = 6;
        this.velY = 3;
        this.gameRedeNeural = game;
        this.obstacule = obstacule;
        
        this.obstacules = new ArrayList<BufferedImage>();
        this.obstacules.add(ResorceImage.getImage("data/cactus1.png"));
        this.obstacules.add(ResorceImage.getImage("data/cactus2.png"));
        
        this.idobject = idobject;
        
        if(this.obstacules.size() - 1 > obstacule){
            new Exception("Object Not Exist");
        }
    }

    @Override
    public void update() {
       this.sppedX += 0.001f;
       this.x -= this.velX;
       this.y += this.velY;
       
       if(this.y >= GROUNDY - this.obstacules.get(this.obstacule).getHeight()){
          this.y = (int) (GROUNDY -  this.obstacules.get(this.obstacule).getHeight());
       }
       
    }

    @Override
    public void render(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        
        Rectangle r = this.getBouds();
        
        g2.drawImage(this.obstacules.get(this.obstacule),r.x ,r.y, null);
        g2.setColor(Color.BLUE);
        g2.drawRect(r.x,r.y, r.width,r.height);
    }

    @Override
    public Rectangle getBouds() {
        return new Rectangle(x,y,this.obstacules.get(this.obstacule).getWidth(),this.obstacules.get(this.obstacule).getHeight());
    }

    @Override
    public void keyEvent(KeyEvent event) {
        
    }

    @Override
    public void KeyRelease(KeyEvent event) {
        
    }

    @Override
    public void remove(int x, int y,int w,int h, GameRedeNeural game, int nextInt) {
        this.x = x;
    }
     
}
