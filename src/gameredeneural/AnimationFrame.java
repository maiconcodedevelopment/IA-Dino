/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameredeneural;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author py-md
 */
public class AnimationFrame {
    
    private int deltaTime;
    private int currentFrame = 0;
    private long nextTime;
    private List<BufferedImage> animationFrames;
    
    public AnimationFrame(int delta){
       this.deltaTime = delta;
       this.animationFrames = new ArrayList<BufferedImage>();
    }
    
    public void update(){
         if(System.currentTimeMillis() - this.nextTime >= this.deltaTime){
             this.currentFrame++;
             if(this.currentFrame >= this.animationFrames.size()){
                 this.currentFrame = 0;
             }
             this.nextTime = System.currentTimeMillis();
         }
    }
    
    public void addImage(BufferedImage image){
        this.animationFrames.add(image);
    }
    
    public BufferedImage getFrame(){
       return this.animationFrames.get(this.currentFrame);
    }
    
}
