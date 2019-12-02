/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameredeneural;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.LinkedList;

/**
 *
 * @author py-md
 */
public class HandlerListObjects {
    
    protected LinkedList<GameObject> listObjects = new LinkedList<GameObject>();
    protected LinkedList<GameObject> listObjectsBox = new LinkedList<GameObject>();
    
    public void update(){
        for(int i = 0;i < this.listObjects.size();i++){
            GameObject ob = this.listObjects.get(i);
            ob.update();
        }
    }
    
    public void render(Graphics g){
         for(int i = 0;i < this.listObjects.size();i++){
            GameObject ob = this.listObjects.get(i);
            ob.render(g);
        }
    }
    
    public void KeyEvent(KeyEvent event){
         for(int i = 0;i < this.listObjects.size();i++){
            GameObject ob = this.listObjects.get(i);
            ob.keyEvent(event);
        }
    }
    
    public void KeyRelease(KeyEvent event){
        for(int i = 0;i < this.listObjects.size();i++){
            GameObject ob = this.listObjects.get(i);
            ob.KeyRelease(event);
        }
    }
    
    public void addObject(GameObject object){
       this.listObjects.add(object);
    }
    public void addObjectBox(GameObject object){
       this.listObjectsBox.add(object);
    }
    
    public void removeObject(GameObject object){
       this.listObjects.remove(object);
    }
    
    public void removePlayer(GameObject object){
      this.listObjects.remove(object);
    }
    
    public void removeBox(GameObject object){
      this.listObjects.remove(object);
    }
    
    public void removeAllBox(){
      this.listObjectsBox.removeAll(listObjectsBox);
    }
    
    public LinkedList<GameObject> getObjects(){
        return this.listObjects;
    }
    
    public LinkedList<GameObject> getObjectsBox(){
        return this.listObjectsBox;
    }
    
    public LinkedList<GameObject> getPlayer(){
        LinkedList<GameObject> lis = new LinkedList();
        for(int i =0; i < this.listObjects.size();i++){
           if(Indentification.PLAYER == this.listObjects.get(i).id){
              lis.add(this.listObjects.get(i));
           }
        }
        return lis;
    }
    
    public LinkedList<GameObject> getObstacule(){
        LinkedList<GameObject> lis = new LinkedList();
        for(int i =0; i < this.listObjects.size();i++){
           if(Indentification.OBSTACULE == this.listObjects.get(i).id){
              lis.add(this.listObjects.get(i));
           }
        }
        return lis;
    }
}
