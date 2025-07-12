package Graphs;
import java.util.*;
public class UnweightedGraph<V> {
	
	protected List<V> vertices = new ArrayList<>();
	protected List<List<Edge>> neighbors = new ArrayList<>();
	
	
	//construct empty
	protected UnweightedGraph() {
		
	}
	
	//construct from arrays
	protected UnweightedGraph(V[] vertices, int[][] edges) {
		for (int i = 0; i < vertices.length; i++) {
			addVertex(vertices[i]);
		}
		
		createAdjencyLists(edges, vertices.length);
		
	}
	
	// construct from lists
	protected UnweightedGraph(List<V> vertices, List<Edge> edges) {
		for (int i =0; i < vertices.size(); i++) {
			addVertex(vertices.get(i));
		}
	}
	

	private void createAdjencyLists(int[][] edges, int numberOfVertices) {
		for (int i = 0; i < edges.length; i++) {
			addEdge(edges[i][0], edges[i][1]);
		}
	}


//	private void createAdjencyLists(List<Edge> edges, int numberOfVertices) {
//		for (Edge edge: edges) {
//			addEdge(edge.u, edge.v);
//		}
//	}

	public boolean addVertex(V v) {
		
		if (!vertices.contains(v)) {
			vertices.add(v);
			neighbors.add(new ArrayList<Edge>());
			
			return true;
		} else {
			return false;
		}
	}
	
	public boolean addEdge(int u, int v) {
		return addEdge(new Edge(u,v));
	}
	
	public boolean addEdge(Edge e) {
		if (e.u < 0 || e.u > getSize() -1) {
			throw new IllegalArgumentException("no such index" + e.u);
		}
		if (e.v < 0 || e.v > getSize() -1 ) {
			throw new IllegalArgumentException("no such index" + e.v);
		}
		if (!neighbors.get(e.u).contains(e)) {
			neighbors.get(e.u).add(e);
			return true;
		}
		return false;
	}
	
	
// Depth first search
	public SearchTree dfs(int v) {
		List<Integer> searchOrder = new ArrayList<>();
		int[] parent = new int[vertices.size()];
		for(int i =0; i < parent.length; i++) {
			parent[i] = -1; // populate init value
		}
		
		boolean[] isVisited = new boolean[vertices.size()];
		
		dfs(v, parent, searchOrder, isVisited);
		
		return new SearchTree(v, parent, searchOrder);
	}
	private void dfs(int v, int[] parent, List<Integer> searchOrder, boolean[] isVisited) {
		searchOrder.add(v);
		isVisited[v] = true;
		
		for(Edge e : neighbors.get(v)) {
			int w = e.v;
			if (!isVisited[w]){
				parent[w] = v;
				dfs(w, parent, searchOrder, isVisited);
			}
		}
	}
	
	
	public SearchTree bfs(int v) {
		List<Integer> searchOrder = new ArrayList<>();
		int[] parent = new int[vertices.size()];
		for (int i = 0; i < parent.length; i++) {
			parent[i] = -1;
		}
		
		java.util.LinkedList<Integer> queue = new java.util.LinkedList<>();
		boolean[] isVisited = new boolean[vertices.size()];
		queue.offer(v);
		isVisited[v] = true;
		
		while (!queue.isEmpty()) {
			int u = queue.poll();
			searchOrder.add(u);
			for (Edge e : neighbors.get(u)) {
				int w = e.v;
				if(!isVisited[w]) {
					queue.offer(w);
					parent[w] = u;
					isVisited[w] = true;
				}
			}
		}
		return new SearchTree(v, parent, searchOrder);
	}
	

	public int getSize() {
		return vertices.size();
	}
	public List<V> getVertices() {
		return vertices;
	}
	public List<List<Edge>> getEdges(){
		return neighbors;
	}
	
	public void printEdges() {
		for (List<Edge> list : neighbors) {
			for (Edge edge : list) {
				System.out.println(edge);
			}
		}
	}
	
	
	public class SearchTree {
		private int root;
		private int[] parent;
		private List<Integer> searchOrder;
		
		public SearchTree(int root, int[] parent, List<Integer> searchOrder) {
			this.setRoot(root);
			this.setParent(parent);
			this.setSearchOrder(searchOrder);
		}
		
		public List<Integer> getPath(int exit) {
			List<Integer> path = new ArrayList<>();
			int current = exit;
			
			while (current != -1) {
				path.add(0, current);
				current = parent[current];
			}
			
			return path;
		}
		

		public int[] getParent() {
			return parent;
		}

		public void setParent(int[] parent) {
			this.parent = parent;
		}

		public List<Integer> getSearchOrder() {
			return searchOrder;
		}

		public void setSearchOrder(List<Integer> searchOrder) {
			this.searchOrder = searchOrder;
		}

		public int getRoot() {
			return root;
		}

		public void setRoot(int root) {
			this.root = root;
		}
	}
}