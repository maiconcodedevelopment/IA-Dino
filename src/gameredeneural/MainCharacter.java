/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameredeneural;

import com.sun.istack.internal.NotNull;
import gameredeneural.DeepQLearning.DeepQLearning;
import gameredeneural.GameRedeNeural.GameScreen;
import static gameredeneural.GameRedeNeural.GameScreen.GRAVITY;
import static gameredeneural.GameRedeNeural.GameScreen.GROUNDY;
import static gameredeneural.GameRedeNeural.GameScreen.spaceObstacule;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Animation;
import javax.imageio.ImageIO;
import javax.lang.model.type.IntersectionType;

/**
 *
 * @author py-md
 */
public class MainCharacter extends GameObject {
    
    private int ID ;
    
    public DeepQLearning deepQLearning;
    private double lastReward = 0;
    
    private float sppedY = 0;
    private float degress = 0;
    
    private static final int RUN_NORMAL = 0;
    private static final int STATE_UP = 1;
    private static final int STATE_DOWN = 2;
    private  int STATE = RUN_NORMAL;
    private float sppedXCos = 0.5f;
    
    private int xspped = 50;
    private int yspped = 160;
    
    private int stateDistance = 0;
    private int stateDistanceUpdate = 0;
    
    private int distance = 0;
    
    private final int sizeSensor = 100;
    
    private Rectangle rect;
    private HandlerListObjects  handlerListObjects;
    private GameScreen game;
    
    private Obstacule obstacule;
    
    private AnimationFrame normalAnimation;
    private AnimationFrame downAnimation;
    private AnimationFrame jumpAnimation;
    
    private NeuralNetwork neuralNetwork;
    
    private double normalizeVariable = 1.0;
    
    private int score = 0;
    
    // sersor
    int x1;
    int x2;
    
    public MainCharacter(int x,int y,int w,int h,Indentification id,HandlerListObjects listObstacule,GameScreen game,int ID ){
       super(x,y,w,h,id);
       
       this.ID = ID;
       
       this.velX = 3;
       this.velY = 2;
       
       this.handlerListObjects = listObstacule;
       this.game = game;
       
       this.deepQLearning = new DeepQLearning(4, 2, 0.9);
       
       normalAnimation = new AnimationFrame(90);
       normalAnimation.addImage(ResorceImage.getImage("data/main-character1.png"));
       normalAnimation.addImage(ResorceImage.getImage("data/main-character2.png"));
       downAnimation = new AnimationFrame(90);
       downAnimation.addImage(ResorceImage.getImage("data/main-character5.png"));
       downAnimation.addImage(ResorceImage.getImage("data/main-character6.png"));
       jumpAnimation = new AnimationFrame(90);
       jumpAnimation.addImage(ResorceImage.getImage("data/main-character3.png"));
       
    }
    
    @Override
    public void update(){
       //this all these line code for jumping
       //this.xspped += this.velX;
       //this.yspped +=  Math.round(10 * Math.cos(Math.toRadians(this.xspped)));
       this.degress += 0.5f;
     
       lastReward = -0.004;
       
       if(y >= (GROUNDY - this.height)){
            sppedY = 0;
            this.y = (int) (GROUNDY - this.height);
            if(this.STATE == STATE_UP){
                this.setState(RUN_NORMAL);
            }
       }else{
            sppedY += GRAVITY;
            y += sppedY;
       }
       
       this.normalAnimation.update();
       this.downAnimation.update();
       
       distanceToObstacule();
       collision();
       
       int sensord = 30;
       
       int x1 = (this.x + this.width);
       int x2 = (x1 + sensord);
       int y1 = (this.y + (this.height / 2));
      
//       (x1,x2) = (x2,x2) + alpha * (x2 - x1,y2 - y1);
       
       double[] inputData = { (double) this.getDistance() / normalizeVariable,
                              (double) this.getWidthObstacule() / normalizeVariable , 
                              (double) this.getHeightObstacule() / normalizeVariable ,
                              (double) this.getVelocity() / normalizeVariable };
       
        double action = this.deepQLearning.update(lastReward, inputData,score);
//       System.out.println(action);
       
       if(action == 1.0){
           this.jump();
       }else if(action == 0.0){
           this.lower();
       }
       
    }
    
