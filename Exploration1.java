/**
 * Author: Samir Bikram Dhami
 * Filename: Exploration1.java
 * Class: CS231A
 * Date: 29th April, 2024.
 * Purpose: This file represents the first exploration.
 */

 import java.util.HashMap;

 public class Exploration1 {
 
     /**
      * Simulates a single game of pursuit-evasion on a graph defined by number of vertices and edge probability.
      * @param n Number of vertices in the graph.
      * @param p Probability of an edge existing between any two vertices.
      * @return A HashMap containing results of the game including if the evader was caught and the number of steps it took.
      */
     public static HashMap<String, Object> runGame(int n, double p) {
         Graph graph = new Graph(n, p);
         AbstractPlayerAlgorithm pursuer = new MoveTowardsPlayerAlgorithm(graph);
         AbstractPlayerAlgorithm evader = new MoveAwayPlayerAlgorithm(graph);
         
         pursuer.setCurrentVertex(pursuer.chooseStart());
         evader.setCurrentVertex(evader.chooseStart(pursuer.getCurrentVertex()));
 
         int steps = 0;
         while (!pursuer.getCurrentVertex().equals(evader.getCurrentVertex()) && steps < 1000) {
             pursuer.setCurrentVertex(pursuer.chooseNext(evader.getCurrentVertex()));
             if (pursuer.getCurrentVertex().equals(evader.getCurrentVertex())) {
                 break;
             }
             evader.setCurrentVertex(evader.chooseNext(pursuer.getCurrentVertex()));
             steps++;
         }
 
         HashMap<String, Object> result = new HashMap<>();
         result.put("caught", pursuer.getCurrentVertex().equals(evader.getCurrentVertex()));
         result.put("steps", steps);
         return result;
     }
 
     /**
      * Main method to run simulations across different graph sizes and edge probabilities.
      * Outputs the capture rate and average number of steps per game configuration.
      */
     public static void main(String[] args) {
         int[] sizes = {10, 15, 20}; // Different graph sizes
         double[] probabilities = {0.2, 0.3, 0.5}; // Different probabilities of edges
         int runsPerConfig = 100; // Number of simulations per configuration
 
         for (int size : sizes) {
             for (double probability : probabilities) {
                 int totalSteps = 0;
                 int captures = 0;
                 for (int i = 0; i < runsPerConfig; i++) {
                     HashMap<String, Object> result = runGame(size, probability);
                     totalSteps += (int) result.get("steps");
                     if ((boolean) result.get("caught")) {
                         captures++;
                     }
                 }
                 double averageSteps = totalSteps / (double) runsPerConfig;
                 double captureRate = (captures / (double) runsPerConfig) * 100;
 
                 System.out.println("Size: " + size + ", Probability: " + probability);
                 System.out.println("Capture Rate: " + captureRate + "%, Average Steps: " + averageSteps);
             }
         }
     }
 }