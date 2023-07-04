import java.util.ArrayList;

public class Vertice {
    private String data;
    private ArrayList<Edge> edges;

    public Vertice(String data) {
        this.data = data;
        this.edges = new ArrayList<>();
    }

    public void addEdge(Vertice finalVertice, int weight) {
        this.edges.removeIf(edge -> edge.getFinalVertice().equals(finalVertice));
        this.edges.add(new Edge(this, finalVertice, weight));
    }

    public void removeEdge(Vertice finalVertice) {
        this.edges.removeIf(edge -> edge.getFinalVertice().equals(finalVertice));
    }

    public String getData() {
        return data;
    }

    public ArrayList<Edge> getEdges() {
        return edges;
    }

    public String printEdges(boolean showWeight) {
        StringBuilder message = new StringBuilder();
        if (this.edges.size() == 0) {
            message.append(this.data).append(" -->");
            return message.toString();
        }

        for (int i = 0; i < this.edges.size(); i++) {
            if (i == 0) {
                message.append(this.edges.get(i).getInitialVertice().getData()).append(" -->");
            }
            message.append(" ").append(this.edges.get(i).getFinalVertice().getData());
            if (showWeight) {
                message.append(" (").append(this.edges.get(i).getWeight()).append(")");
            }
            if (i != this.edges.size() - 1) {
                message.append(",");
            }
        }
        return message.toString();
    }

    public void removeEdgeWith(Vertice vertice) {
        // Buscar la arista que se conecta con el vértice dado y eliminarla
        Edge edgeToRemove = null;
        for (Edge edge : edges) {
            if (edge.getFinalVertice().equals(vertice)) {
                edgeToRemove = edge;
                break;
            }
        }
        if (edgeToRemove != null) {
            edges.remove(edgeToRemove);
        }
    }

}
