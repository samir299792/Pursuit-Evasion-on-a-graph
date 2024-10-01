/**
 * Author: Samir Bikram Dhami
 * Filename: Graph.java
 * Class: CS231A
 * Date: 29th April, 2024.
 * Purpose: This file represents a graph class.
 */

import java.util.ArrayList;
import java.util.Random;
import java.util.Comparator;
import java.io.* ;

public class Graph {

    private ArrayList<Vertex> vertices; // Stores vertices of the graph
    private ArrayList<Edge> edges; // Stores edges of the graph

    /**
     * Constructs an empty graph.
     */
    public Graph() {
        this(0);
    }

    /**
     * Constructs a graph with a specified number of vertices and no edges.
     * 
     * @param n the number of vertices
     */
    public Graph(int n) {
        this(n, 0.0);
    }

    /**
     * Constructs a graph with a specified number of vertices and randomly connects them
     * based on a given probability.
     * 
     * @param n the number of vertices
     * @param probability the probability of creating an edge between any two vertices
     */
    public Graph(int n, double probability) {
        Random random = new Random();
        vertices = new ArrayList<>();
        edges = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            Vertex vertex = addVertex();
            for (Vertex otherVertex : vertices) {
                if (random.nextDouble() < probability) {
                    addEdge(vertex, otherVertex, 1);
                }
            }
        }
    }

    /* *
	 * A graph constructor that takes in a filename and builds
	 * the graph with the number of vertices and specific edges 
	 * specified.  
	 * */
	public Graph( String filename )  {

    	try {
    		//Setup for reading the file
    		FileReader fr = new FileReader(filename);
    		BufferedReader br = new BufferedReader(fr);

    		//Get the number of vertices from the file and initialize that number of verticies
			vertices = new ArrayList<Vertex>() ;
        	Integer numVertices = Integer.valueOf( br.readLine().split( ": " )[ 1 ] ) ;
			for ( int i = 0 ; i < numVertices ; i ++ ) {
				vertices.add( new Vertex() );
			}

			//Read in the edges specified by the file and create them
        	edges = new ArrayList<Edge>() ; //If you used a different data structure to store Edges, you'll need to update this line
			String header = br.readLine(); //We don't use the header, but have to read it to skip to the next line
			//Read in all the lines corresponding to edges
        	String line = br.readLine();
       		while(line != null){
       			//Parse out the index of the start and end vertices of the edge
 	           	String[] arr = line.split(",");
 	           	Integer start = Integer.valueOf( arr[ 0 ] ) ;
 	           	Integer end = Integer.valueOf( arr[ 1 ] ) ;
 	           	
 	           	//Make the edge that starts at start and ends at end with weight 1
 	           	Edge edge = new Edge( vertices.get( start ) , vertices.get( end ) , 1. ) ;
 				//Add the edge to the set of edges for each of the vertices
 				vertices.get( start ).addEdge( edge ) ;
				vertices.get( end ).addEdge( edge ) ;
				//Add the edge to the collection of edges in the graph
            	this.edges.add( edge );
            	
            	//Read the next line
            	line = br.readLine();
            }
        	// call the close method of the BufferedReader:
        	br.close();
        	System.out.println( this.edges );
      	}
      	catch(FileNotFoundException ex) {
        	System.out.println("Graph constructor:: unable to open file " + filename + ": file not found");
      	}
      	catch(IOException ex) {
        	System.out.println("Graph constructor:: error reading file " + filename);
      	}
	}
    /**
     * 
     * Returns the number of vertices in the graph.
     * 
     * @return the number of vertices in the graph
     */
    public int size() {
        return vertices.size();
    }

    /**
     * 
     * Returns a list of the vertices in the graph.
     * 
     * @return a list of the vertices in the graph
     */
    public ArrayList<Vertex> getVertices() {
        return vertices;
    }

    /**
     * 
     * Returns a list of the edges in the graph.
     * 
     * @return a list of the edges in the graph
     */
    public ArrayList<Edge> getEdges() {
        return edges;
    }

    /**
     * 
     * Adds a new vertex to the graph.
     * 
     * @return the newly added vertex
     */
    public Vertex addVertex() {
        Vertex vertex = new Vertex();
        vertices.add(vertex);
        return vertex;
    }

    /**
     * Adds the edge to the list of edges in the graph
     * 
     * @return the edge being added in the list
     */
    public Edge addEdge(Vertex u, Vertex v, double distance) {
        Edge edge = new Edge(u, v, distance);
        u.addEdge(edge);
        v.addEdge(edge);
        edges.add(edge);
        return edge;
    }

    /**
     * Gets the edge connecting the two vertices
     * 
     * @param u the first vertex
     * @param v the second vertex
     * @return the edge that connects the first and second vertex and returns null
     *         if no edge connects the vertices
     */
    public Edge getEdge(Vertex u, Vertex v) {
        Edge edge = u.getEdgeTo(v);
        return edge;
    }

    /**
     * To remove a Vertex
     * @param vertex the vertex to be removed
     * @return the removed vertex
     */
    public boolean remove(Vertex vertex) {
        for (Vertex otherVertex : vertices) {
            if (otherVertex.equals(vertex)) {
                vertices.remove(otherVertex);
                ArrayList<Vertex> neighbors = (ArrayList<Vertex>) otherVertex.adjacentVertices();
                for (Vertex neighbor : neighbors) {
                    Edge edge = otherVertex.getEdgeTo(neighbor);
                    boolean removeStatus = neighbor.removeEdge(edge);
                    return removeStatus;
                }
            }
        }
        return false;
    }

    /**
     * Removes the edge from the list of edges in the graph
     * 
     * @param edge the edge to be removed from the graph
     * @return true if edge was found and removed successfully or else returns false
     */
    public boolean remove(Edge edge) {

        Vertex[] vertices = edge.vertices();
        vertices[0].removeEdge(edge);
        vertices[1].removeEdge(edge);

        boolean removeStatus = edges.remove(edge);
        return removeStatus;
    }

    /**
     * The distance from a Vertex to all other vertices
     * @param source the source to calculate distance from
     * @return the distance
     */
    public HashMap<Vertex, Double> distanceFrom(Vertex source) {
        HashMap<Vertex, Double> hashMap = new HashMap<>();
        Comparator<Vertex> comparator = new Comparator<Vertex>() {
            @Override
            public int compare(Vertex o1, Vertex o2) {
                return hashMap.get(o1).compareTo(hashMap.get(o2));
            }
        };
        PriorityQueue<Vertex> priorityQueue = new Heap<>(comparator, false);
        for (Vertex v : vertices) {
            if (!v.equals(source)) {
                hashMap.put(v, Double.POSITIVE_INFINITY);
            } else {
                hashMap.put(source, 0.0);
            }
            priorityQueue.offer(v);
        }
        while (priorityQueue.size() != 0) {
            Vertex u = priorityQueue.poll();
            for (Vertex neighbor : u.adjacentVertices()) {
                double alt = hashMap.get(u) + u.getEdgeTo(neighbor).distance();
                if (alt < hashMap.get(neighbor)) {
                    hashMap.put(neighbor, alt);
                    priorityQueue.updatePriority(neighbor);
                }
            }
        }
        return hashMap;
    }

    
    public static void main(String[] args){
        // Create an instance of the Graph class by passing "graph.txt" as the filename
        Graph graph = new Graph("graph.txt");

        System.out.println("Vertices:");
        for (Vertex vertex : graph.getVertices()) {
            System.out.println(vertex);
        }

        System.out.println("Edges:");
        for (Edge edge : graph.getEdges()) {
            System.out.println(edge);
        }
    }
}