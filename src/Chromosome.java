/*
  D FOUCHE
  UCT CS HONS
  FCHDYL001
*/

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.lang.*;

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
      gene[i] = instance.randomGenerator.nextBoolean(0.1);//probability to ensure some initial solutions meet weight constraint
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
    boolean[] new_gene = new boolean[instance.knapsack.items.size()];
    if(instance.mutationMethod.equals("BFM")){
      new_gene = doBFM();
    }
    else if(instance.mutationMethod.equals("EXM")){
      new_gene = doEXM();
    }
    else if(instance.mutationMethod.equals("IVM")){
      new_gene = doIVM();
    }
    else if(instance.mutationMethod.equals("ISM")){
      new_gene = doISM();
    }
    else if(instance.mutationMethod.equals("DPM")){
      new_gene = doDPM();
    }
    else{
      System.out.println("Invalid mutation method!");
      System.exit(0);
    }
    Chromosome chromosome = new Chromosome(new_gene,instance);
    return chromosome;
  }

  private Chromosome[] do1PX(Chromosome chromosome){
    //1-point crossover algorithm
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

  private Chromosome[] do2PX(Chromosome chromosome){
    //2-point crossover algorithm
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

  private boolean[] doBFM(){
    //bit flip mutation algorithm
    boolean[] new_gene = gene;
    int mutationIndex = instance.randomGenerator.nextInt(instance.knapsack.items.size()-1);
    new_gene[mutationIndex] = !new_gene[mutationIndex];
    return new_gene;
  }

  private boolean[] doEXM(){
    //exchange mutation algorithm
    boolean[] new_gene = gene;
    int mutationIndex1 = instance.randomGenerator.nextInt(instance.knapsack.items.size()-1);
    int mutationIndex2 = instance.randomGenerator.nextInt(instance.knapsack.items.size()-1);
    boolean temp = new_gene[mutationIndex1];
    new_gene[mutationIndex1] = new_gene[mutationIndex2];
    new_gene[mutationIndex2] = temp;
    return new_gene;
  }

  private boolean[] doIVM(){
    //inversion mutation algorithm
    boolean[] new_gene = gene;
    int[] mutation_indicies = {instance.randomGenerator.nextInt(instance.knapsack.items.size()-1),
      instance.randomGenerator.nextInt(instance.knapsack.items.size()-1)};
    Arrays.sort(mutation_indicies);
    while (mutation_indicies[0] < mutation_indicies[1]){
      boolean temp = new_gene[mutation_indicies[0]];
      new_gene[mutation_indicies[0]] = new_gene[mutation_indicies[1]];
      new_gene[mutation_indicies[1]] = temp;
      ++mutation_indicies[0];
      --mutation_indicies[1];
    }
    return new_gene;
  }

  private boolean[] doISM(){
    //insertion mutation algorithm
    boolean[] new_gene = gene;
    int[] mutation_indicies = {instance.randomGenerator.nextInt(instance.knapsack.items.size()-1),
      instance.randomGenerator.nextInt(instance.knapsack.items.size()-1)};
    Arrays.sort(mutation_indicies);
    boolean temp = new_gene[mutation_indicies[0]];
    for(int i=mutation_indicies[0]; i<mutation_indicies[1]; ++i){
      new_gene[i] = new_gene[i+1];
    }
    new_gene[mutation_indicies[1]] = temp;
    return new_gene;
  }

  private boolean[] doDPM(){
    //displacement mutation algorithm
    List<Boolean> new_gene_list = new LinkedList<Boolean>();
    for(boolean b : gene){
      new_gene_list.add(new Boolean(b));
    }
    int[] mutation_indicies = {instance.randomGenerator.nextInt(instance.knapsack.items.size()-1),
      instance.randomGenerator.nextInt(instance.knapsack.items.size()-1)};
    Arrays.sort(mutation_indicies);
    List<Boolean> sublist = new_gene_list.subList(mutation_indicies[0], mutation_indicies[1]);
    new_gene_list.subList(mutation_indicies[0], mutation_indicies[1]).clear();
    int insertion_index = instance.randomGenerator.nextInt(new_gene_list.size()-1);
    new_gene_list.addAll(insertion_index, sublist);
    boolean[] new_gene = new boolean[instance.knapsack.items.size()];
    int i=0;
    for(Boolean b : new_gene_list){
      new_gene[i++] = b.booleanValue();
    }
    return new_gene;
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
