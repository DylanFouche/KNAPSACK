/*
  D FOUCHE
  UCT CS HONS
  FCHDYL001
*/

import java.util.Arrays;
import java.util.Collections;

public class Swarm
{
  public Particle[] particles;
  public boolean[] gbest;
  public int gbest_fitness;
  public Instance instance;

  public Swarm(Instance instance)
  {
    this.instance = instance;
    this.particles = new Particle[instance.particles];
    for (int i=0; i<instance.particles; ++i){
      this.particles[i] = new Particle(instance);
    }
    Arrays.sort(particles, Collections.reverseOrder());
    this.gbest = particles[0].x;
    this.gbest_fitness = particles[0].fitness;
  }

  public void updatePositions()
  {
    for(int i=0; i<particles.length; ++i){
      particles[i].updatePosition(gbest);
    }
    Arrays.sort(particles, Collections.reverseOrder());
    if(particles[0].fitness > gbest_fitness){
      gbest = particles[0].x;
      gbest_fitness = particles[0].fitness;
    }
  }
}