    public Point getPointOnCircle(float degress, float radius){
       int xp = Math.round(GameScreen.widthDimension / 2);
       int yp = Math.round(GameScreen.heightDimension / 2);
       
       double rads = Math.toRadians(degress - 90);
       
       int xPoint = Math.round((float) ( (xp + ( 1 / 1+ Math.exp(-Math.cos(rads))) * radius)));
       int yPoint = Math.round((float) (yp + Math.sin(rads) * radius));
       
       return new Point(xPoint,yPoint);
    }
    
    @Override
    public void render(Graphics g) {
       Rectangle r = this.getBouds();
       this.distanceToObstacule(g);
       this.sensors(g);
       
       switch(this.STATE){
            case RUN_NORMAL:
              g.drawImage(this.normalAnimation.getFrame(), r.x, r.y, null);
              break;
            case STATE_DOWN:
              g.drawImage(this.downAnimation.getFrame(), r.x, r.y, null);
              break;
            case STATE_UP:
              g.drawImage(this.jumpAnimation.getFrame(),r.x,r.y,null);
              break;
       }
       
       int diameter = Math.min(GameScreen.widthDimension, GameScreen.heightDimension);
       
       int xc = (GameScreen.widthDimension - diameter) / 2;
       int yc = (GameScreen.heightDimension - diameter) / 2;
       
       g.setColor(Color.MAGENTA);
       
       g.drawString("Distace to Player : " + String.valueOf(this.getDistance() / normalizeVariable),(GameScreen.widthDimension / 2) - 100,60);
       g.drawString("Width Obstacule : " + String.valueOf(this.getWidthObstacule() / normalizeVariable), (GameScreen.widthDimension / 2) - 100, 80);
       g.drawString("Height Obstacule : " + String.valueOf(this.getHeightObstacule() / normalizeVariable), (GameScreen.widthDimension / 2) - 100, 100);
       g.drawString("Velocity Obstacule :" + String.valueOf(this.getVelocity() / normalizeVariable),  (GameScreen.widthDimension / 2) - 100, 120);
       
       float innerDimension = 20;
       
       //g.drawOval(xc, yc, diameter, diameter);

       Point p = getPointOnCircle(degress, (diameter / 2f) - (innerDimension / 2));
       //g.drawOval(xc + p.x  - (int) (innerDimension / 2), yc + p.y - (int) (innerDimension / 2) ,(int) innerDimension,(int) innerDimension);

       g.drawRect(r.x, r.y, r.width,r.height);
       g.drawLine(100, 300, r.x, r.y);
       
       //sensor
       g.drawLine(x + width,y + (height / 2),(x + width) + sizeSensor,y + (height / 2));
       g.drawLine(x + width,y + (height / 2),(x + width) + sizeSensor,((y + (height / 2)) - 50) % 360);
       g.drawLine(x + width,y + (height / 2),(x + width) + sizeSensor,((y + (height / 2)) + 50) % 360);
       
       g.drawRect(((x + width) + sizeSensor) - 5, (y) - 5, (width) + 5,(height) + 5);
       g.drawRect(((x + width) + sizeSensor) - 5, (((y) - 5) - 50) % 360 , (width) + 5,(((height) + 5)) );
    }
    
    public void jump(){
        if( y >= GROUNDY - this.height){
            sppedY = -10;
            this.y += sppedY;
            this.setState(STATE_UP);
        }
    }
    
    public void lower(){
        if(this.STATE == STATE_UP){
            sppedY = 9;
            this.y += sppedY;
        }
        
        if(y >= GROUNDY - this.height){
           this.setState(STATE_DOWN);
       }
    }
    
    public void setState(int state){
        this.STATE = state;
    }

    @Override
    public Rectangle getBouds() {
        
        this.rect = new Rectangle();
        
        if(this.STATE == STATE_DOWN){
           rect.x = this.x;
           rect.y = this.y + 20;
           rect.width = this.normalAnimation.getFrame().getWidth();
           rect.height = this.normalAnimation.getFrame().getHeight() - 20;
        }else{
           rect.x = this.x;
           rect.y = this.y;
           rect.width = this.normalAnimation.getFrame().getWidth();
           rect.height = this.normalAnimation.getFrame().getHeight();
        }
        
        return rect;
    }

    @Override
    public void keyEvent(KeyEvent event) {
//        if(event.getKeyCode() == KeyEvent.VK_W){
//            this.jump();
//        }
//        if(event.getKeyCode() == KeyEvent.VK_S){
//            this.lower();
//        }
    }

