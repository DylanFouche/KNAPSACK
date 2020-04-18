/*
  D FOUCHE
  UCT CS HONS
  FCHDYL001
*/

public abstract class Solver
{
  public Instance instance;
  public Generator generator;
  public Timer timer;
  public CandidateSolution[] solutions;
  public int iteration = 0;
  public String timestamp;
  public long runtime;
  public String parameterString;

  public abstract void solve();
  public abstract void tick();

  public String header(){
    String s = "";
    s += "Evaluation | " + timestamp + "\n";
    s += "Configuration:" + "\t" + instance.configuration_name + "\n";
    s += "\t" + "\t" + parameterString + "\n";
    s += "====================================================================" + "\n";
    s += "#" + "\t" + "bweight" + "\t" + "bvalue" + "\t" + "squality" + "\t" + "knapsack" + "\n";
    s += "--------------------------------------------------------------------" + "\n";
    return s;
  }

  public String footer(){
    String s = "";
    s += "--------------------------------------------------------------------" + "\n";
    s += "[Statistics]" + "\n";
    s += "Runtime" + "\t" + runtime + "ms" + "\n";
    s += "Convergence" + "\t" + "#" + "\t" + "bweight" + "\t" + "bvalue" + "\t" + "squality" + "\n";
    for(int i = 0; i<instance.maxIterations; ++i){
      if(i==0 || (i+1)%2500==0){
        s += "\t" + "\t" + solutions[i].iteration + "\t" + solutions[i].bweight + "\t" + solutions[i].bvalue + "\t" + solutions[i].squality + "\n";
      }
    }
    s += "====================================================================" + "\n";
    return s;
  }

  public class CandidateSolution
  {
    public Instance instance;
    public int iteration;
    public int bweight;
    public int bvalue;
    public double squality;
    public String knapsack;

    public CandidateSolution(Instance instance, int iteration, int bweight, int bvalue, boolean[] knapsack){
      this.instance = instance;
      this.iteration = iteration+1;
      this.bweight = bweight;
      this.bvalue = bvalue;
      this.squality = (bvalue / instance.knapsack.best_solution) * 100;
      String knapsackString = "[";
      for(int i=0;i<25;++i){
        if(knapsack[i]){
          knapsackString += "1";
        }
        else{
          knapsackString += "0";
        }
      }
      knapsackString += "...]";
      this.knapsack = knapsackString;
    }

    @Override
    public String toString(){
      String s = "";
      s += iteration + "\t";
      s += bweight + "\t";
      s += bvalue + "\t";
      s += squality + "%" + "\t";
      s += knapsack;
      return s;
    }
  }
}
