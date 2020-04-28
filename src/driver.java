/*
  D FOUCHE
  UCT CS HONS
  FCHDYL001
*/

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

public class driver
{
  static Instance instance;
  static Solver solver = null;

  public static void main(String[] args)
  {
    if(args.length==2){
      if(args[0].equals("-configuration")){
        System.out.println("Executing configuration " + args[1]);
        instance = new Instance(args[1]);
        if(instance.algorithm_type.equals("ga")){
          solver = new GASolver(instance);
        }
        else if (instance.algorithm_type.equals("sa")){
          solver = new SASolver(instance);
        }
        else if(instance.algorithm_type.equals("pso")){
          solver = new PSOSolver(instance);
        }
        if (solver!=null) solver.solve();
      }
      else if(args[0].equals("-search_best_configuration")){
        File f = new File("config/");
        String[] configurations = f.list();
        Arrays.sort(configurations);
        System.out.println("Searching for best " + args[1] + " configuration");
        double bestSquality = 0;
        String bestConfigurationName = "";
        String bestConfigurationContents = "";
        for(String configuration : configurations)
        {
          if(configuration.charAt(0)!='.'){
            instance = new Instance(configuration);
            if(args[1].equals("ga")){
              if (instance.algorithm_type.equals("ga")){
                solver = new GASolver(instance);
              }
            }
            else if (args[1].equals("sa")){
              if (instance.algorithm_type.equals("sa")){
                solver = new SASolver(instance);
              }
            }
            else if(args[1].equals("pso")){
              if (instance.algorithm_type.equals("pso")){
                solver = new PSOSolver(instance);
              }
            }
            else if(args[1].equals("overall")){
              if(instance.algorithm_type.equals("ga")){
                solver = new GASolver(instance);
              }
              else if (instance.algorithm_type.equals("sa")){
                solver = new SASolver(instance);
              }
              else if(instance.algorithm_type.equals("pso")){
                solver = new PSOSolver(instance);
              }
            }
            else{
              showUsage();
            }
            if(solver != null){
              solver.solve();
              if(solver.currentBestSolutionQuality > bestSquality){
                bestSquality = solver.currentBestSolutionQuality;
                bestConfigurationName = instance.configuration_name;
                bestConfigurationContents = instance.configuration_string;
              }
            }
            solver = null;
          }
        }
        System.out.println("The best " + args[1] + " configuration is " + bestConfigurationName);
        String outputFileName = "config/" + args[1] + "_best.json";
        try{
          PrintWriter out = new PrintWriter(outputFileName);
          out.println(bestConfigurationContents);
          System.out.println("Configuration saved to " + outputFileName);
          out.close();
        }
        catch(IOException e){
          System.out.println("Unable to write best configuration to file!");
          System.exit(0);
        }
      }
      else{
        showUsage();
      }
    }
    else{
      showUsage();
    }
  }

  public static void showUsage()
  {
    String useCase1 = "./run.sh -configuration [name].json";
    String useCase2 = "./run.sh -search_best_configuration [ ga | sa | pso | overall ]";
    System.out.println("Error in input parameters. Correct usage:");
    System.out.println(useCase1);
    System.out.println("OR");
    System.out.println(useCase2);
    System.exit(0);
  }
}
