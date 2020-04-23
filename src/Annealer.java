/*
  D FOUCHE
  UCT CS HONS
  FCHDYL001
*/

import java.lang.Math.*;

public class Annealer
{
  public boolean[] current_solution;
  public boolean[] best_solution;
  public double temperature;
  public Instance instance;

  public Annealer(Instance instance){
    this.instance = instance;
    this.temperature = instance.initialTemp;
    this.current_solution = generateRandomSolution();
    this.best_solution = this.current_solution;
  }

  public void cool(){
    boolean[] new_solution = generateRandomSolution();
    double current_energy = calculateFitness(current_solution);
    double new_energy = calculateFitness(new_solution);
    if(new_energy > current_energy){
      current_solution = new_solution;
    }
    else if(acceptanceProbability(current_energy, new_energy) > instance.randomGenerator.nextDouble()){
      current_solution = new_solution;
    }
    if(calculateFitness(current_solution) > calculateFitness(best_solution)){
      best_solution = new_solution;
    }
    temperature *= 1 - instance.coolingRate;
  }

  public double acceptanceProbability(double currentEnergy, double newEnergy){
    return Math.exp((currentEnergy-newEnergy)/temperature);
  }

  public int calculateFitness(boolean[] solution){
    int fitness = 0;
    for (int i=0; i<instance.knapsack.items.size(); ++i)
    {
      if(solution[i]){
        fitness += instance.knapsack.items.get(i).value;
      }
    }
     return (calculateWeight(solution) <= instance.knapsack.capacity) ? fitness : 0;
  }

  public int calculateWeight(boolean[] solution){
    int weightTotal = 0;
    for (int i=0; i<instance.knapsack.items.size(); ++i)
    {
      if(solution[i]){
        weightTotal += instance.knapsack.items.get(i).weight;
      }
    }
    return weightTotal;
  }

  public boolean[] generateRandomSolution() {
    boolean[] position = new boolean[instance.knapsack.items.size()];
    for (int i=0; i<position.length; ++i)
    {
      position[i] = instance.randomGenerator.nextBoolean(0.1);
    }
    return position;
  }

}
