package Graphs;

public class LocatedNode {
	int index;
	float x;
	float y;
	
	public LocatedNode(int index, float x, float y) {
		this.index = index;
		this.x = x;
		this.y = y;
	}
	
	@Override
	public boolean equals(Object obj) {
	    if (this == obj) return true;
	    if (!(obj instanceof LocatedNode)) return false;
	    LocatedNode other = (LocatedNode) obj;
	    return this.index == other.index;
	}

	@Override
	public int hashCode() {
	    return Integer.hashCode(index);
	}

	
	@Override
	public String toString() {
		return "id: " + index + ", x: " + x + ", y: " + y;
	}
}
