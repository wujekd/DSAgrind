package Graphs;

class CostNode implements Comparable<CostNode> {
    int vertex;
    double cost;

    public CostNode(int vertex, double cost) {
        this.vertex = vertex;
        this.cost = cost;
    }

    @Override
    public int compareTo(CostNode other) {
        return Double.compare(this.cost, other.cost);
    }
}