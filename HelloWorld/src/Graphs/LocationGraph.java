package Graphs;

import java.util.List;

public class LocationGraph {

	public static void main(String[] args) {
		LocatedNode[] nodes = {
				new LocatedNode(0,1,2),
				new LocatedNode(1,3,4),
				new LocatedNode(2,4,2),
				new LocatedNode(3,3,7),
				new LocatedNode(4,8,4),
				new LocatedNode(5,7,8)
		};
		int[][] edges = {
				  {0,1}, {0,2}, {1,3}, {1,4},
				  {2,4}, {3,5}, {5,4}
				};
		
		UnweightedGraph<LocatedNode> graph = new UnweightedGraph<>(nodes, edges);
		
		
		for (List<Edge> list : graph.getEdges()) {
			for (Edge edge : list) {
				System.out.println(edge);
			}
		}
	}
}
