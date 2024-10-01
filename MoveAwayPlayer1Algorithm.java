/**
 * Author: Samir Bikram Dhami
 * Filename: MoveAwayPlayer1Algorithm.java
 * Class: CS231A
 * Date: 13th May, 2024.
 * Purpose: This file represents a MoveAwayPlayer1Algorithm as part of the extension.
 */

 import java.util.ArrayList;
 import java.util.Random;
 
 /**
  * A class representing a player algorithm that tries to move away from the other player using a weighted scoring system.
  */
 public class MoveAwayPlayer1Algorithm extends AbstractPlayerAlgorithm {
 
     private Random rand = new Random();
 
     /**
      * Constructs a MoveAwayPlayer1Algorithm with the given graph.
      * @param graph The graph on which the player will play.
      */
     public MoveAwayPlayer1Algorithm(Graph graph) {
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
         return this.setCurrentVertexAndGet(selectFurthestVertex(getGraph().getVertices(), other));
     }
 
     /**
      * Chooses the next vertex to move to, aiming to maximize a weighted score based on distance, mobility, and foresight.
      * @param otherPlayer The position of the other player.
      * @return The chosen next vertex to move to.
      */
     @Override
     public Vertex chooseNext(Vertex otherPlayer) {
         ArrayList<Vertex> neighbors = getCurrentVertex().adjacentVertices();
         Vertex bestVertex = null;
         double maxScore = Double.NEGATIVE_INFINITY;
 
         for (Vertex neighbor : neighbors) {
             double score = evaluatePosition(neighbor, otherPlayer);
             if (score > maxScore) {
                 maxScore = score;
                 bestVertex = neighbor;
             } else if (score == maxScore && rand.nextBoolean()) {
                 bestVertex = neighbor;
             }
         }
 
         setCurrentVertex(bestVertex != null ? bestVertex : getCurrentVertex()); // Stay in place if no better option
         return getCurrentVertex();
     }
 
     /**
      * Evaluates the score of a candidate vertex based on distance, mobility, and foresight.
      * @param candidate The candidate vertex.
      * @param pursuer The position of the pursuer.
      * @return The score of the candidate vertex.
      */
     private double evaluatePosition(Vertex candidate, Vertex pursuer) {
         double distanceScore = getGraph().distanceFrom(pursuer).get(candidate);
         int mobilityScore = candidate.adjacentVertices().size();
         double foresightScore = calculateForesightScore(candidate, pursuer);
 
         return distanceScore * 0.5 + mobilityScore * 0.3 + foresightScore * 0.2; // Weighted score
     }
 
     /**
      * Calculates the foresight score of a candidate vertex.
      * @param candidate The candidate vertex.
      * @param pursuer The position of the pursuer.
      * @return The foresight score of the candidate vertex.
      */
     private double calculateForesightScore(Vertex candidate, Vertex pursuer) {
         double score = 0;
         ArrayList<Vertex> possiblePursuerMoves = pursuer.adjacentVertices();
         for (Vertex futurePos : possiblePursuerMoves) {
             double dist = getGraph().distanceFrom(futurePos).get(candidate);
             score += dist;
         }
         return score / possiblePursuerMoves.size();
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
     private Vertex selectFurthestVertex(ArrayList<Vertex> vertices, Vertex fromVertex) {
         Vertex result = null;
         double maxDist = Double.NEGATIVE_INFINITY;
         for (Vertex v : vertices) {
             double dist = getGraph().distanceFrom(fromVertex).get(v);
             if (dist > maxDist) {
                 maxDist = dist;
                 result = v;
             }
         }
         return result;
     }
 }
 