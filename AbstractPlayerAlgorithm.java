/**
 * Author: Samir Bikram Dhami
 * Filename: AbstractPlayerAlgorithm.java
 * Class: CS231A
 * Date: 29th April, 2024.
 * Purpose: This file defines an abstract class for player algorithms in a graph-based game.
 */

 public abstract class AbstractPlayerAlgorithm {

    protected Graph graph; // The graph on which the game is played
    protected Vertex current; // The current position of the player on the graph

    /**
     * Constructor that initializes the player algorithm with a given graph.
     * 
     * @param graph The graph on which the game will be played.
     */
    public AbstractPlayerAlgorithm(Graph graph){
        this.graph = graph;
        this.current = null;
    }

    /**
     * Retrieves the graph associated with this player.
     * 
     * @return The graph associated with this player.
     */
    public Graph getGraph() {
        return graph;
    }

    /**
     * Retrieves the current position of the player on the graph.
     * 
     * @return The current vertex of the player.
     */
    public Vertex getCurrentVertex() {
        return current;
    }

    /**
     * Sets the current position of the player to a specified vertex.
     * 
     * @param vertex The new current vertex of the player.
     */
    public void setCurrentVertex(Vertex vertex) {
        this.current = vertex;
    }

    /**
     * Determines the initial vertex from which the player will start the game.
     * This method needs to be implemented by subclasses to define starting strategy.
     * 
     * @return The chosen starting vertex.
     */
    public abstract Vertex chooseStart();

    /**
     * Determines the starting vertex based on the initial choice of another player.
     * This method needs to be implemented by subclasses to adapt the start based on another player's choice.
     * 
     * @param other The starting vertex chosen by the other player.
     * @return The starting vertex chosen by this algorithm.
     */
    public abstract Vertex chooseStart(Vertex other);

    /**
     * Chooses the next vertex to move to based on the current position of another player.
     * This method must be implemented by subclasses to define the movement strategy during the game.
     * 
     * @param otherPlayer The current vertex of the other player.
     * @return The vertex chosen by this algorithm to move to.
     */
    public abstract Vertex chooseNext(Vertex otherPlayer);
}
