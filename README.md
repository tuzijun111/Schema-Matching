# DySM

There are four algorithms for incremental schema matching. And they can be run by the main functions in the 
Experiment, Cluster and DSframe classes.

In the Experiment class, we first integrate sources from source1 to source 8 one by one and we take the integrating results as 
the initial clustering result. (This is the first algorithm, and we can call it as naive schema matching algorithm). Then we 
apply the greedy DBindex algorithm (DBGreedy, the second algorithm) on the initial clustering result. The two algorithms can be
run in the main function of the Experiment class.

In the Cluster class, the main function contains our proposed CenterCluster algorithm (the third algorithm). This algorithm is 
desinged as an incremental algorithm. So the batch algorithm is the same with its incremental algorithm.

In the DSframe Class, the main function contains the incremental algorithm for DBindex clustering (it is called IncrementalDB, 
the forth algorithm).


