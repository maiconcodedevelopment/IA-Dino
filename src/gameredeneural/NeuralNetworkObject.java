/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameredeneural;

import static gameredeneural.GameRedeNeural.GameScreen.GRAVITY;
import static gameredeneural.GameRedeNeural.GameScreen.GROUNDY;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.*;
import java.util.Arrays;

/**
 *
 * @author py-md
 */
public class NeuralNetworkObject extends GameObject {

    private float sppedX = 1f;

    public static double[] inputData;
    public static double[] camadaHidden;
    public static double[] camadaOutput;
    
    private HandlerListObjects listHandler;
    private GameRedeNeural.GameScreen gameScreen;

    public NeuralNetworkObject(int x, int y, int w, int h, Indentification id,int idObject,HandlerListObjects listHandler, GameRedeNeural.GameScreen aThis) {
        super(x, y, w, h, id,idObject);
        this.listHandler = listHandler;
        this.gameScreen = aThis;
    }

    @Override
    public void update() {
        this.x += this.sppedX;
        
        
        System.out.println(this);
    }

    @Override
    public void render(Graphics g) {
        
        g.setColor(Color.BLUE);
        g.drawString(String.valueOf(this.gameScreen.SCOREGAME),50,50);
        g.setColor(Color.PINK);
        
        int lineHeight = 50;

        int x = 20;
        int y = (int) GROUNDY + 50;

        int[] s = {5, 5, 2};

        createCamadaNeural(g, x, y, 3, s);

    }

    public void createCamadaNeural(Graphics g, int x, int y, int quantityCamada, int[] camadaCamadaHidden) {
        
        double[][] camadaNeural = this.listHandler.getPlayer().getFirst().getDeepLearning().getHiddenNeural();
        double[] inputData_ = camadaNeural[0];
        double[] camadaHidden_ = camadaNeural[1];
        double[] output_ = camadaNeural[2];

        int lineHeight = 50;

        for (int i = 0; i < quantityCamada; i++) {
            for (int a = 0; a < camadaCamadaHidden[i]; a++) {
                if (i == 0) { // get index maincharater
                    neuralOval(g, (int) inputData_[a], (i * 200) + x, (a * lineHeight) + y, 50, 50);
                }if(i == 1){
                    neuralOval(g, (int) (camadaHidden_[a] * 100 * 10), (i * 200) + x, (a * lineHeight) + y, 50, 50);
                }if(i == 2){
                    neuralOval(g, (int) (output_[a] * 2), (i * 200) + x, (a * lineHeight) + y, 50, 50);
                }
            }

            for (int b = 0; b < camadaCamadaHidden[i]; b++) {
                if ((i + 1) >= quantityCamada) {
                    return;
                } else {
                    for (int a = 0; a < camadaCamadaHidden[i + 1]; a++) {
//                        g.drawLine((b * 200) + (25 + x), b, a, a);
                        g.drawLine(((i * 200) + (50 + x)), (b * lineHeight) + (50 / 2) + y, ((i + 1) * 200) + x, (a * lineHeight) + (50 / 2) + y);
                    }
                }
            }

        }

    }

    public void neuralOval(Graphics g, int value, int x, int y, int width, int height) {
        Graphics2D gd2 = (Graphics2D) g;
        gd2.setColor(Color.RED);
        gd2.drawOval(x, y, width, height);
        gd2.drawString(Integer.toString(value), x + (10), y + (50 / 2) + 5);
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
