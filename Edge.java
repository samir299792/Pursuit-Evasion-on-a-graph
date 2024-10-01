/**
 * Author: Samir Bikram Dhami
 * Filename: Edge.java
 * Class: CS231A
 * Date: 29th April, 2024.
 * Purpose: This file represents an edge class.
 */

 public class Edge {
    
    private Vertex firstVertex; // One endpoint of the edge
    private Vertex secondVertex; // The other endpoint of the edge
    private double distance; // The distance or weight between the two vertices

    /**
     * Constructs an edge between two vertices with a specified distance.
     * 
     * @param u        the first vertex the edge connects to
     * @param v        the second vertex the edge connects to
     * @param distance the weight or distance between the two vertices
     */
    public Edge(Vertex u, Vertex v, double distance) {
        this.firstVertex = u;
        this.secondVertex = v;
        this.distance = distance;
    }

    /**
     * Returns the distance or weight of this edge.
     * 
     * @return the distance between the vertices
     */
    public double distance() {
        return distance;
    }

    /**
     * Determines the opposite vertex connected by this edge to a given vertex.
     * 
     * @param vertex the vertex from which to find the opposite
     * @return the opposite vertex connected by this edge
     */
    public Vertex other(Vertex vertex) {
        return vertex.equals(firstVertex) ? secondVertex : firstVertex;
    }

    /**
     * Provides both vertices that this edge connects as an array.
     * 
     * @return an array containing both vertices
     */
    public Vertex[] vertices() {
        return new Vertex[] {firstVertex, secondVertex};
    }

    /**
     * Retrieves the neighboring vertex of a specified vertex on this edge.
     * 
     * @param v the vertex whose neighbor is to be found
     * @return the neighboring vertex on this edge
     */
    public Vertex getNeighbor(Vertex v) {
        return v.equals(firstVertex) ? secondVertex : firstVertex;
    }
}
