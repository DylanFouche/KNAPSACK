/*
  D FOUCHE
  UCT CS HONS
  FCHDYL001
*/

import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

public class PSOSolver extends Solver
{
  public Swarm swarm;

  public PSOSolver(Instance instance)
  {
    this.instance = instance;
    this.timer = new Timer();
    this.swarm = new Swarm(instance);
    this.solutions = new CandidateSolution[instance.maxIterations];
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    LocalDateTime now = LocalDateTime.now();
    this.timestamp = dtf.format(now);
    this.generator = new Generator(instance, this);
    String parameterString = "";
    parameterString += instance.algorithm_type + " | #" + instance.particles + " |" + "w=" + instance.inertia + "|";
    parameterString += "c1=" + instance.c1 + "|" + "c2=" + instance.c2 + "|";
    parameterString += "Vmax=" + instance.maxVelocity + "|" + "Vmin=" + instance.minVelocity + "|";
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
    swarm.updatePositions();
    CandidateSolution candidateSolution = new CandidateSolution(instance, iteration,
      swarm.particles[0].weight, swarm.particles[0].fitness, swarm.particles[0].x);
    solutions[iteration] = candidateSolution;
    if(iteration == 0  || solutions[iteration].squality > currentBestSolutionQuality){
      generator.log(solutions[iteration].toString());
      currentBestSolutionQuality = solutions[iteration].squality;
      currentBestSolutionIndex = iteration;
    }
  }
}
