package Graphs;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import Graphs.UnweightedGraph.SearchTree;


public class maze1 {

	public static void main(String[] args) {

		UnweightedGraph<Integer> graph = createMazeBase(3,3);
		
		generateMaze(graph, 3, 3);
		
//		graph.addEdge(0,3);
//		graph.addEdge(3, 4);
//		graph.addEdge(4, 1);
//		graph.addEdge(1, 2);
//		graph.addEdge(2, 5);
//		graph.addEdge(5, 8);
//		graph.addEdge(4, 7);
//		graph.addEdge(7, 6);
		
		
		System.out.println(graph.getVertices());
		
		printEdges(graph.getEdges());
		
		SearchTree tree = graph.bfs(0);
		System.out.println("");
		System.out.println(tree.getSearchOrder());
		System.out.println(tree.getPath(6));
	}
	
	
	public static UnweightedGraph<Integer> createMazeBase(int x, int y) {
		
		UnweightedGraph<Integer> graph = new UnweightedGraph<>(); 
		
		for (int i = 0; i < (x * y); i++) {
			graph.addVertex(i);
		}
		return graph;
	}
	
	public static void generateMaze(UnweightedGraph<Integer> graph, int cols, int rows) {
		
		boolean visited[][] = new boolean[rows][cols];
		Random rand = new Random();
		
		generateMaze(0, 0, rows, cols, graph, visited, rand);
		
	}


	private static void generateMaze(int row, int col, int rows, int cols, UnweightedGraph<Integer> graph, boolean[][] visited, Random rand) {
	    visited[row][col] = true;

	    // Direction vectors: N, S, W, E
	    int[][] directions = {
	        {-1, 0}, // up
	        {1, 0},  // down
	        {0, -1}, // left
	        {0, 1}   // right
	    };

	    // Shuffle directions for random traversal
	    List<int[]> dirList = new ArrayList<>();
	    for (int[] d : directions) dirList.add(d);
	    java.util.Collections.shuffle(dirList, rand);

	    for (int[] dir : dirList) {
	        int newRow = row + dir[0];
	        int newCol = col + dir[1];

	        // Check bounds
	        if (newRow >= 0 && newRow < rows && newCol >= 0 && newCol < cols && !visited[newRow][newCol]) {
	            int current = row * cols + col;
	            int next = newRow * cols + newCol;

	            // Carve path (i.e., add edge in the graph)
	            graph.addEdge(current, next);
//	            graph.addEdge(next, current); // since undirected

	            // Recurse
	            generateMaze(newRow, newCol, rows, cols, graph, visited, rand);
	        }
	    }
	}

		
	
	private static boolean isValid(int r, int c, boolean[][] visited, int rows, int cols) {
	    return r >= 0 && r < rows && c >= 0 && c < cols && !visited[r][c];
	}
	
	
	public static void printEdges(List<List<Edge>> list) {
		System.out.println("-- edges --");
		for (List<Edge> edgeArray : list) {
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
		System.out.println("  ");
	}
}