    @Override
    public void KeyRelease(KeyEvent event) {
//        if(event.getKeyCode() == KeyEvent.VK_S){;
//            this.setState(RUN_NORMAL);
//        }
    }
    
    public void sensors(Graphics g){
        //g.drawRect(((x + width) + sizeSensor) - 5, (y) - 5, (width) + 5,(height) + 5);
        //g.drawRect(((x + width) + sizeSensor) - 5, (((y) - 5) - 50) % 360 , (width) + 5,(((height) + 5)) );
        Rectangle r = new Rectangle(((x + width) + sizeSensor) - 5, (y) - 5, (width) + 5,(height) + 5);
        
        g.setColor(Color.red);
        
        for(GameObject obstacule : this.handlerListObjects.getObstacule()){
            Obstacule obs = (Obstacule) obstacule;
            //g.drawLine(r.x, r.y, , obs.y);
            if(r.intersects(obs.getBouds()) && true){
                
            }
            
            if(r.x >= (int) (obs.x + obs.getBouds().getWidth())){
                
            }
        }
        
    }
    
    public void collision(){
       
        for(int i = 0;i < this.handlerListObjects.getObstacule().size();i++){
            Obstacule object = (Obstacule) this.handlerListObjects.getObstacule().get(i);
            if(object.id == Indentification.OBSTACULE){
                if(this.getBouds().intersects(object.getBouds())){
//                    DeepQLearning.lastReward = -1;
                    lastReward = -1.0;
                    this.game.removeObject(this.ID,this);
                }
            }
        }
    
    }

    @Override
    public void remove(@NotNull int i,@NotNull int i0,@NotNull int i1,@NotNull int i2,@NotNull GameRedeNeural game,@NotNull int nextInt) {
        this.stateDistance = 0;
        this.stateDistanceUpdate = 0;
        score = 0;
    }
    
    public int distanceToObstacule(){
        if((this.handlerListObjects.getObstacule().size() - 1) < this.stateDistanceUpdate){ this.stateDistanceUpdate = 0;}
        
        GameObject objectD = this.handlerListObjects.getObstacule().get(this.stateDistanceUpdate);
        if(objectD.id == Indentification.OBSTACULE){
           int distance = (int) ((objectD.x) - (this.x));
           this.distance = distance;
           this.obstacule = (Obstacule) objectD;
        }
        
        for(int i = 0;i < this.handlerListObjects.getObstacule().size();i++){
            Obstacule object = (Obstacule) this.handlerListObjects.getObstacule().get(i);
            if(object.id == Indentification.OBSTACULE){
                int objectDistance = object.x + object.getWidth();
                if(this.x >= objectDistance && object.idobject == this.stateDistanceUpdate){
                   lastReward = 1.0;
                   score++;
                   this.stateDistanceUpdate++;
                }
                int distaceT = ((object.x + object.getWidth()) - (this.x));
                
            }
        }
        return distance;
    }
    
    public void distanceToObstacule( Graphics g ){
        if((this.handlerListObjects.getObstacule().size() - 1) < this.stateDistance){ this.stateDistance = 0;}
        
//        GameObject objectD = this.handlerListObjects.getObstacule().get(this.stateDistance);
//        if(objectD.id == Indentification.OBSTACULE){
//           int distance = (int) (((objectD.x + objectD.getBouds().getWidth() ) - (this.x)) + (this.x));
//           g.drawLine(x, y, distance, objectD.y);
//           this.distance = distance;
//           this.obstacule = (Obstacule) objectD;
//        }
        
        for(int i = 0;i < this.handlerListObjects.getObstacule().size();i++){
            Obstacule object = (Obstacule) this.handlerListObjects.getObstacule().get(i);
            if(object.id == Indentification.OBSTACULE){
                int objectDistance = object.x + object.getWidth();
                if(this.x >= objectDistance && object.idobject == this.stateDistance){
                   this.stateDistance++;
                }
                int distaceT = ((object.x + object.getWidth()) - (this.x));
                
            }
        }
       
    }
    
    public float getDistance(){
        return this.distance;
    }
    
    public int getHeightObstacule(){
        return (int) this.obstacule.getBouds().getHeight();
    }
    
    public int getWidthObstacule(){
        return (int) this.obstacule.getBouds().getWidth();
    }
    
    public int getVelocity(){
        return this.velX;
    }
    
}
