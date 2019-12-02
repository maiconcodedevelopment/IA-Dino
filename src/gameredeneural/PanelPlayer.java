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
public class PanelPlayer extends GameObject {

    public PanelPlayer(int x, int y, int w, int h, Indentification id) {
        super(x, y, w, h, id);
        
    }
    

    @Override
    public void update() {
        
    }

    @Override
    public void render(Graphics g) {
        g.drawString("Distance Player - Obstacule: "+3+"", x, y);
    }

    @Override
    public void keyEvent(KeyEvent event) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void KeyRelease(KeyEvent event) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Rectangle getBouds() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void remove(int i, int i0, int i1, int i2, GameRedeNeural game, int nextInt) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
