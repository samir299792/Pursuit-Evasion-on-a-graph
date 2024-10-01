/**
 * Author: Samir Bikram Dhami
 * Filename: MoveTowardsPlayer1Algorithm.java
 * Class: CS231A
 * Date: 13th May, 2024.
 * Purpose: This file represents a MoveTowardsPlayer1Algorithm as part of the extension.
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * A class representing a player algorithm that tries to move towards the other player by considering shortest path distance.
 */
public class MoveTowardsPlayer1Algorithm extends AbstractPlayerAlgorithm {

    private Random random = new Random();
    private HashMap<Vertex, Integer> visitCounts; // Tracks visits to each vertex

    /**
     * Constructs a MoveTowardsPlayer1Algorithm with the given graph.
     * @param graph The graph on which the player will play.
     */
    public MoveTowardsPlayer1Algorithm(Graph graph) {
        super(graph);
        this.visitCounts = new HashMap<>();
    }

    /**
     * Chooses a starting vertex with high connectivity to maximize initial options.
     * @return The chosen starting vertex.
     */
    @Override
    public Vertex chooseStart() {
        Vertex maxDegreeVertex = null;
        int maxDegree = -1;
        for (Vertex v : this.getGraph().getVertices()) {
            int currentDegree = v.degree();
            if (currentDegree > maxDegree) {
                maxDegree = currentDegree;
                maxDegreeVertex = v;
            }
        }
        setCurrentVertex(maxDegreeVertex);
        return maxDegreeVertex;
    }

    /**
     * Chooses a starting vertex as close as possible to the other player.
     * @param other The position of the other player.
     * @return The chosen starting vertex.
     */
    @Override
    public Vertex chooseStart(Vertex other) {
        setCurrentVertex(other);
        return other;
    }

    /**
     * Chooses the next vertex to move to based on shortest path distance to the other player.
     * @param otherPlayer The position of the other player.
     * @return The chosen next vertex to move to.
     */
    @Override
    public Vertex chooseNext(Vertex otherPlayer) {
        ArrayList<Vertex> neighbors = getCurrentVertex().adjacentVertices();
        Vertex bestVertex = null;
        double minDistance = Double.MAX_VALUE;

        for (Vertex neighbor : neighbors) {
            double pathDistance = shortestPathDistance(neighbor, otherPlayer);
            if (pathDistance < minDistance) {
                minDistance = pathDistance;
                bestVertex = neighbor;
            }
        }

        if (bestVertex != null) {
            visitCounts.put(bestVertex, visitCounts.getOrDefault(bestVertex, 0) + 1);
            setCurrentVertex(bestVertex);
        }

        return bestVertex;
    }

    /**
     * Calculates the shortest path distance between two vertices.
     * @param start The starting vertex.
     * @param goal The goal vertex.
     * @return The shortest path distance between the two vertices.
     */
    private double shortestPathDistance(Vertex start, Vertex goal) {
        // Placeholder for a real shortest path calculation (e.g., Dijkstra's algorithm)
        // Currently returns a direct simple estimate based on graph's direct distance value for simplicity.
        return this.getGraph().distanceFrom(start).get(goal);
    }

    /**
     * Sets the current vertex.
     * @param vertex The vertex to set as current.
     */
    public void setCurrentVertex(Vertex vertex) {
        this.current = vertex;
    }
}
