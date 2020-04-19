/*
  D FOUCHE
  UCT CS HONS
  FCHDYL001
*/

import java.text.DecimalFormat;

public abstract class Solver
{
  public Instance instance;
  public Generator generator;
  public Timer timer;
  public CandidateSolution[] solutions;
  public double currentBestSolutionQuality;
  public int currentBestSolutionIndex;
  public int iteration = 0;
  public String timestamp;
  public long runtime;
  public String parameterString;
  public DecimalFormat df = new DecimalFormat("###.0000");

  public abstract void solve();
  public abstract void tick();

  public class CandidateSolution
  {
    public Instance instance;
    public int iteration;
    public int bweight;
    public int bvalue;
    public double squality;
    public String knapsack_string_short;
    public String knapsack_string_long;
    private DecimalFormat df = new DecimalFormat("##.0000");

    public CandidateSolution(Instance instance, int iteration, int bweight, int bvalue, boolean[] knapsack){
      this.instance = instance;
      this.iteration = iteration+1;
      this.bweight = bweight;
      this.bvalue = bvalue;
      this.squality = (double)((double)bvalue / (double)instance.knapsack.best_solution) * 100;
      this.knapsack_string_long = "[";
      this.knapsack_string_short = "[";
      for(int i=0;i<instance.knapsack.items.size();++i){
        if(i<25){
          if(knapsack[i]){
            this.knapsack_string_short += "1";
          }
          else{
            this.knapsack_string_short += "0";
          }
        }
        if(knapsack[i]){
          this.knapsack_string_long += "1";
        }
        else{
          this.knapsack_string_long += "0";
        }
      }
      this.knapsack_string_short += "...]";
      this.knapsack_string_long += "]";
    }

    @Override
    public String toString(){
      String s = "";
      s += iteration + "\t";
      s += bweight + "\t";
      s += bvalue + "\t";
      s += df.format(squality) + "%" + "\t";
      s += knapsack_string_short;
      return s;
    }
  }
}
