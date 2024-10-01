/**
 * Author: Samir Bikram Dhami
 * Filename: Exploration3.java
 * Class: CS231A
 * Date: 14th May, 2024.
 * Purpose: This file simulates third exploration
 */

public class Exploration3 {

    /**
     * main method itself
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        String filename = "exploration3graph.txt";
        Graph graph = new Graph(filename);

        Vertex startPursuer = graph.getVertices().get(2); // Start at the central vertex
        Vertex startEvader = graph.getVertices().get(3); // Start at one of the loop vertices

        AbstractPlayerAlgorithm pursuer = new MoveTowardsPlayerAlgorithm(graph);
        AbstractPlayerAlgorithm evader = new MoveAwayPlayerAlgorithm(graph);

        pursuer.setCurrentVertex(startPursuer);
        evader.setCurrentVertex(startEvader);

        GraphDisplay display = new GraphDisplay(graph, pursuer, evader, 40);
        display.repaint();

        while (!pursuer.getCurrentVertex().equals(evader.getCurrentVertex())) {
            Thread.sleep(1000); // Slow down the simulation for better observation

            // Evader moves first
            evader.chooseNext(pursuer.getCurrentVertex());
            display.repaint();
            if (pursuer.getCurrentVertex().equals(evader.getCurrentVertex())) {
                break; // Check if pursuer catches evader after evader's move
            }

            Thread.sleep(1000); // Pause before the pursuer moves

            // Pursuer moves second
            pursuer.chooseNext(evader.getCurrentVertex());
            display.repaint();
            if (pursuer.getCurrentVertex().equals(evader.getCurrentVertex())) {
                break; // Check again in case pursuer catches evader
            }
        }

        if (pursuer.getCurrentVertex().equals(evader.getCurrentVertex())) {
            System.out.println("Pursuer has caught the evader.");
        } else {
            System.out.println("Evader has escaped.");
        }
    }
}