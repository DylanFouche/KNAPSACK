/*
  D FOUCHE
  UCT CS HONS
  FCHDYL001
*/

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;

public class Knapsack
{
  ArrayList<knapsackItem> items = new ArrayList<knapsackItem>();

  public class knapsackItem
  {
    public int weight;
    public int value;

    public knapsackItem(int weight, int value){
      this.weight=weight;
      this.value=value;
    }
  }

  public Knapsack(){
    try{
      File file = new File("data/knapsack_instance.csv");
      Scanner sc = new Scanner(file);
      String header = sc.nextLine();
      while (sc.hasNextLine()){
        String[] line = sc.nextLine().split(";");
        knapsackItem item = new knapsackItem(Integer.parseInt(line[1]),Integer.parseInt(line[2]));
        items.add(item);
      }
    }
    catch(IOException e){
      System.out.println("Error reading in knapsack contents.");
      System.exit(0);
    }
  }

  @Override
  public String toString(){
    String s = "";
    s += "Item" + "\t" + "Weight" + "\t" + "Value" + "\n";
    int i=0;
    for(knapsackItem item : items){
      s += (++i) + "\t" + item.weight + "\t" + item.value + "\n";
    }
    return s;
  }
}
