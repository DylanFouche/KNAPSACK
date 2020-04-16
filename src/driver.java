/*
  D FOUCHE
  UCT CS HONS
  FCHDYL001
*/

public class driver
{

  public static void main(String[] args)
  {
    if(args.length==2)
    {
      if(args[0].equals("-configuration"))
      {
        String config_filename = args[1];
        //IMPLEMENT
        System.out.println("Executing configuration " + config_filename);
      }
      else if(args[0].equals("-search_best_configuration"))
      {
        if(args[1].equals("ga"))
        {
          //IMPLEMENT
          System.out.println("Searching for best genetic algorithm configuration");
        }
        else if (args[1].equals("sa"))
        {
          //IMPLEMENT
          System.out.println("Searching for best simulated annealing configuration");
        }
        else if(args[1].equals("pso"))
        {
          //IMPLEMENT
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
