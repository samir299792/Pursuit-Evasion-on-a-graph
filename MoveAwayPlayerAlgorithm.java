/**
 * Author: Samir Bikram Dhami
 * Filename: MoveAwayPlayerAlgorithm.java
 * Class: CS231A
 * Date: 13th May, 2024.
 * Purpose: This file represents a MoveAwayPlayerAlgorithm.
 */

import java.util.ArrayList;
import java.util.Random;

/**
 * A class representing a player algorithm that always tries to move away from the other player.
 */
public class MoveAwayPlayerAlgorithm extends AbstractPlayerAlgorithm {

    private Random rand = new Random();

    /**
     * Constructs a MoveAwayPlayerAlgorithm with the given graph.
     * @param graph The graph on which the player will play.
     */
    public MoveAwayPlayerAlgorithm(Graph graph) {
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
     * Chooses a starting vertex as far away as possible from the other player.
     * @param other The position of the other player.
     * @return The chosen starting vertex.
     */
    @Override
    public Vertex chooseStart(Vertex other) {
        return this.setCurrentVertexAndGet(selectMaxDistanceVertex(getGraph().getVertices(), other));
    }

    /**
     * Chooses the next vertex to move to, aiming to maximize distance from the other player.
     * @param otherPlayer The position of the other player.
     * @return The chosen next vertex to move to.
     */
    @Override
    public Vertex chooseNext(Vertex otherPlayer) {
        ArrayList<Vertex> neighbors = getCurrentVertex().adjacentVertices();
        Vertex targetVertex = selectMaxDistanceVertex(neighbors, otherPlayer);
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

    /**
     * Selects the vertex with maximum distance from a given set of vertices to a specified vertex.
     * @param vertices The set of vertices to choose from.
     * @param fromVertex The vertex from which distances are measured.
     * @return The vertex with maximum distance from the specified vertex.
     */
    private Vertex selectMaxDistanceVertex(ArrayList<Vertex> vertices, Vertex fromVertex) {
        Vertex result = getCurrentVertex();
        double maxDist = Double.NEGATIVE_INFINITY;
        for (Vertex v : vertices) {
            double dist = getGraph().distanceFrom(fromVertex).get(v);
            if (dist > maxDist) {
                maxDist = dist;
                result = v;
            } else if (dist == maxDist && rand.nextBoolean()) {
                result = v;
            }
        }
        return result;
    }
}
