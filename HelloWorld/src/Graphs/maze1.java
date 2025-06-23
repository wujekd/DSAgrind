package Graphs;
import java.util.List;
import Graphs.UnweightedGraph.SearchTree;


public class maze1 {

	public static void main(String[] args) {

		UnweightedGraph<Integer> graph = createMaze(3,3);
		
		graph.addEdge(0,3);
		graph.addEdge(3, 4);
		graph.addEdge(4, 1);
		graph.addEdge(1, 2);
		graph.addEdge(2, 5);
		graph.addEdge(5, 8);
		graph.addEdge(4, 7);
		graph.addEdge(7, 6);
		
		
		System.out.println(graph.getVertices());
		
		for (List<Edge> edgeArray : graph.getEdges()) {
			if (edgeArray.isEmpty()) {
				System.out.print("[] ");
				
			} else {
				// open [
				for (Edge e : edgeArray) {
					System.out.printf(" " + e.u + "-" + e.v + " ");
				}
				//close ]
			}
		}
		
		
		SearchTree tree = graph.bfs(0);
		System.out.println("");
		System.out.println(tree.getSearchOrder());
		System.out.println(tree.getPath(6));
	}
	
	
	
	private static UnweightedGraph<Integer> createMaze(int x, int y) {
		
		UnweightedGraph<Integer> graph = new UnweightedGraph<>(); 
		
		for (int i = 0; i < (x * y); i++) {
			graph.addVertex(i);
		}
		return graph;
	}
}