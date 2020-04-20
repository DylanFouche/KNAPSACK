/*
  D FOUCHE
  UCT CS HONS
  FCHDYL001
*/

import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

public class GASolver extends Solver
{
  public Population population;

  public GASolver(Instance instance)
  {
    this.instance = instance;
    this.timer = new Timer();
    this.population = new Population(instance);
    this.solutions = new CandidateSolution[instance.maxIterations];
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    LocalDateTime now = LocalDateTime.now();
    this.timestamp = dtf.format(now);
    this.generator = new Generator(instance, this);
    String parameterString = "";
    parameterString += instance.algorithm_type + " | #" + instance.maxIterations + " | " + instance.selectionMethod + " | ";
    parameterString += instance.crossoverMethod + "(" + instance.crossoverRatio + ") | ";
    parameterString += instance.mutationMethod + "(" + instance.mutationRatio + ")";
    this.parameterString = parameterString;
  }

  public void solve(){
    currentBestSolutionQuality = 0;
    generator.header();
    timer.tick();
    while(iteration < instance.maxIterations){
      tick();
      ++iteration;
    }
    this.runtime = timer.tock();
    generator.footer();
    generator.writeLog();
  }

  public void tick(){
    population.evolve();
    CandidateSolution candidateSolution = new CandidateSolution(instance, iteration,
      population.population[0].weight, population.population[0].fitness, population.population[0].gene);
    solutions[iteration] = candidateSolution;
    if(iteration == 0  || solutions[iteration].squality > currentBestSolutionQuality){
      generator.log(solutions[iteration].toString());
      currentBestSolutionQuality = solutions[iteration].squality;
      currentBestSolutionIndex = iteration;
    }
  }
}
