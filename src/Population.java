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

  private Chromosome[] selectParents() {
    //TODO implement
    Chromosome[] parents = new Chromosome[2];
    return parents;
  }

  public Chromosome[] getPopulation() {
    Chromosome[] chromosomeArray = new Chromosome[population.length];
    System.arraycopy(population, 0, chromosomeArray, 0, population.length);
    return chromosomeArray;
  }

}
