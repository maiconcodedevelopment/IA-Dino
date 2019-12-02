/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameredeneural;

import gameredeneural.DeepQLearning.DeepQLearning;
import gameredeneural.DeepQLearning.QLearning;
import gameredeneural.DeepQLearning.ReplayMemory;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author py-md
 */
public class GameRedeNeural extends JFrame{

    private GameScreen gamescreen;
    
    public class GameScreen extends JPanel implements Runnable , KeyListener {
        
        private GameRedeNeural game;
        private Thread thread;
        
        public static final int widthDimension = 1200;
        public static final int heightDimension = 500;
        
        public static final float GRAVITY = 0.6f;
        public static final float GROUNDY = 300;
        public static final int spaceObstacule = 300;
        
        public LinkedList<Obstacule> listObstacule = new LinkedList<Obstacule>();
        public HandlerListObjects handlerListObjects;
        
        private Obstacule obstacule;
        
        private float x = 0, y = 0 , speedY = 0;
        private boolean running;
        
        public GameScreen(GameRedeNeural game){
           this.game = game;
           this.setBackground(Color.red);
           this.thread = new Thread(this);
           
           Random random = new Random();
           handlerListObjects = new HandlerListObjects();
           
            for (int i = 0; i < 10; i++) {
                this.handlerListObjects.addObject(new MainCharacter(random.nextInt(150) + 90,0,40,43,Indentification.PLAYER,handlerListObjects,this,i));
            }
           
           
           this.handlerListObjects.addObject(new NeuralNetworkObject(100, 10, 25, 25, Indentification.NEURAL));
           
           for(int i =0; i < 5;i++){
               this.handlerListObjects.addObject(new Obstacule(((this.game.getWidth() / 2)) + (i * spaceObstacule) ,(int) GROUNDY - 100,24,50,this.game,random.nextInt(2),Indentification.OBSTACULE,i));
           }

        }
        
        public void resetObjects(){
            for(int i=0;i < this.handlerListObjects.getObstacule().size();i++){
                GameObject object = this.handlerListObjects.getObstacule().get(i);
                if((object.x + 50) <= 0){
                    object.setX( ((this.handlerListObjects.getObstacule().size()) * spaceObstacule) - 50 );
                }
            }
        }
        
        public void resetGame(){
            
           Random random = new Random();
           
            for (int i = 0; i < 10; i++) {
                this.handlerListObjects.addObject(this.handlerListObjects.getObjectsBox().get(i));
            }
           
           
           this.handlerListObjects.removeAllBox();
           
           for(int i =0; i < handlerListObjects.getObjects().size();i++){
               GameObject object = handlerListObjects.getObjects().get(i);
               if(object.id == Indentification.PLAYER){
                   this.handlerListObjects.getObjects().get(i).remove(i, i, i, i, game, ALLBITS);
               }
               if(object.id == Indentification.OBSTACULE){
                   this.handlerListObjects.getObjects().get(i).remove((this.game.getWidth() / 2) + (i * spaceObstacule) ,(int) GROUNDY - 100,24,50,this.game,random.nextInt(2));
               }
           }
           
        }
        
        public void removeObject(int ID, MainCharacter aThis){
            
            this.handlerListObjects.addObjectBox(aThis);
            this.handlerListObjects.removePlayer(aThis);
            
            if(this.handlerListObjects.getPlayer().size() == 0) {
                this.resetGame();
                return;
            }
        }

        @Override
        public void keyTyped(KeyEvent e) {
            
        }

        @Override
        public void keyPressed(KeyEvent e) {
            this.handlerListObjects.KeyEvent(e);
        }

        @Override
        public void keyReleased(KeyEvent e) {
            this.handlerListObjects.KeyRelease(e);
        }
        
        public void startGame(){
           this.thread.start();
           this.running = true;
        }
        
        public void stopGame(){
           this.running = false;
        }

