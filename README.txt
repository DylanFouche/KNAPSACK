# D Fouche
# UCT CS Hons
# fchdyl001

--- Dependencies:

gnu make
Oracle JDK 7+

--- To compile and execute:

./run.sh -configuration [name].json
(or)
./run.sh -search_best_configuration [ ga | sa | pso | overall ]

This shell script will compile the program if necessary, and execute it with the given arguments.

The -configuration option will execute the single configuration json file pointed to by the second argument. This configuration must be in the /config folder. Eg: ./run.sh -configuration ga_default_25.json

The -search_best_configuration option will execute multiple configurations and find the one with the greatest solution quality. You can specify which algorithm to execute the default configurations for [ ga | sa | pso ] or search the default configurations for all three algorithms [overall]. A new json will be written to the /config folder: [ga|sa|pso|overall]_best.json. Eg: ./run.sh -search_best_configuration overall

Both options will generate a report in the /data folder after execution.

--- General remarks:

I found the best configuration to be ga_default_25, yielding a solution quality of 113% (weight 822, value 1128). For the ga algorithm, the tournament selection method seemed to consistently outperform the roulette wheel selection method, perhaps due to higher selection pressure in a very limited number of iterations.

The pso algorithm showed mixed results, with certain configurations yielding very high quality (above 100%) solutions, while other configurations show little improvement from the starting solution at all. I am not sure exactly why this is the case.

The sa algorithm was not able to reach the optimal solution in the given number of iterations. I suspect that this is because this algorithm only considers one solution per iteration (and thus has a much quicker runtime compared to the other two algorithms). If the maximum number of iterations is increased, the performance may be comparable to the other algorithms.

This program was tested on macOS and Ubuntu, but not Windows. Should any issues arise, please try to compile and run it on a Unix server such as nightmare.cs.uct.ac.za.

Please email me at dylanfouche@gmail.com if there are any concerns or queries.


	