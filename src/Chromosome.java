/*
  D FOUCHE
  UCT CS HONS
  FCHDYL001
*/

public class Chromosome implements Comparable<Chromosome>
{
  private boolean[] gene;
  private int fitness;
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
    //TODO implement
    Chromosome[] children = new Chromosome[2];
    Chromosome[] parents = new Chromosome[2];
    return children;
  }

  public Chromosome doMutation() {
    //TODO implement
    Chromosome chromosome = new Chromosome(instance);
    return chromosome;
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
