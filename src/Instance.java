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
  int maxIterations = 10000;
  String configuration_name;
  String configuration_string;
  String algorithm_type;
  //genetic algorithm fields
  int populationSize = 2048;
  double elitismRatio = 0.1;
  double crossoverRatio;
  double mutationRatio;
  String selectionMethod;
  String crossoverMethod;
  String mutationMethod;
  //particle swarm optimization fields
  double c1;
  double c2;
  double inertia;
  int maxVelocity;
  int minVelocity;
  int particles;
  //simulated annealing fields
  int initialTemp;
  double coolingRate;

  public Instance(String config_filename)
  {
    this.algorithm_type = config_filename.substring(0,config_filename.indexOf("_"));
    String json_string = "";
    try{
      Scanner sc = new Scanner(new File("config/"+config_filename));
      while(sc.hasNextLine()){
        json_string += sc.nextLine();
      }
      sc.close();
    }
    catch(IOException e){
      System.out.println("Configuration file not found!");
      System.exit(0);
    }
    JSONObject json_obj = new JSONObject(json_string);
    this.configuration_string = json_string;
    this.configuration_name = json_obj.getString("configuration");

    if(this.algorithm_type.equals("ga"))
    {
      this.selectionMethod = json_obj.getString("selection_method");
      this.crossoverMethod = json_obj.getString("crossover_method");
      this.mutationMethod = json_obj.getString("mutation_method");
      this.crossoverRatio = json_obj.getDouble("crossover_ratio");
      this.mutationRatio = json_obj.getDouble("mutation_ratio");
    }
    else if(this.algorithm_type.equals("pso"))
    {
      this.particles = json_obj.getInt("number_particles");
      this.maxVelocity = json_obj.getInt("maximum_velocity");
      this.minVelocity = json_obj.getInt("minimum_velocity") * -1;
      this.inertia = json_obj.getDouble("inertia");
      this.c1 = json_obj.getDouble("c1");
      this.c2 = json_obj.getDouble("c2");
    }
    else if(this.algorithm_type.equals("sa"))
    {
      this.initialTemp = json_obj.getInt("initial_temperature");
      this.coolingRate = json_obj.getDouble("cooling_rate");
    }
    else
    {
      System.out.println("Invalid configuration file!");
      System.exit(0);
    }
  }

}
