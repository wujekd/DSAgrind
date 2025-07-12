package Graphs;

public class Edge {
	
	public int u;
	public int v;
	
	public Edge(int u, int v) {
		this.u = u;
		this.v = v;
	}
	
	public boolean equals(Object o) { //overwrite method of Object
		return u == ((Edge)o).u && v ==((Edge)o).v;		 // type cast
	}
	
	@Override
	public String toString() {
		return "from: " + u + " to: " + v;
	}
	
}
