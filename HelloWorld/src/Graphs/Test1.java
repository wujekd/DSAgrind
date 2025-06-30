package Graphs;
import java.util.Arrays;
import java.util.List;

import Graphs.UnweightedGraph.SearchTree;

public class Test1 {

	public static void main(String[] args) {

		UnweightedGraph<Integer> graph = new UnweightedGraph<>();

		graph.addVertex(0);
		graph.addVertex(1);
		graph.addVertex(2);
		graph.addVertex(3); 
		graph.addVertex(4);
// Add edges
		graph.addEdge(0, 1);
		graph.addEdge(0, 2);
		graph.addEdge(1, 3);
		graph.addEdge(2, 4);
		
//		System.out.println(graph.getVertices());

		// DFS from vertex 0 (index 0)
		 SearchTree tree = graph.dfs(0);
		 
//		SearchTree tree = graph.bfs(0);
		
		//System.out.println(tree.getSearchOrder());
		 
		 System.out.println(graph.getVertices());
		System.out.println("Search order: " + tree.getSearchOrder());
		System.out.println("Parent array: " + Arrays.toString(tree.getParent()));
		
	}
}