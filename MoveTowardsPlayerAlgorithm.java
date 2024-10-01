/**
 * Author: Samir Bikram Dhami
 * Filename: MoveTowardsPlayerAlgorithm.java
 * Class: CS231A
 * Date: 13th May, 2024.
 * Purpose: This file represents a MoveTowardsPlayerAlgorithm.
 */

import java.util.ArrayList;
import java.util.Random;

/**
 * A class representing a player algorithm that always tries to move towards the other player.
 */
public class MoveTowardsPlayerAlgorithm extends AbstractPlayerAlgorithm {

    private Random rand = new Random();

    /**
     * Constructs a MoveTowardsPlayerAlgorithm with the given graph.
     * @param graph The graph on which the player will play.
     */
    public MoveTowardsPlayerAlgorithm(Graph graph) {
        super(graph);
    }

    /**
     * Chooses a starting vertex randomly from the graph.
     * @return The chosen starting vertex.
     */
    @Override
    public Vertex chooseStart() {
        ArrayList<Vertex> vertices = this.getGraph().getVertices();
        return this.setCurrentVertexAndGet(vertices.get(rand.nextInt(vertices.size())));
    }

    /**
     * Chooses a starting vertex based on the other player's position.
     * @param other The other player's position.
     * @return The chosen starting vertex.
     */
    @Override
    public Vertex chooseStart(Vertex other) {
        return this.setCurrentVertexAndGet(other);
    }

    /**
     * Chooses the next vertex to move to, aiming to minimize the distance to the other player.
     * @param otherPlayer The position of the other player.
     * @return The chosen next vertex to move to.
     */
    @Override
    public Vertex chooseNext(Vertex otherPlayer) {
        ArrayList<Vertex> neighbors = getCurrentVertex().adjacentVertices();
        Vertex targetVertex = getCurrentVertex(); // Start by assuming stay in place
        double minDist = Double.POSITIVE_INFINITY;

        for (Vertex neighbor : neighbors) {
            if (neighbor.equals(getGraph().getVertices().get(0))) continue; // Skip the current vertex
            
            double dist = getGraph().distanceFrom(otherPlayer).get(neighbor);
            if (dist < minDist) {
                minDist = dist;
                targetVertex = neighbor;
            } else if (dist == minDist && rand.nextBoolean()) {
                targetVertex = neighbor;
            }
        }

        setCurrentVertex(targetVertex);
        return targetVertex;
    }

    /**
     * Sets the current vertex and returns it.
     * @param vertex The vertex to set as current.
     * @return The current vertex.
     */
    private Vertex setCurrentVertexAndGet(Vertex vertex) {
        this.current = vertex;
        return vertex;
    }
}
