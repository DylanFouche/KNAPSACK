# KNAPSACK

The knapsack assignment problem is a famous combinatorial optimization problem in computer science. An exhaustive search of the solution space is effectively non-computable for large instance sizes, as the algorithm grows as O(2<sup>n</sup>). In this instance, n=150, so evaluating each subset of items would be 2<sup>150</sup> = 1427247692705959881058285969449495136382746624 subsets. Computing this on my 4-core laptop would take tens of thousands of years. 

However, we are able to find a good approximation of the optimal subset in less than a minute, using a meta-heuristic optimization approah. Three such algorithms are implemented to demonstrate the efficiency of this approach: genetic algorithms, particle swarm optimization, and simulated annealing. Several configurations for each of these algorithms are given in /config, and one can either run a selected configuration or evaulate a set of configurations to determine the optimal configuration. In either case, each configuration will be run for 10000 iterations after which the best found solution will be given and a report saved in /data.

## To compile and run:

```bash
./run.sh -configuration [name].json
```
(or)
```bash
./run.sh -search_best_configuration [ ga | sa | pso | overall ]
```

This shell script will compile the program if necessary, and execute it with the given arguments.

## Program options:

The -configuration option will execute the single configuration json file pointed to by the second argument. This configuration file must be in the /config folder. Eg: 
```bash
./run.sh -configuration ga_default_25.json
```

The -search_best_configuration option will execute multiple configurations and find the one with the greatest solution quality. You can specify which algorithm to execute the default configurations for [ ga | sa | pso ] or search the default configurations for all three algorithms [ overall ]. A new json file will be written to the /config folder: [ ga | sa | pso | overall ]_best.json. Eg: 
```bash
./run.sh -search_best_configuration overall
```

Both options will generate a report in the /data folder after execution.

## Dependencies:

gnu make 

Oracle JDK 7+

	
