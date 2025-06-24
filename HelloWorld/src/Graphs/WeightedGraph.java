package Graphs;

import java.util.ArrayList;
import java.util.List;

public class WeightedGraph<V> extends UnweightedGraph<V> {
	
	
	//construct empty
	public WeightedGraph() {
	}
	
	public WeightedGraph(V[] vertices, int[][] edges) {
		createWeightedGraph(java.util.Arrays.asList(vertices, edges));
	}
	public WeightedGraph(int[][] edges, int numberOfVertices) {
		List<V> vertices = new ArrayList<>();
		for (int i = 0; i < numberOfVertices; i++) {
			vertices.add((V)(Integer.valueOf(i)));
		}
		createWeightedGraph(vertices, edges);
	}
		
	
	
	public WeightedGraph(List<WeightedEdge> edges, int numberOfVertices) {
		
	}
	
	public createWeightedGraph(List<V> vertices, List<WeightedEdge> edges) {
		
	}
	
	
	public createWeightedGraph(List<V> vertices, List<WeightedEdge> edges) {
	}
	

}
