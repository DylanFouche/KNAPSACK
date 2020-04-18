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
  public String outputString;

  public Generator(Instance instance){
    this.instance = instance;
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
}
