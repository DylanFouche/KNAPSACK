/*
  D FOUCHE
  UCT CS HONS
  FCHDYL001
*/

import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class Generator
{
  public Instance instance;
  public Solver solver;
  public String outputString;

  public Generator(Instance instance, Solver solver){
    this.instance = instance;
    this.solver = solver;
    outputString = "";
  }

  public void log(String s){
    System.out.println(s);
    outputString += s + "\n";
  }

  public void writeLog(){
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
    LocalDateTime now = LocalDateTime.now();
    String outputFileName = "data/report_" + instance.algorithm_type + "_" + dtf.format(now) + ".txt";
    try{
      PrintWriter out = new PrintWriter(outputFileName);
      out.println(outputString);
      out.close();
    }
    catch(IOException e){
      System.out.println("Unable to write report to file!");
      System.exit(0);
    }
  }

  public void header(){
    String s = "";
    s += "Evaluation | " + solver.timestamp + "\n";
    s += "Configuration:" + "\t" + instance.configuration_name + "\n";
    s += "\t" + "\t" + solver.parameterString + "\n";
    s += "======================================================================" + "\n";
    s += "#" + "\t" + "bweight" + "\t" + "bvalue" + "\t" + "squality" + "\t" + "knapsack" + "\n";
    s += "----------------------------------------------------------------------";
    log(s);
  }

  public void footer(){
    String s = "";
    s += "----------------------------------------------------------------------" + "\n";
    s += "[Statistics]" + "\n";
    s += "Runtime" + "\t" + solver.runtime + "ms" + "\n\n";
    s += "Convergence" + "\t" + "#" + "\t" + "bweight" + "\t" + "bvalue" + "\t" + "squality" + "\n";
    for(int i = 0; i<instance.maxIterations; ++i){
      if(i==0 || (i+1)%2500==0){
        s += "\t" + "\t" + solver.solutions[i].iteration + "\t" + solver.solutions[i].bweight + "\t" +
          solver.solutions[i].bvalue + "\t" + solver.df.format(solver.solutions[i].squality) + "%" + "\n";
      }
    }
    s += "\n";
    s += "Best solution" + "\n";
    s += "weight:" + "\t" + solver.solutions[solver.currentBestSolutionIndex].bweight + "\t";
    s += "value:" + "\t" + solver.solutions[solver.currentBestSolutionIndex].bvalue + "\n";
    s += (solver.solutions[solver.currentBestSolutionIndex].knapsack_string_long).substring(0,52) + "\n";
    s += (solver.solutions[solver.currentBestSolutionIndex].knapsack_string_long).substring(52,104) + "\n";
    s += (solver.solutions[solver.currentBestSolutionIndex].knapsack_string_long).substring(104) + "\n";
    s += "======================================================================" + "\n";
    log(s);
  }
}
