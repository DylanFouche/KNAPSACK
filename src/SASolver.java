/*
  D FOUCHE
  UCT CS HONS
  FCHDYL001
*/

import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

public class SASolver extends Solver
{
  public Annealer annealer;

  public SASolver(Instance instance){
    this.instance = instance;
    this.timer = new Timer();
    this.annealer = new Annealer(instance);
    this.solutions = new CandidateSolution[instance.maxIterations];
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    LocalDateTime now = LocalDateTime.now();
    this.timestamp = dtf.format(now);
    this.generator = new Generator(instance, this);
    String parameterString = instance.algorithm_type + " | Initial temperature = " + instance.initialTemp + " | ";
    parameterString += "Cooling rate = " + instance.coolingRate + " |";
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
    annealer.cool();
    CandidateSolution candidateSolution = new CandidateSolution(instance, iteration,
      annealer.calculateWeight(annealer.current_solution), annealer.calculateFitness(annealer.current_solution), annealer.current_solution);
    solutions[iteration] = candidateSolution;
    if(iteration == 0  || solutions[iteration].squality > currentBestSolutionQuality){
      generator.log(solutions[iteration].toString());
      currentBestSolutionQuality = solutions[iteration].squality;
      currentBestSolutionIndex = iteration;
    }
  }
}
