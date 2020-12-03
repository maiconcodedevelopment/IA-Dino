/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameredeneural.DeepQLearning;

import gameredeneural.np;
import static gameredeneural.NeuralNetworkObject.camadaHidden;

/**
 *
 * @author py-md
 */
public class QLearning {

    private int inputSize;
    private int ndAction;
    private int qdHidden;

    private double[][] camadaInput;
    private double[][] camandaHidden;

    private double[][] camadaInputBias;
    private double[][] camadaHiddenBias;

    //weights
    private double[][] weightsOne;
    private double[][] weightsThow;

    private double[][] weightOneBias;
    private double[][] weightThowBias;

    private double learningRate = 0.0001;
    
    private DeepQLearning deepQLearning;

    public QLearning(int input_size, int camada_hidden, int nb_action,DeepQLearning deepQLearning) {
        
        this.deepQLearning = deepQLearning;

        this.inputSize = input_size;
        this.ndAction = nb_action;
        this.qdHidden = camada_hidden;

        this.weightsOne = new double[this.inputSize][camada_hidden];
        this.weightsThow = new double[camada_hidden][this.ndAction];
        this.weightOneBias = new double[1][camada_hidden];
        this.weightThowBias = new double[1][this.ndAction];
        // 4 -> 5 -> 3 full connection dense
        for (int i = 0; i < weightsOne.length; i++) {
            for (int a = 0; a < weightsOne[0].length; a++) {
                weightsOne[i][a] += (double) (Math.random() * 2 / 100);
            }
        }

        for (int i = 0; i < weightsThow.length; i++) {
            for (int a = 0; a < weightsThow[0].length; a++) {
                weightsThow[i][a] += (double) (Math.random() * 2 / 100);
            }
        }

        for (int i = 0; i < 1; i++) {
            for (int a = 0; a < weightOneBias[0].length; a++) {
                this.weightOneBias[i][a] += (double) (Math.random() * 2 / 100);
            }
        }

        for (int i = 0; i < 1; i++) {
            for (int a = 0; a < weightThowBias[0].length; a++) {
                this.weightThowBias[i][a] += (double) (Math.random() * 2 / 100);
            }

        }

//         np.printArray(weightsOne);
//         np.printArray(weightsThow);
//         np.printArray(weightOneBias);
//         np.printArray(weightThowBias);
    }

    public double[][] Forward(double[][] state) {
        double[][] camadaHiddenOne = np.dot(state, this.weightsOne);
        double[][] camadaHiddenOneActive = np.relu(camadaHiddenOne);

        double[][] input_bias = {{1}};
        double[][] camadaHiddenBias = np.dot(input_bias, this.weightOneBias);
        double[][] addBiasHiddenOne = np.add(camadaHiddenBias, camadaHiddenOneActive);
        
        this.deepQLearning.setCamadaHidden(camadaHiddenBias[0]);

        double[][] QValues = np.multiply(1, np.dot(addBiasHiddenOne, this.weightsThow));
        double[][] hidden_bias = {{1}};
        double[][] camandaHiddenThowBias = np.dot(hidden_bias, weightThowBias);
        double[][] addBiasHiddenThow = np.add(camandaHiddenThowBias, QValues);

        return addBiasHiddenThow;
    }

    public double[][] ForwardState(double[][] state) {
        double[][] camadaHiddenOne = np.dot(state, this.weightsOne);
        double[][] camadaHiddenOneActive = np.relu(camadaHiddenOne);

        double[][] input_bias = {{1}};
        double[][] camadaHiddenBias = np.dot(input_bias, this.weightOneBias);
        double[][] addBiasHiddenOne = np.add(camadaHiddenBias, camadaHiddenOneActive);

        camandaHidden = addBiasHiddenOne;

        double[][] QValues = np.multiply(1, np.dot(addBiasHiddenOne, this.weightsThow));
        double[][] hidden_bias = {{1}};
        double[][] camandaHiddenThowBias = np.dot(hidden_bias, weightThowBias);
        double[][] addBiasHiddenThow = np.add(camandaHiddenThowBias, QValues);

        return addBiasHiddenThow;
    }

    public double[][] Loss(double[][] state, double[][] q_values) {

        double[][] output = np.softmax(this.ForwardState(state));
//        np.printArray(output);

        double[][] error = np.multiply(2, np.subtract(q_values, output));

        // losss function
        double[][] camada_hidden_error = np.multiply(np.dot(error, np.T(this.weightsThow)), np.relu(this.camandaHidden));

        this.weightThowBias = np.sumArray(np.multiply(1, this.weightThowBias), np.multiply(learningRate, np.dot(np.T(np.ones(state.length, 1)), error)));
        this.weightsThow = np.sumArray(np.multiply(1, this.weightsThow), np.multiply(learningRate, np.dot(np.T(this.camandaHidden), error)));
        this.weightOneBias = np.sumArray(np.multiply(1, this.weightOneBias), np.multiply(learningRate, np.dot(np.T(np.ones(state.length, 1)), camada_hidden_error)));
        this.weightsOne = np.sumArray(np.multiply(1, this.weightsOne), np.multiply(learningRate, np.dot(np.T(state), camada_hidden_error)));
//        np.printArray(this.weightsThow);
        return new double[4][4];
    }

}