        @Override
        public void run() {
            
            while(this.running){
                
                try {
                      this.handlerListObjects.update();
                      this.resetObjects();
                      repaint();
                      Thread.sleep(20);
                } catch (InterruptedException ex) {
                    Logger.getLogger(GameRedeNeural.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
        @Override
        public void paint(Graphics g){
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, this.getWidth(), this.getHeight());
            g.setColor(Color.MAGENTA);
            g.drawLine(0,(int) GROUNDY,this.getWidth(),(int) GROUNDY);
            this.handlerListObjects.render(g);
        }

 
    }
    
    public GameRedeNeural(){
        super("Game Start");
        Dimension window = this.getSize();
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Point centerPoint = ge.getCenterPoint();
        
        this.setSize(new Dimension(GameScreen.widthDimension,GameScreen.heightDimension));
        this.setMaximumSize(new Dimension(GameScreen.widthDimension,GameScreen.heightDimension));
        this.setMinimumSize(new Dimension(GameScreen.widthDimension,GameScreen.heightDimension));
        
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setFocusable(true);
        this.gamescreen = new GameScreen(this);
        this.add(this.gamescreen);
        this.addKeyListener(this.gamescreen);
    }
    
    public void startGame(){
       this.gamescreen.startGame();
    }

    public static void main(String[] args) {
        
       GameRedeNeural game = new GameRedeNeural();;
       game.setVisible(true);
       game.startGame();
       
//        QLearning qLearning = new QLearning(4, 5, 3);
        
//        double[][] state = {{5,3,4,2},{4,3,4,2}};
//        double[][] q_values = {{1,0,1},{1,0,1}};
//        np.sumArray(state, q_values);
        
//        qLearning.Loss(state, q_values);
//        qLearning.Loss(state, q_values);;
        
//        double[][] dd = {{7.946128661473875E-23, 0.0}, {0.0, 0.0}, {0.0, 0.0}, {841.4546842848139, 683.0364416544453}, {0.0, 0.0}};
//        np.printArray(np.softmax(dd));
//        System.out.println(Math.exp(841.4546842848139) / Math.exp(841.4546842848139));
//        System.out.println(0.0 / 0.0);
        
        
//        double[][] td = {{-0.03346465014754889, -0.016297235869092208}, {-0.057795536545899785, -0.0291576359824996}, {-0.030602192924213477, -0.014784247620456041}, {-0.06237546810323645, -0.031578417180317474}, {-0.0406207932058874, -0.02007970649068262}};
//        np.printArray(np.softmax(td));
        //double[][] f = {{1,1,5,3},{1,1,2,3},{1,1,2,3},{1,1,2,3},{1,1,2,3},{1,1,2,3},{1,1,2,3},{1,1,2,3}};
        //System.out.println(Arrays.toString(np.amax(f)));
//        double[][] d = {{1,1,3,3},{1,1,2,3},{1,1,2,3},{1,1,2,3},{1,1,2,3},{1,1,2,3},{1,1,2,3},{1,1,2,3}}; 
//        
//        double[] x = { 1,2,3,4 };
//        double[] xr = { 1,2,3,5 };
//        
//        double[][] input = { x , x , {0},{0} };
//        double[][] input1 = { x , xr , {0},{0} };
//        
//        List<double[][]> lists = new ArrayList<>();
//        lists.add(input);
//        lists.add(input1);
//        lists.add(input1);
//        
//        np.zipSeparator(lists);
//        
        
//        np.printArray(dataSet);
        
//        System.out.println(dataSet[0]);
        
//        np.zip(dataSet,5,5,5);
        
        // first input distance
        // center input altura
        // end input largura
        // velocidade
        
//        double[][] inputData = {{0.200,0.40,0.15,0.3},{0.100,0.40,0.15,0.3},{0.100,0.40,0.15,0.3},{ 0.200,0.40,0.15,0.3}};
//        
//        double[][] weightN1 = new double[4][5];
//        double[][] weightN2 = new double[5][2];
//        
//        
//        for(int i = 0; i < 4;i++){
//          for(int a= 0; a < 5;a++){
//             weightN1[i][a] = 1 - Math.floor(Math.random() * 86464.343);
//          }
//        }
//        
//        for(int i = 0; i < 5;i++){
//          for(int a= 0; a < 2;a++){
//             weightN2[i][a] = Math.floor( Math.random() * 1000.104444);
//          }
//        }
//        
//        double[][] target = {{1,0},{0,1},{0,1},{1,0}};
//        
//        NeuralNetwork neural = new NeuralNetwork(weightN1, weightN2,0.01);
//        neural.inputNeural(inputData);
//        
//        for(int i=0;i < 10;i++){
//            neural.feedForward();
//            neural.gradientDescent(target);
//            //neural.update_weights();
//        }
//        
//        double[][] values = {{3,2,1,3}};
        
        //neural.predict(values);
        
        //np.printArray(np.T(weightN2));
        
        //double[][] t = np.division_n(input, weight_n1);
        //System.out.println(t.length);
        //System.out.println(t[0].length);
        //np.dot( np.exp(t), weight_n3);
        //double[][] m = np.division(input, weight);
    }
    
}
