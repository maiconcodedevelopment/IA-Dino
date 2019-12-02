/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameredeneural;

import com.sun.istack.internal.Nullable;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author py-md
 */
 public class np {
     
        public static double[][] amax(double[][] x){
            
            double value = 0.0;
            int index = 0;
            double[] max_values = new double[x.length];
            double[] index_values = new double[x.length];
            
            for (int i = 0; i < x.length; i++) {
                value = 0.0;
                for (int j = 0; j < x[0].length; j++) {
                    if(x[i][j] > value){
                        index++;
                        value = x[i][j];
                        max_values[i] = value;
                        index_values[i] = j;
                    }
                }
            }
            
            double[][] listValues = {max_values,index_values};
            
//            System.out.println(Arrays.toString(max_values));
            
            return listValues;
        }
     
        public static double[][] exp(double[][] x){
            double[][] n = new double[x.length][x[0].length];
            for(int i = 0;i < x.length;i++){
               for(int a = 0;a < x[0].length;a++){
                  n[i][a] += Math.exp(x[i][a]);
               }
            }
            return n;
        }
        
        public static float[][] exp(float[][] x){
            float[][] n = new float[x.length][x[0].length];
            for(int i = 0;i < x.length;i++){
               for(int a = 0;a < x[0].length;a++){
                  n[i][a] = (float) Math.exp(x[i][a]);
               }
            }
            return n;
        }
        
         public static double[][] sigmoid(double[][] x){
            double[][] n = new double[x.length][x[0].length];
            for(int i = 0;i < x.length;i++){
               for(int a = 0;a < x[0].length;a++){
                  n[i][a] += 1 / 1 + Math.exp(-x[i][a]);
               }
            }
            return n;
        }
         
        public static double[][] relu(double[][] x){
            double[][] n = new double[x.length][x[0].length];
            for(int i = 0;i < x.length;i++){
               for(int a = 0;a < x[0].length;a++){
                  if(x[i][a] < 0){
                      n[i][a] += 0;
                  }else{
                      n[i][a] += x[i][a];
                  }
               }
            }
            return n;
        }
        
        public static double[][] softmax(double[][] x){
            return np.division(np.exp(x),(double) np.sum(np.exp(x)));
        }
        
        public static double[][] sigmoidDerivada(double[][] x){
            return np.multiply(np.exp(x),np.subtract(1,np.exp(x)));
        }
        
        public static double[][] sumArray(double[][] x,double[][] xx){
         if(x.length != xx.length || xx[0].length != x[0].length) throw new RuntimeException("Not equal");
          
          double[][] c = new double[x.length][x[0].length];

          for(int i = 0;i < x.length;i++){
            for(int j = 0;j < x[0].length;j++){
               c[i][j] += x[i][j] + xx[i][j];
            }
          }
          
          return c;
        }
     
        public static double[][] dot(double[][] x,double[][] xx){
            
            if(x[0].length != xx.length) throw new RuntimeException("Not equal");
            
            double[][] n = new double[x.length][xx[0].length];
            
            for(int i = 0;i < x.length;i++){
                for(int j = 0;j < xx[0].length;j++){
                    for(int a = 0;a < x[0].length;a++){
                        n[i][j] += x[i][a] * xx[a][j];
                    }
                }
            }
            
            return n;
        }
        
        public static double[][] multiply(double[][] x,double[][] xx){
          if(x.length != xx.length || xx[0].length != x[0].length) throw new RuntimeException("Not equal");
          double[][] c = new double[x.length][x[0].length];
          for(int i = 0;i < x.length;i++){
            for(int j = 0;j < x[0].length;j++){
               c[i][j] += x[i][j] * xx[i][j];
            }
          }
          return c;
        }
        
        public static double[][] multiply(double x,double[][] xx){
            double[][] n = new double[xx.length][xx[0].length];
            for(int i =0;i < xx.length;i++){
              for(int a = 0;a < xx[0].length;a++){
                  n[i][a] += x * xx[i][a];
              }
           }
            return n;
        }
        
        public static double[][] subtract(double a,double[][] x ){
           double[][] n = new double[x.length][x[0].length];
           for(int i = 0;i < n.length;i++){
              for(int j = 0;j < n[0].length;j++){
                  n[i][j] = a - x[i][j];
              }
           }
           return n;
        }
        
        public static double[][] subtract(double[][] x,double[][] xx){
            if(x.length != xx.length) throw new RuntimeException("Not Equal");
            double[][] n = new double[x.length][x[0].length];
            for(int i=0; i < x.length;i++){
               for(int a=0;a < x[0].length;a++){
                  n[i][a] += x[i][a] - xx[i][a];
               }
            }
            return n;
        }
        
        public static double[][] division(double[][] x,double[][] xx){
          if(x.length != xx.length || xx[0].length != x[0].length ) throw new RuntimeException("Not Equal");
          double[][] n = new double[x.length][x[0].length];
          for(int i = 0;i < x.length;i++){
             for(int j = 0;j < xx.length;j++){
                 n[i][j] += x[i][j] / xx[i][j];
             }
          }
          return n;
        }
        
        public static double[][] division(double[][] x,double xx ){
           double[][] n = new double[x.length][x[0].length];
           for(int i = 0;i < n.length;i++){
              for(int j = 0;j < n[0].length;j++){
//                  if(Double.isNaN(x[i][j] / xx)){
//                     n[i][j] = 0;
//                  }else{
                    n[i][j] = x[i][j] / xx;
//                  }
                  
              }
           }
           return n;
        }
        
        public static double[][] division_n(double[][] x,double[][] xx){
            if(x[0].length != xx.length){
               throw new RuntimeException("Matrix dimensions");
            }
            double[][] n = new double[x.length][xx[0].length];
            for(int i = 0;i < x.length;i++){
                for(int j = 0;j < xx[0].length;j++){
                    for(int a = 0;a < x[0].length;a++){
                        n[i][j] += x[i][a] / xx[a][j];
                    }
                }
            }
            return n;
        }
        
        public static double sum(double[][] input){
           double sum = 0;
           for(int i =0;i < input.length;i++){
              for(int a =0;a < input[0].length;a++){
                 sum += input[i][a];
              }
           }
           return sum;
        }
        
        public static double[][] add(double[][] x,double[][] xx){
           
            double[][] n = new double[xx.length][xx[0].length];
              for(int a =0;a < xx.length;a++){
                  for (int j = 0; j < xx[0].length; j++) {
                         n[a][j] = x[0][j] + xx[a][j];
                      }
                  }
           
           return n;
        }
        
        public static double[][] ones(int line,int column){
           double[][] n = new double[line][column];
            for (int i = 0; i < line; i++) {
                for (int j = 0; j < column; j++) {
                    n[i][j] = 1;
                }
            }
            return n;
        }
        
        public static void printArray(double[][] x,@Nullable boolean arrayPrintValue){
            if(arrayPrintValue){
              for (double[] x1 : x) {
                  for (int a = 0; a < x[0].length; a++) {
                      System.out.println(x1[a]);
                  }
              }
            }
            System.out.println(Arrays.deepToString(x));
        }
        
        public static void printArray(double[][] x) {
            System.out.println(Arrays.deepToString(x));
        }
        
        public static void printArray(Object[][] x){
           System.out.println(Arrays.deepToString(x));
        }
        
        public static void printArray(Object[] x){
           System.out.println(Arrays.deepToString(x));
        }
        
        public static double[][] T(double[][] x){
            double[][] n = new double[x[0].length][x.length];
            for(int i = 0;i < n.length;i++){
               for(int a = 0;a < n[0].length;a++){
                  n[i][a] += x[a][i];
               }
            }
            return n;
        }

        public static Object[][] zip(List<Object[][]> lists){
             
            Object[][] n = new Object[lists.get(0)[0].length][lists.size()];
            for(int i = 0; i < n.length;i++){
                System.out.println(i);
                for(int b = 0; b < n[0].length;b++){
                   n[i][b] = lists.get(0)[0][i];
                }
            }
            
            return n;
        }
        
        public static ArrayList zipSeparator(List<double[][]> lists){
            
            double[][] teste = new double[lists.size()][lists.get(0).length];;
            double[][] teste1 = new double[lists.size()][lists.get(0).length];
            double[] teste2 = new double[lists.size()];
            double[] teste3 = new double[lists.size()];
            
            for (int i = 0; i < teste.length; i++) {
                teste[i] = lists.get(i)[0];
                teste1[i] = lists.get(i)[1];
                teste2[i] = lists.get(i)[2][0];
                teste3[i] = lists.get(i)[3][0];
            }
            
            ArrayList listUp = new ArrayList();
            listUp.add(teste);
            listUp.add(teste1);
            listUp.add(teste2);
            listUp.add(teste3);
                    
            return listUp;
        }

             

}

