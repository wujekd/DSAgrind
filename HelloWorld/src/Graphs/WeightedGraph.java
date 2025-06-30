package Graphs;

import java.util.ArrayList;
import java.util.List;

public class WeightedGraph<V> extends UnweightedGraph<V> {
	
	
	//construct empty
	public WeightedGraph() {
	}
	
	public WeightedGraph(V[] vertices, int[][] edges) {
		createWeightedGraph(java.util.Arrays.asList(vertices), edges);
	}
	
	public WeightedGraph(int[][] edges, int numberOfVertices) {
		List<V> vertices = new ArrayList<>();
		for (int i = 0; i < numberOfVertices; i++) {
			vertices.add((V)(Integer.valueOf(i)));
		}
		createWeightedGraph(vertices, edges);
	}
	
	public WeightedGraph(List<V> vertices, List<WeightedEdge> edges) {
		createWeightedGraph(vertices, edges);
	}
	
	@SuppressWarnings("unchecked")
	public WeightedGraph(List<WeightedEdge> edges, int numberOfVertices) {
		List<V> vertices = new ArrayList<>();
		for (int i = 0; i < numberOfVertices; i++) {
			vertices.add((V)(Integer.valueOf(i)));
		}
	}
	
	// from edge arrays
	private void createWeightedGraph(List<V> vertices, int[][] edges) {
		this.vertices = vertices;
		
		for (int i = 0; i < vertices.size(); i++) { //create list for vertices
			neighbors.add(new ArrayList<Edge>());
		}
		for (int i = 0; i < edges.length; i++) {
			neighbors.get(edges[i][0]).add(
					new WeightedEdge(edges[i][0], edges[i][1], edges[i][2])); // create weighhted edge
		}
		
	}
	
	// from edge List
	private void createWeightedGraph(List<V> vertices, List<WeightedEdge> edges) {
		this.vertices = vertices;
		
		for (int i = 0; i < vertices.size(); i++) {
			neighbors.add(new ArrayList<Edge>());
		}
		for (WeightedEdge edge : edges) {
			neighbors.get(edge.u).add(edge);
		}
	}
	
	public double getWeight(int u, int v) throws Exception {
		for (Edge edge : neighbors.get(u)) {
			if (edge.v == v) {
				return ((WeightedEdge)edge).weight;
			}
		}
		throw new Exception("Edge does not exsist");
	}
	
	public void printWeightedEdges() {
		for (int i =0; i < getSize(); i++) {
			System.out.print(vertices.get(i) + " (" + i + "): ");
			for (Edge edge : neighbors.get(i)) {
				System.out.print(" (" + edge.u + ", " + edge.v + ((WeightedEdge)edge).weight + ") "); //casting weightededge type on edge type
			}
			System.out.println("");
		}
	}
	
	public boolean addEdge(int u, int v, double weight) {
		return addEdge(new WeightedEdge(u, v, weight));
	}
	
	public MST getMinimumSpanningTree() {
		return getMinimumSpanningTree(0);
		}
	
	// get MST at specified vertex
	public MST getMinimumSpanningTree(int startingVertex) {
		double[] cost = new double[getSize()];
		for (int i = 0; i < cost.length; i++) {
			cost[i] = Double.POSITIVE_INFINITY;
		}
		
		cost[startingVertex] = 0;
		
		int[] parent = new int[getSize()];
		parent[startingVertex] = -1;
		
		double totalWeight = 0;
		
		List<Integer> T = new ArrayList<>();
		
		while(T.size() < getSize()) {
			int u = -1;
			double currentMinCost = Double.POSITIVE_INFINITY;
			for (int i = 0; i < getSize(); i++) {
				if (!T.contains(i) && cost[i] < currentMinCost){
					currentMinCost = cost[i];
					u = i;
				}
			}
			if (u == -1) break; else T.add(u);  //
			totalWeight += cost[u];
			
			for (Edge e : neighbors.get(u)) {
				if (!T.contains(e.v) && cost[e.v] > ((WeightedEdge)e).weight) {
					cost[e.v] = ((WeightedEdge)e).weight;
					parent[e.v] = u;
				}
			}
		} // end of while
		
	
		return new MST(startingVertex, parent, T, totalWeight);
	}
	
	public class MST extends SearchTree {
		private double totalWeight;
		
		public MST(int root, int[] parent, List<Integer> searchOrder, double totalWeight) {
			super(root, parent, searchOrder);
			this.totalWeight = totalWeight;
			
		}
		public double getTotalWeight() {
			return totalWeight;
		}
		
	}
	
	
}
