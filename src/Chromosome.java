/*
  D FOUCHE
  UCT CS HONS
  FCHDYL001
*/

import java.util.Arrays;

public class Chromosome implements Comparable<Chromosome>
{
  public boolean[] gene;
  public int weight;
  public int fitness;
  public Instance instance;

  public Chromosome(Instance instance)
  {
    this.instance = instance;
    this.gene = generateRandomGene();
    this.fitness = calculateFitness();
  }

  public Chromosome(boolean[] gene, Instance instance)
  {
    this.instance = instance;
    this.gene = gene;
    this.fitness = calculateFitness();
  }

  public int calculateFitness() {
    int fitness = 0;
    int weightTotal = 0;
    for (int i=0; i<instance.knapsack.items.size(); ++i)
    {
      if(gene[i])
      {
        fitness += instance.knapsack.items.get(i).value;
        weightTotal += instance.knapsack.items.get(i).weight;
      }
    }
    this.weight = weightTotal;
    return weightTotal <= instance.knapsack.capacity ? fitness : 0;
  }

  public boolean[] generateRandomGene() {
    boolean[] gene = new boolean[instance.knapsack.items.size()];
    for (int i=0; i<gene.length; ++i)
    {
      gene[i] = instance.randomGenerator.nextBoolean();
    }
    return gene;
  }

  public Chromosome[] doCrossover(Chromosome chromosome) {
    Chromosome[] children = new Chromosome[2];
    if(instance.crossoverMethod.equals("1PX")){
      children = do1PX(chromosome);
    }
    else if(instance.crossoverMethod.equals("2PX")){
      children = do2PX(chromosome);
    }
    else{
      System.out.println("Invalid crossover method!");
      System.exit(0);
    }
    return children;
  }

  public Chromosome doMutation() {
    //TODO implement
    Chromosome chromosome = new Chromosome(instance);
    return chromosome;
  }

  public Chromosome[] do1PX(Chromosome chromosome){
    Chromosome[] children = new Chromosome[2];
    boolean[] child1_gene = new boolean[instance.knapsack.items.size()];
    boolean[] child2_gene = new boolean[instance.knapsack.items.size()];
    int crossover_point = instance.randomGenerator.nextInt(instance.knapsack.items.size()-1);
    for(int i=0; i<instance.knapsack.items.size();++i)
    {
      if(i<crossover_point){
        child1_gene[i] = gene[i];
        child2_gene[i] = chromosome.gene[i];
      }
      else{
        child1_gene[i] = chromosome.gene[i];
        child2_gene[i] = gene[i];
      }
    }
    children[0] = new Chromosome(child1_gene,instance);
    children[1] = new Chromosome(child2_gene,instance);
    return children;
  }

  public Chromosome[] do2PX(Chromosome chromosome){
    Chromosome[] children = new Chromosome[2];
    boolean[] child1_gene = new boolean[instance.knapsack.items.size()];
    boolean[] child2_gene = new boolean[instance.knapsack.items.size()];
    int[] crossover_points = {instance.randomGenerator.nextInt(instance.knapsack.items.size()-1),
      instance.randomGenerator.nextInt(instance.knapsack.items.size()-1)};
    Arrays.sort(crossover_points);
    for(int i=0; i<instance.knapsack.items.size();++i)
    {
      if(i<crossover_points[0]){
        child1_gene[i] = gene[i];
        child2_gene[i] = chromosome.gene[i];
      }
      else if (i>crossover_points[0] && i<crossover_points[1]){
        child1_gene[i] = chromosome.gene[i];
        child2_gene[i] = gene[i];
      }
      else{
        child1_gene[i] = gene[i];
        child2_gene[i] = chromosome.gene[i];
      }
    }
    children[0] = new Chromosome(child1_gene,instance);
    children[1] = new Chromosome(child2_gene,instance);
    return children;
  }

  public int compareTo(Chromosome chromosome) {
      if (fitness < chromosome.fitness) {
          return -1;
      }
      if (fitness > chromosome.fitness) {
          return 1;
      }
      return 0;
  }

    public boolean equals(Object o) {
      if (!(o instanceof Chromosome)) {
          return false;
      }
      Chromosome chromosome = (Chromosome) o;
      return (gene.equals(chromosome.gene)) && (fitness == chromosome.fitness);
  }
}
