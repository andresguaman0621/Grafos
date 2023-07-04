import java.util.ArrayList;
public class Edge {
    private Vertice initialVertice;
    private Vertice finalVertice;
    private int weight;
    public Edge(Vertice initialVertice, Vertice finalVertice, int weight) {
        this.initialVertice = initialVertice;
        this.finalVertice = finalVertice;
        this.weight = weight;
    }

    public Vertice getInitialVertice() {
        return initialVertice;
    }

    public Vertice getFinalVertice() {
        return finalVertice;
    }

    public int getWeight() {
        return weight;
    }
}
