/*
  D FOUCHE
  UCT CS HONS
  FCHDYL001
*/

import java.util.Arrays;
import java.util.Collections;

public class Population
{
  public Chromosome[] population;
  public Instance instance;

  public Population(Instance instance)
  {
    this.instance = instance;
    population = new Chromosome[instance.populationSize];
    for (int i=0; i<instance.populationSize; ++i){
      population[i] = new Chromosome(instance);
    }
    Arrays.sort(population, Collections.reverseOrder());
  }

  public void evolve() {
    Chromosome[] chromosomeArray = new Chromosome[population.length];
    int index = (int) Math.round(population.length * instance.elitismRatio);
    System.arraycopy(population, 0, chromosomeArray, 0, index);

    while (index < chromosomeArray.length) {
      if (instance.randomGenerator.nextFloat() <= instance.crossoverRatio) {
        Chromosome[] parents = selectParents();
        Chromosome[] children = parents[0].doCrossover(parents[1]);

        if (instance.randomGenerator.nextFloat() <= instance.mutationRatio) {
          chromosomeArray[(index++)] = children[0].doMutation();
        }
        else {
          chromosomeArray[(index++)] = children[0];
        }

        if (index < chromosomeArray.length){
          if (instance.randomGenerator.nextFloat() <= instance.mutationRatio) {
            chromosomeArray[index] = children[1].doMutation();
          }
          else {
            chromosomeArray[index] = children[1];
          }
        }
        else if (instance.randomGenerator.nextFloat() <= instance.mutationRatio) {
          chromosomeArray[index] = population[index].doMutation();
        }
        else {
          chromosomeArray[index] = population[index];
        }
        index++;
      }
    }
    Arrays.sort(chromosomeArray, Collections.reverseOrder());
    population = chromosomeArray;
  }

  private Chromosome[] selectParents(){
    Chromosome[] parents = new Chromosome[2];
    if(instance.selectionMethod.equals("RWS")){
      parents = doRWS();
    }
    else if(instance.selectionMethod.equals("TS")){
      parents = doTS();
    }
    else{
      System.out.println("Invalid parent selection method!");
      System.exit(0);
    }
    return parents;
  }

  private Chromosome[] doTS(){
    int tournament_size = instance.populationSize / 10;
    Chromosome[] competitors = new Chromosome[tournament_size];
    for(int i=0; i<tournament_size; ++i){
      competitors[i] = population[instance.randomGenerator.nextInt(instance.populationSize-1)];
    }
    Arrays.sort(competitors, Collections.reverseOrder());
    Chromosome[] parents = {competitors[0], competitors[1]};
    return parents;
  }

  private Chromosome[] doRWS(){
    long fitness_sum = 0;
    for(Chromosome c : population){
      fitness_sum += c.fitness;
    }
    double[] probabilities = new double[instance.populationSize];
    double probability_sum = 0;
    int i=0;
    for(Chromosome c : population){
      double probability = probability_sum + (c.fitness / fitness_sum);
      probability_sum += probability;
      probabilities[i++] = probability;
    }
    Chromosome[] parents = new Chromosome[2];
    for(int j=0; j<2; ++j){
      double n = instance.randomGenerator.nextDouble();
      for(int k=0; k<instance.populationSize; ++k){
        if(n > probabilities[k]){
          parents[j] = population[k];
          break;
        }
      }
    }
    return parents;
  }

}
