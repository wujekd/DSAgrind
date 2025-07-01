package Graphs;

import Graphs.WeightedGraph.MST;
import Graphs.WeightedGraph.ShortestPathTree;

public class WeightedGraphTest {
	
	public static void main(String[] args) {
		
		int[][] edges = {
				{0,1,2},{0,3,8},{1,0,2},{1,2,7},{1,3,3},
				{2,1,7},{2,3,4},{2,4,5},{3,0,8},{3,1,3},{3,2,4},
				{3,4,6},{4,2,5},{4,3,6}
		};
		WeightedGraph<Integer> graph = new WeightedGraph<>(edges, 5);
		
		graph.printWeightedEdges();
		
		MST res = graph.getMinimumSpanningTree();

		System.out.println(res.getSearchOrder()); // hell yeah
		
		System.out.println("Dijakstras search order:");
		
		ShortestPathTree res2 = graph.getShortestPath(0);
		
		System.out.println(res2.getSearchOrder());
	}
	
}