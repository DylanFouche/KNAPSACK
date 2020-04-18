/*
  D FOUCHE
  UCT CS HONS
  FCHDYL001
*/

public class Timer
{
  public long startTime;
  public long endTime;

  public Timer(){}

  public void tick(){
    startTime = System.currentTimeMillis();
  }

  public long tock(){
    endTime = System.currentTimeMillis();
    return endTime - startTime;
  }
}
