package Graphs;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import Graphs.WeightedGraph.ShortestPathTree;


public class MinHeapWithDijakstrasAndPrims {

	public static void main(String[] args) {

		System.out.println("testy");

	}
	
	
	public static ShortestPathTree MHDijakstras(WeightedGraph<Integer> graph, int startVert) {
		List<List<Edge>> neighbors = graph.getEdges();
		double[] cost = new double[graph.getSize()];
		int[] parent = new int[graph.getSize()];
		boolean[] visited = new boolean[graph.getSize()];
		List<Integer> T = new ArrayList<>();
		PriorityQueue<CostNode> pq = new PriorityQueue<>();
		for (int i = 0; i < graph.getSize(); i++) {
			cost[i] = Double.POSITIVE_INFINITY;	
		}
		parent[startVert] = -1;
		pq.add(new CostNode(startVert, 0));
		cost[startVert] = 0;

		
		
		while(!pq.isEmpty()) {
			CostNode current = pq.poll();
			int u = current.vertex; // vertex we currently working from
			
			if (visited[u]) continue;
			T.add(u);
			visited[u] = true;
			
			
			// Explore Neighbours
			for (Edge e : neighbors.get(u)) {
				int v = e.v; // the edge led to by current e
				double weight = ((WeightedEdge)e).weight; // the weight of the current e
				
				if (!visited[v] && cost[v] > cost[u] + weight) {
					cost[v] = cost[u] + weight;
					parent[v] = u;
					pq.add(new CostNode(v, cost[v]));
				}
			}
		}
		return new ShortestPathTree(startVert, parent, T, cost);
	}
}