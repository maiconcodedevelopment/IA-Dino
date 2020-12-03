/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameredeneural.DeepQLearning;

import gameredeneural.np;
import java.util.*;
import static gameredeneural.NeuralNetworkObject.camadaOutput;

/**
 *
 * @author py-md
 */
public class DeepQLearning {

    private double descont;
    private int[] rewardWindow;
    public QLearning qLearning;
    private ReplayMemory memory;
    private OPtimizer optimizer;
    
    public static double[] lastState;
    public static double lastAction;
    public static double lastReward;
    
    private double[] camadaInput;
    private double[] camadaHidden;
    private double[] camadaOutput;

    public double Descont = 0.009;

    public DeepQLearning(int input_size, int nb_action, double descont) {
        this.descont = descont;
        this.memory = new ReplayMemory(1000);
        this.qLearning = new QLearning(input_size, 5, nb_action,this);

        lastState = new double[input_size];

        for (int i = 0; i < 4; i++) {
            lastState[i] += 0;
        }

        lastAction = 0;
        lastReward = 0;
    }

    public QLearning getQLearning() {
        return this.qLearning;
    }

    public double[][] selectAction(double[][] state) {
        double[][] probs = np.softmax(np.multiply(1, this.qLearning.Forward(state)));
        camadaOutput = probs[0];
        
//       np.printArray(probs);
//       double[] action = np.amax(probs);
//       System.out.println(action[1]);
        return np.amax(probs);
    }

    public double[][] selectionActionForward(double[][] state) {
        return np.softmax(this.qLearning.Forward(state));
    }

    public double[][] LossGradient(double[][] state, double[][] q_values) {
        return this.qLearning.Loss(state, q_values);
    }

    public void learn() {
        // q(s,a) + learning * ( reward + descont * max(q(s,a)...) -> target - (q,s) -> predict  )
    }

    private void learn(ArrayList samples) {

        double[][] newStateList = (double[][]) samples.get(0);
        double[][] lastStateList = (double[][]) samples.get(1);
        double[] rewardLastList = (double[]) samples.get(2);
        double[] actionLastList = (double[]) samples.get(3);

        for (int i = 0; i < newStateList.length; i++) {

            double[][] stateNext = {newStateList[i]};
            double[][] q_predict = this.selectionActionForward(stateNext);
            double[][] q_predict_max = np.amax(q_predict);
            double output = q_predict_max[0][0];

            int reward = (int) rewardLastList[i];
            double q_state_learning = (reward + Descont * output);

            int actionIndex = (int) actionLastList[i];
            double[][] lastState = {lastStateList[i]};
            double[][] q_values = this.selectionActionForward(lastState);

            q_values[0][actionIndex] = q_state_learning;
        }

    }

    private void learnBatch(ArrayList samples) {

        double[][] newStateList = (double[][]) samples.get(0);
        double[][] lastStateList = (double[][]) samples.get(1);
        double[] rewardLastList = (double[]) samples.get(2);
        double[] actionLastList = (double[]) samples.get(3);

        double[][] max_predicts = np.amax(this.selectionActionForward(newStateList));
        double[] max_statenext_predict = max_predicts[0];
        double[][] max_statenext = {max_statenext_predict};
        double[][] reward_list = {rewardLastList};

        double[][] q_update = np.sumArray(reward_list, np.multiply(descont, max_statenext));

        double[][] q_values = this.selectionActionForward(lastStateList);

        for (int i = 0; i < q_update[0].length; i++) {
            int action = (int) actionLastList[i];
            q_values[i][action] = q_update[0][i];
        }

        this.LossGradient(lastStateList, q_values);
    }

    public double update(double last_reward, double[] new_state, int score) {
        
        camadaInput = new_state;
        
        double[] newState = new_state;

        double[][] event = {newState, lastState, {lastReward}, {lastAction}};

        this.memory.Push(event);

        //action network
        double[][] InputState = {new_state};
        double[][] action = this.selectAction(InputState);

        if (score < 300) {
            if (this.memory.memory.size() > 100) {
                this.learnBatch(this.memory.Sample(10));
            }
        }

        lastState = newState;
        lastReward = last_reward;
        lastAction = action[1][0];

        return action[1][0];
    }
    
    public void setCamadaHidden(double[] camada){
      this.camadaHidden = camada;
    }
    
    public double[][] getHiddenNeural(){
      return new double[][]{ this.camadaInput , this.camadaHidden , this.camadaOutput };
    }

}
