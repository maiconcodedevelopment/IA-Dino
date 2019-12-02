/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameredeneural;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

/**
 *
 * @author py-md
 */
public class CanvasGame extends Canvas implements Runnable {
    
    public class WindowGame{
        public WindowGame(String title,int w,int h,CanvasGame game){
           JFrame frame = new JFrame(title);
           frame.setPreferredSize(new Dimension(w,h));
           frame.setMinimumSize(new Dimension(w,h));
           frame.setMaximumSize(new Dimension(w,h));
           frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
           frame.setResizable(true);
           frame.setLocationRelativeTo(null);
           frame.setFocusable(true);
           frame.add(game);
           frame.setVisible(true);
           game.start();
        }
    }
   
    private Thread thread;
    private boolean running;
    
    private final static int WIDTH = 500 , HEIGHT = 1000;
    
    public CanvasGame(){
        new WindowGame("GAME FLAPPY BIRD",WIDTH,HEIGHT,this);
    }
    
    public synchronized void start(){
        thread = new Thread(this);
        thread.start();
        running = true;
    }
    
    public synchronized void stop(){
        try {
            thread.join();
            running = false;
        } catch (InterruptedException ex) {
            Logger.getLogger(CanvasGame.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }

    @Override
    public void run() {
         System.out.println(System.nanoTime());
         System.out.println(System.currentTimeMillis());
         System.out.println("Running Here Game");
         
         long lastTime = System.nanoTime();
         long timer = System.currentTimeMillis();
         
         double ns = 1000000000 / 60.0;
         double delta = 0;
         int frames = 0;
         // 1 s -> 1,000 ms -> 1000000 micro -> 1000000000 nano         
         while(running){;
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            
            System.out.println(System.nanoTime());
            
            while(delta >= 1){
               delta--;
               update();
            }
            frames++;
            
            if(running){
                render();
            }
            
            frames++;
            
            if(System.currentTimeMillis() - timer > 1000){
                timer += 1000;
                frames = 0;
            }
         };
    }
    
    public void update(){
        
    }
    
    public void render(){
        BufferStrategy bufferStrategy = this.getBufferStrategy();
        if(bufferStrategy == (null)){
           this.createBufferStrategy(3);
           return;
        }
        Graphics g = bufferStrategy.getDrawGraphics();
        g.setColor(Color.red);
        g.fillRect(0, 0,WIDTH, HEIGHT);
        
        g.dispose();
        bufferStrategy.show();
    }
    
    public static void main(String[] args){
        new CanvasGame();
    }
    
}
