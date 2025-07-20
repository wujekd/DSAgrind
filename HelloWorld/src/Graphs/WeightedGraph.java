package Graphs;

import java.util.ArrayList;
import java.util.List;

import Graphs.UnweightedGraph.SearchTree;

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
	
	// create weighted graph from Located Nodes
	@SuppressWarnings("unchecked")
	public WeightedGraph(int[][] edges, LocatedNode[] nodes){
		List<LocatedNode> nodeList = new ArrayList<>();
		for (int i = 0; i < nodes.length; i++) {
			nodeList.add(nodes[i]);
		}
		int[][] weightedEdges = new int[edges.length][3];
		
		double dx;
		double dy;
		LocatedNode nodeA;
		LocatedNode nodeB;
		
		for (int i = 0; i < edges.length; i++) {
			nodeA = nodeList.get(edges[i][0]);
			nodeB = nodeList.get(edges[i][1]);
			dx = nodeA.x - nodeB.x;
			dy = nodeA.y - nodeB.y;
			int distance = (int) Math.sqrt(dx * dx + dy * dy);
			
			weightedEdges[i] = new int[] {nodeA.index, nodeB.index, distance};
		}
		createWeightedGraph((List<V>) nodeList, weightedEdges);
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
	
	
	// MST MST MST MST 
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
		
		List<Integer> T = new ArrayList<>(); // tracking discovered nodes
		
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
	
	// apparently refined Prims algorithm:
	// while (size of T < n){
	//	find u not in T with the smallest cost[u]
	//	add u to T
	//	for (each v not in T and (u,v) in E){
	//		if (cost[v] > w(u, v)){
	//			cost[v] = w(u, v); parent[v] = u;
	//		}
	//	}
	// }
	
	
	// Dijakstra's
	public ShortestPathTree getShortestPath(int sourceVertex) {
		// setting cost stuff
		double[] cost = new double[getSize()]; // combined cost to get to each node from the source
		for (int i = 0; i < cost.length; i++) {
			cost[i] = Double.POSITIVE_INFINITY;
		}
		cost[sourceVertex] = 0;
		
		//setting the parent tracking to recreate the path
		int[] parent = new int[getSize()];
		parent[sourceVertex] = -1; // mark the source vertex in the array
		
		List<Integer> T = new ArrayList<>();
		
		while (T.size() < getSize()) {
			// find the smallest cost u in V - T (which node to move to)
			int u = -1;
			double currentMinCost = Double.POSITIVE_INFINITY;
			for (int i = 0; i < getSize(); i++) {
				if (!T.contains(i) &&  cost[i] < currentMinCost) {
					currentMinCost = cost[i];
					u=i;
				}
			}
			if (u == -1) break; else T.add(u); //add newly explored V to T
			
			// adjust cost[v] for each v that is adjacent to u
			for(Edge e : neighbors.get(u)) {
				if(!T.contains(e.v) && cost[e.v] > cost[u] + ((WeightedEdge)e).weight) {
					cost[e.v] = cost[u] + ((WeightedEdge)e).weight;
					parent[e.v] = u;
				}
			}
		} // end of while
		return new ShortestPathTree(sourceVertex, parent, T, cost);
	}
	
	
	public void makeUndirected() { // create reverse edge for all edges
		
		for (List<Edge> list : neighbors) {
			List<Edge> snapshot = new ArrayList<>(list);
			
			for (Edge edge : snapshot) {
				boolean found = false;
				
				for (Edge reverse : neighbors.get(edge.v)) {
					if (reverse.v == edge.u) {
						found = true;
						break;
					}
				}
				if (!found) {
					double weight = ((WeightedEdge)edge).getWeight();
					addEdge(edge.v, edge.u, weight);
					}
				}
			}
		}
	
	
	// RESULT TREES
	//public
	public static class MST extends SearchTree {
		private double totalWeight;
		
		public MST(int root, int[] parent, List<Integer> searchOrder, double totalWeight) {
			super(root, parent, searchOrder);
			this.totalWeight = totalWeight;
			
		}
		public double getTotalWeight() {
			return totalWeight;
		}
	}
	
	public static class ShortestPathTree extends SearchTree {
		private double[] cost; // cost[v] = cost from v to source
		
		public ShortestPathTree(int source, int[] parent, List<Integer> searchOrder, double[] cost) {
			super(source, parent, searchOrder);
			this.cost = cost;
		}
		
		public double getCost(int v) {
			return cost[v];
		}
		
		public void printAllPaths() {
			System.out.println("All Shortest Paths:");
			for (int i=0; i < cost.length; i++) {
				System.out.println(getPath(i));
			}
		}
	}
}
