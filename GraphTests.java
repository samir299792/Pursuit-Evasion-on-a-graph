/**
 * Author: Samir Bikram Dhami
 * Filename: GraphTests.java
 * Class: CS231A
 * Date: 29th April, 2024.
 * Purpose: This file tests the Graph class.
 */

public class GraphTests {
    public static void testGraphFromFile() {
        Graph graph = new Graph("graph.txt");
        System.out.println("Test Graph From File:");
        System.out.println("Number of vertices: " + graph.size());
        System.out.println("Number of edges: " + graph.getEdges().size());
        for (Edge edge : graph.getEdges()) {
            System.out.println("Edge between " + edge.vertices()[0] + " and " + edge.vertices()[1] + " with distance " + edge.distance());
        }
    }

    public static void testRandomGraphConstruction(int n, double p) {
        Graph graph = new Graph(n, p);
        int possibleEdges = n * (n - 1) / 2;
        int actualEdges = graph.getEdges().size();
        double observedP = (double) actualEdges / possibleEdges;
    
        System.out.println("Test Random Graph Construction:");
        System.out.println("Number of vertices: " + graph.size());
        System.out.println("Number of edges: " + actualEdges);
        System.out.println("Expected edges: " + (int)(possibleEdges * p));
        System.out.println("Observed probability: " + observedP);
        System.out.println("Within expected range: " + (Math.abs(observedP - p) < 0.05 ? "Yes" : "No"));
    }

    public static void main(String[] args) {
        testGraphFromFile(); // Ensure that "graph1.txt" exists and is formatted correctly.
        testRandomGraphConstruction(10, 0.3); // Test with 10 vertices and 30% probability of edge creation.
    }
}
