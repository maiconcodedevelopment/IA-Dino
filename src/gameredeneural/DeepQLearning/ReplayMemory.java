/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameredeneural.DeepQLearning;

import gameredeneural.np;
import java.util.*;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;

/**
 *
 * @author py-md
 */
public class ReplayMemory {
    
    private int capacity; 
    public List<double[][]> memory;
    
    public static int count = 0;
    public static List<Object[]> samples;
    
    public ReplayMemory(int capacity){
       this.capacity = capacity;
       this.memory = new ArrayList<double[][]>();
       samples = new ArrayList<Object[]>();
    }
    
    // ultimo state , novo estado , ultima acao , ultima recompensa
    public void Push(double[][] event){
       this.memory.add(event);
       if(this.memory.size() > this.capacity){
           this.memory.remove(0);
       }
//       System.out.println(this.memory.size());
//       np.printArray(event);
    }
    
    public ArrayList Sample(int batch_size){
       int[] ty = new Random().ints(0,this.memory.size()).limit(batch_size).toArray();
       
       List<double[][]> on = new ArrayList<>();
       
       for(int i=0;i < ty.length; i++){
            on.add(this.memory.get(ty[i]));
       }
       
       return np.zipSeparator(on);
//       System.out.println(Arrays.deepToString((double[][]) zipped.get(0)));
    }


    
}
