/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameredeneural;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author py-md
 */
public class ResorceImage {
    
    public static BufferedImage getImage(String path){
        try {
            return ImageIO.read(new File(path));
        } catch (IOException ex) {
            Logger.getLogger(ResorceImage.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
