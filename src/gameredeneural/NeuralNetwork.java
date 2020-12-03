/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameredeneural;

import com.sun.istack.internal.NotNull;

/**
 *
 * @author py-md
 */
public class NeuralNetwork {

    private double[][] inputData;
    private double[][] weightN1;
    private double[][] weightN2;

    private double learningRate = 0.01;

    private double[][] camada_hidden_n1;
    private double[][] camada_hidden_n2;
    private double[][] error;
    private double[][] camada_hidden_error;

    public NeuralNetwork(double[][] weight_n1, double[][] weight_n2, @NotNull double learning_rate) {
        this.learningRate = learning_rate;
        this.weightN1 = weight_n1;
        this.weightN2 = weight_n2;
    }

    public void inputNeural(double[][] input) {
        this.inputData = input;
    }

    public void feedForward() {
        camada_hidden_n1 = np.relu(np.multiply(1, np.dot(this.inputData, this.weightN1)));
        camada_hidden_n2 = np.relu(np.multiply(1, np.dot(camada_hidden_n1, weightN2)));
        //np.printArray(camada_hidden_n2);
    }

    public void gradientDescent(double[][] target) {
        error = np.multiply(2, np.subtract(camada_hidden_n2, target));
        System.out.println(np.sum(error) / 2);
        camada_hidden_error = np.multiply(np.dot(error, np.T(this.weightN2)), np.relu(camada_hidden_n1));
    }

    public void update_weights() {
        weightN2 = np.subtract(np.multiply(1, weightN2), np.multiply(learningRate, np.dot(np.T(camada_hidden_n1), error)));
        weightN1 = np.subtract(np.multiply(1, weightN1), np.multiply(learningRate, np.dot(np.T(inputData), camada_hidden_error)));
        //np.printArray(weightN2);
    }

    public double[][] predict(double[][] values) {
        if (values[0].length != this.inputData[0].length) {
            throw new RuntimeException("Input Error");
        }
        camada_hidden_n1 = np.relu(np.dot(values, this.weightN1));
        camada_hidden_n2 = np.relu(np.dot(camada_hidden_n1, weightN2));
        return camada_hidden_n2;
    }

    public double[][] getFeedForward() {
        return camada_hidden_n2;
    }

}
