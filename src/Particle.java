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
      double r1 = instance.randomGenerator.nextDouble();
      double r2 = instance.randomGenerator.nextDouble();
      double momentum = instance.inertia * v[i];
      double cognitive_component = r1 * instance.c1 * boolDiff(pbest[i], x[i]);
      double social_component = r2 * instance.c2 * boolDiff(gbest[i], x[i]);
      v[i] = clamp(momentum + cognitive_component + social_component);
      x[i] = (instance.randomGenerator.nextDouble() < sigmoid(v[i])) ? true : false;
    }
    calculateFitness();
    if(fitness > pbest_fitness){
      pbest = x;
      pbest_fitness = fitness;
    }
  }

  public double sigmoid(double x){
    return 1 / (1 + Math.exp(x));
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

  public int boolDiff(boolean a, boolean b){
    if(a && !b){
      return 1;
    }
    else if(!a && b){
      return -1;
    }
    return 0;
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
