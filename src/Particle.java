/*
  D FOUCHE
  UCT CS HONS
  FCHDYL001
*/

import java.lang.Math.*;

public class Particle implements Comparable<Particle>
{
  public boolean[] x;
  public boolean[] pbest;
  public int pbest_fitness;
  public double[] v;
  public int weight;
  public int fitness;
  public Instance instance;

  public Particle(Instance instance)
  {
    this.instance = instance;
    this.x = generateRandomPosition();
    this.pbest = this.x;
    this.v = new double[this.x.length];
    calculateFitness();
    this.pbest_fitness = this.fitness;
  }

  public void updatePosition(boolean[] gbest){
    for(int i=0; i<x.length; ++i)
    {
      if(x!=gbest){
        //ACKNOWLEDGEMENT
        //Using the Modified Binary Particle Swarm Optimization Algorithm (MBPSO)
        //Authors: J.C. Bansal, K. Deep (2012)
        double momentum = instance.inertia * v[i];
        double cognitive = instance.randomGenerator.nextDouble() * instance.c1 * (boolToInt(pbest[i]) - boolToInt(x[i]));
        double social = instance.randomGenerator.nextDouble() * instance.c2 * (boolToInt(gbest[i]) - boolToInt(x[i]));
        v[i] = clamp(momentum + cognitive + social);
        double p =(boolToInt(x[i]) + v[i] + instance.maxVelocity)/(1 + (2 * instance.maxVelocity));
        x[i] = (instance.randomGenerator.nextDouble() < p) ? true : false;
      }
    }
    calculateFitness();
    if(fitness > pbest_fitness){
      pbest = x;
      pbest_fitness = fitness;
    }
  }

  public double clamp(double v){
    double velocity = v;
    if(velocity > instance.maxVelocity){
      velocity = instance.maxVelocity;
    }
    else if(velocity < instance.minVelocity){
      velocity = instance.minVelocity;
    }
    return velocity;
  }

  public int boolToInt(boolean b){
    return b ? 1 : 0;
  }

  public double sigmoid(double x){
    return 1 / (1 + Math.exp(x));
  }

  public void calculateFitness() {
    int fitness = 0;
    int weightTotal = 0;
    for (int i=0; i<instance.knapsack.items.size(); ++i)
    {
      if(x[i]){
        fitness += instance.knapsack.items.get(i).value;
        weightTotal += instance.knapsack.items.get(i).weight;
      }
    }
    this.weight = weightTotal;
    this.fitness = (weightTotal <= instance.knapsack.capacity) ? fitness : 0;
  }

  public boolean[] generateRandomPosition() {
    boolean[] position = new boolean[instance.knapsack.items.size()];
    for (int i=0; i<position.length; ++i)
    {
      position[i] = instance.randomGenerator.nextBoolean(0.1);
    }
    return position;
  }

  public int compareTo(Particle particle) {
      if (fitness < particle.fitness) {
          return -1;
      }
      if (fitness > particle.fitness) {
          return 1;
      }
      return 0;
  }
}
