import java.util.*;

public class Grafo {

    private ArrayList<Vertice> vertices;
    private boolean isDirected;
    private boolean isWeighted;
    private Map<Vertice, Vertice> previousVertices;

    public Grafo(boolean isDirected, boolean isWeighted){
      this.vertices = new ArrayList<Vertice>();
      this.isDirected = isDirected;
      this.isWeighted = isWeighted;
  }

  public void  addVertice (String data){
      Vertice newVertice = new Vertice(data);
      this.vertices.add(newVertice);

  }

  public void addEdge(String initialVertice, String finalVertice, int weight){
      if (!this.isWeighted){
          weight = 1;
      }

      getVertexByValue(initialVertice).addEdge(getVertexByValue(finalVertice), weight);

      if (!this.isDirected){
          getVertexByValue(finalVertice).addEdge(getVertexByValue(initialVertice), weight);

      }
  }
    public boolean existeArista(String initialVertex, String finalVertex) {
        Vertice v1 = getVertexByValue(initialVertex);
        Vertice v2 = getVertexByValue(finalVertex);

        if (v1 != null && v2 != null) {
            for (Edge edge : v1.getEdges()) {
                if (edge.getFinalVertice().equals(v2)) {
                    return true; //La arista ya existe
                }
            }
        }

        return false; //La arista no existe
    }

    public void  removeEdge(Vertice initialVertice, Vertice finalVertice){
      initialVertice.removeEdge(finalVertice);
      if (!this.isDirected){
          finalVertice.removeEdge(initialVertice);
      }
  }

    public void removeVertice(Vertice vertice) {
        // Eliminar el vértice de la lista de vértices
        this.vertices.remove(vertice);

        // Eliminar las aristas que se conectan con el vértice
        for (Vertice v : this.vertices) {
            v.removeEdgeWith(vertice);
        }
    }
    public ArrayList<Vertice> getVertices(){
        return this.vertices;
    }
    public boolean isWeighted(){
        return this.isWeighted;
    }
    public boolean isDirected(){
        return this.isDirected;
    }
    public Vertice getVertexByValue(String value){
        for (Vertice v: this.vertices){
            if (v.getData().equals(value)){
                return v;
            }
        }
        return null;
    }

    public Map<Vertice, Integer> dijkstra(Vertice source) {
        Map<Vertice, Integer> distances = new HashMap<>();
        Map<Vertice, Vertice> previous = new HashMap<>();
        Map<Vertice, Integer> finalDistances = distances;
        PriorityQueue<Vertice> queue = new PriorityQueue<>(Comparator.comparingInt(v -> finalDistances.getOrDefault(v, Integer.MAX_VALUE)));

        for (Vertice v : vertices) {
            distances.put(v, Integer.MAX_VALUE);
            previous.put(v, null);
        }

        distances.put(source, 0);
        queue.offer(source);

        while (!queue.isEmpty()) {
            Vertice current = queue.poll();

            for (Edge edge : current.getEdges()) {
                Vertice neighbor = edge.getFinalVertice();
                int newDistance = distances.get(current) + edge.getWeight();

                if (newDistance < distances.get(neighbor)) {
                    distances.put(neighbor, newDistance);
                    previous.put(neighbor, current);
                    queue.offer(neighbor);
                }
            }
        }

        previousVertices = new HashMap<>();
        for (Vertice v : vertices) {
            previousVertices.put(v, previous.get(v));
        }
        // Después de completar el algoritmo Dijkstra, asigna los resultados a los atributos
        distances = new HashMap<>(distances);

        previousVertices = new HashMap<>(previous);

        return distances;
    }


    public Map<Vertice, Vertice> getPreviousVertices() {
        return previousVertices;
    }
    public String getShortestPathAsString(List<Vertice> shortestPath) {
        StringBuilder path = new StringBuilder();
        for (Vertice v : shortestPath) {
            path.append(v.getData()).append(" -> ");
        }
        // Eliminar la flecha adicional al final del camino
        if (path.length() > 0) {
            path.delete(path.length() - 4, path.length());
        }
        return path.toString();
    }

    public List<Vertice> bfs(Vertice source) {
        List<Vertice> visited = new ArrayList<>();
        Queue<Vertice> queue = new PriorityQueue<>((v1, v2) -> v1.getData().compareTo(v2.getData()));

        visited.add(source);
        queue.offer(source);

        while (!queue.isEmpty()) {
            Vertice current = queue.poll();

            for (Edge edge : current.getEdges()) {
                Vertice neighbor = edge.getFinalVertice();
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.offer(neighbor);
                }
            }
        }

        return visited;
    }


    public List<Vertice> dfs(Vertice source) {
        List<Vertice> visited = new ArrayList<>();
        dfsHelper(source, visited);
        return visited;
    }

    private void dfsHelper(Vertice current, List<Vertice> visited) {
        visited.add(current);

        for (Edge edge : current.getEdges()) {
            Vertice neighbor = edge.getFinalVertice();
            if (!visited.contains(neighbor)) {
                dfsHelper(neighbor, visited);
            }
        }
    }
}
