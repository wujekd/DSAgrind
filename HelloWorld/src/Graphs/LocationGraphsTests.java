package Graphs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

import Graphs.UnweightedGraph.SearchTree;
import Graphs.WeightedGraph.ShortestPathTree;

public class LocationGraphsTests {

	public static void main(String[] args) {
		LocatedNode[] nodes = {
				new LocatedNode(0,1,2),
				new LocatedNode(1,3,4),
				new LocatedNode(2,4,2),
				new LocatedNode(3,3,7),
				new LocatedNode(4,8,4),
				new LocatedNode(5,7,8),
				new LocatedNode(6,3,9)
		};
		int[][] edges = {
				  {0,1}, {0,2}, {1,3}, {1,4},
				  {2,4}, {3,5}, {5,4},
				  {2,6}, {6,3}
				};
		
		UnweightedGraph<LocatedNode> graph1 = new UnweightedGraph<>(nodes, edges);
		graph1.makeUndirected();
		SearchTree resultBeFS = BeFS(graph1, nodes[2], nodes[3]);
		System.out.println("Best first Search:");
		System.out.println(resultBeFS.getPath(3));
		System.out.printf("Path weight: " + graph1.getPathWeight(resultBeFS.getPath(3)));
		
//		WeightedGraph<LocatedNode> graph2 = new WeightedGraph<>(edges, nodes);
//		graph2.makeUndirected();
//		ShortestPathTree resultDijakstras = graph2.getShortestPath(2);
//		System.out.println("Dijakstra's:");
//		System.out.println(resultDijakstras.getPath(3));
		
		

	}
	
	public static SearchTree BeFS(UnweightedGraph<LocatedNode> graph, LocatedNode start, LocatedNode goal){
		
		List<List<Edge>> neighbors = graph.getEdges();
		int n = graph.getSize();
		
		Comparator<Integer> cmp = Comparator.comparingDouble(id ->{
			LocatedNode node = graph.getVertices().get(id);
			return Math.hypot(node.x - goal.x, node.y - goal.y);
		});
		
		PriorityQueue<Integer> queue = new PriorityQueue<>(cmp);
		boolean[] visited = new boolean[n];
		int[] parent = new int[graph.getSize()];
		Arrays.fill(parent,  -1);
		List<Integer> order = new ArrayList<>();
		
		queue.add(start.index);
		
		while (!queue.isEmpty()) {
			int u = queue.poll();
			if (visited[u]) continue;
			visited[u] = true;
			order.add(u);
			if (u == goal.index) break;
			
			for (Edge edge : neighbors.get(u)) {
				int v = edge.v;
				if (!visited[v]) {
					if (parent[v] == -1) parent[v] = u;
					queue.add(v);
				}
			}
		}
		return new SearchTree(start.index, parent, order);
	}
}