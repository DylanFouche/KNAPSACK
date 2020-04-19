/*
  D FOUCHE
  UCT CS HONS
  FCHDYL001
*/

public class driver
{
  static Instance instance;

  public static void main(String[] args)
  {
    if(args.length==2)
    {
      if(args[0].equals("-configuration"))
      {
        System.out.println("Executing configuration " + args[1]);
        instance = new Instance(args[1]);
        Solver solver = null;
        if(instance.algorithm_type.equals("ga"))
        {
          solver = new GASolver(instance);
        }
        else if (instance.algorithm_type.equals("sa"))
        {
          solver = new SASolver(instance);
        }
        else if(instance.algorithm_type.equals("pso"))
        {
          solver = new PSOSolver(instance);
        }
        if (solver!=null) solver.solve();
      }
      else if(args[0].equals("-search_best_configuration"))
      {
        if(args[1].equals("ga"))
        {
          //TODO implement
          System.out.println("Searching for best genetic algorithm configuration");
        }
        else if (args[1].equals("sa"))
        {
          //TODO implement
          System.out.println("Searching for best simulated annealing configuration");
        }
        else if(args[1].equals("pso"))
        {
          //TODO implement
          System.out.println("Searching for best particle swarm optimization configuration");
        }
        else
        {
          showUsage();
        }
      }
      else
      {
        showUsage();
      }
    }
    else
    {
      showUsage();
    }
  }

  public static void showUsage()
  {
    String useCase1 = "driver -configuration [name].json";
    String useCase2 = "driver -search_best_configuration [ ga | sa | pso ]";
    System.out.println("Error in input parameters. Correct usage:");
    System.out.println(useCase1);
    System.out.println("OR");
    System.out.println(useCase2);
    System.exit(0);
  }
}
