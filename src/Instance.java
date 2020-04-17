/*
  D FOUCHE
  UCT CS HONS
  FCHDYL001
*/

import org.json.JSONObject;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Instance
{
  //shared fields
  Knapsack knapsack = new Knapsack();
  MersenneTwister randomGenerator = new MersenneTwister(System.currentTimeMillis());
  //genetic algorithm fields
  int populationSize = 2048;
  int maximumNumberOfGenerations = 1000000;
  double elitismRatio = 0.1;
  double crossoverRatio;
  double mutationRatio;
  String selectionMethod;
  String crossoverMethod;
  String mutationMethod;

  public Instance(String config_filename)
  {
    int underscore = config_filename.indexOf("_");
    String algorithm_type = config_filename.substring(0,underscore);
    String json_string = "";
    try{
      Scanner sc = new Scanner(new File("config/"+config_filename));
      while(sc.hasNextLine()){
        json_string += sc.nextLine();
      }
    }
    catch(IOException e){
      System.out.println("Configuration file not found!");
      System.exit(0);
    }
    JSONObject json_obj = new JSONObject(json_string);

    if(algorithm_type.equals("ga"))
    {
      this.selectionMethod = json_obj.getString("selection_method");
      this.crossoverMethod = json_obj.getString("crossover_method");
      this.mutationMethod = json_obj.getString("mutation_method");
      this.crossoverRatio = json_obj.getDouble("crossover_ratio");
      this.mutationRatio = json_obj.getDouble("mutation_ratio");
    }
    else if(algorithm_type.equals("pso"))
    {

    }
    else if(algorithm_type.equals("sa"))
    {

    }
    else
    {
      System.out.println("Invalid configuration file!");
      System.exit(0);
    }
  }

}
