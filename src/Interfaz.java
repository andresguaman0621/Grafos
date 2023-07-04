import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Interfaz extends JFrame {
    private JPanel mainPanel;
    private JCheckBox direccionadoCheckBox;
    private JCheckBox conPesoCheckBox;
    private JButton crearButton;
    private JButton quemarButton;
    private JTextField textField1;
    private JButton insertarButton;
    private JComboBox<String> comboBox1;
    private JComboBox<String> comboBox2;
    private JTextField textField2;
    private JButton insertarButton1;
    private JButton mostrarGrafoButton;
    private JComboBox<String> comboBox3;
    private JButton DFSButton;
    private JButton BFSButton;
    private JButton dijkstraButton;
    private JTextArea textArea1;
    private JPanel JPanel;
    private JComboBox cmBoxRemoveVer;
    private JButton btnAceptRemove;
    private List<Vertice> recorrido;

    private Grafo grafo;

    public Interfaz() {
        crearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                comboBox1.removeAllItems();
                comboBox2.removeAllItems();
                comboBox3.removeAllItems();
                cmBoxRemoveVer.removeAllItems();

                boolean isDirected = direccionadoCheckBox.isSelected();
                boolean isWeighted = conPesoCheckBox.isSelected();

                if (isWeighted==false){
                    textField2.setEditable(false);
                    textField2.setText("1");
                }else{
                    textField2.setEditable(true);
                }
                grafo = new Grafo(isDirected, isWeighted);
                mostrarGrafoButton.setEnabled(true);

                JOptionPane.showMessageDialog(null, "Grafo creado con éxito.");
            }
        });

        insertarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(textField1.getText().isEmpty()==false){
                    String data = textField1.getText();
                    grafo.addVertice(data);
                    comboBox1.addItem(data);
                    comboBox2.addItem(data);
                    comboBox3.addItem(data);
                    cmBoxRemoveVer.addItem(data);
                    textField1.setText("");

                    JOptionPane.showMessageDialog(null, "Vertice añadido con éxito");
                }else{
                    JOptionPane.showMessageDialog(null, "Valor no válido");
                }

            }
        });

        insertarButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String initialVertex=null;
                String finalVertex=null;
                if(comboBox1.getSelectedItem()!=null && comboBox1.getSelectedItem()!=null && textField2.getText().isEmpty()==false){
                    initialVertex = comboBox1.getSelectedItem().toString();
                    finalVertex = comboBox2.getSelectedItem().toString();

                int weight = Integer.parseInt(textField2.getText());

                // Verificar si la arista ya existe
                if (grafo.existeArista(initialVertex, finalVertex)) {
                    JOptionPane.showMessageDialog(null, "La arista ya existe");
                }else {
                    // Añadir la arista al grafo
                    grafo.addEdge(initialVertex, finalVertex, weight);
                    textField2.setText("");
                    JOptionPane.showMessageDialog(null, "Arista añadida con éxito.");
                }

                }else{
                    JOptionPane.showMessageDialog(null, "Valores no validos");
                }
            }
        });

        mostrarGrafoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StringBuilder graphInfo = new StringBuilder("Grafo:\n");
                for (Vertice vertice : grafo.getVertices()) {
                    graphInfo.append(vertice.getData()).append(" -> ");
                    List<String> edgesInfo = new ArrayList<>();
                    for (Edge edge : vertice.getEdges()) {
                        String edgeInfo = edge.getFinalVertice().getData();
                        if (conPesoCheckBox.isSelected()) {
                            edgeInfo += "(" + edge.getWeight() + ")";
                        }
                        edgesInfo.add(edgeInfo);
                    }
                    graphInfo.append(String.join(",", edgesInfo)).append("\n");
                }
                textArea1.setText(graphInfo.toString());
            }
        });

        DFSButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //JOptionPane.showMessageDialog(null, "DFS ejecutado con éxito.");
                if(comboBox3.getSelectedItem()!=null){
                    Vertice source = grafo.getVertexByValue(comboBox3.getSelectedItem().toString());
                    List<Vertice> dfsResult = grafo.dfs(source);
                    StringBuilder result = new StringBuilder("*Algortimo DFS*\n");
                    for (Vertice v : dfsResult) {
                        result.append(v.getData()).append("\n");
                    }
                    textArea1.setText(result.toString());
                }else{
                    JOptionPane.showMessageDialog(null, "Valor inicial no válido");
                }

            }
        });

        BFSButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(comboBox3.getSelectedItem()!=null){
                    Vertice source = grafo.getVertexByValue(comboBox3.getSelectedItem().toString());
                    List<Vertice> bfsResult = grafo.bfs(source);
                    StringBuilder result = new StringBuilder("*Algortimo BFS*\n");
                    for (Vertice v : bfsResult) {
                        result.append(v.getData()).append("\n");
                    }
                    textArea1.setText(result.toString());
                }else{
                    JOptionPane.showMessageDialog(null, "Valor inicial no válido");
                }

            }
        });

        dijkstraButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(comboBox3.getSelectedItem()!=null){
                    Vertice source = grafo.getVertexByValue(comboBox3.getSelectedItem().toString());
                    Map<Vertice, Integer> distances = grafo.dijkstra(source);

                    StringBuilder result = new StringBuilder("*Algortimo Dijkstra Camino mas corto*\n\n");
                    for (Map.Entry<Vertice, Integer> entry : distances.entrySet()) {
                        Vertice target = entry.getKey();
                        List<Vertice> shortestPath = new ArrayList<>();
                        Vertice current = target;

                        // Reconstruir el camino desde el vértice objetivo hasta el origen
                        while (current != null) {
                            shortestPath.add(0, current);
                            current = grafo.getPreviousVertices().get(current);
                        }

                        int distancia = entry.getValue();
                        if(distancia == 2147483647){
                            result.append("Camino mas corto a ").append(target.getData()).append(": ").append("No existe camino\n");
                        }else{
                            result.append("Camino mas corto a ").append(target.getData()).append(": ").append(entry.getValue()).append("\n");
                        }


                        for (Vertice v : shortestPath) {
                            result.append(v.getData()).append("  ");
                        }
                        result.append("\n\n");
                    }
                    textArea1.setText(result.toString());
                }else{
                    JOptionPane.showMessageDialog(null, "Valor inicial no válido");
                }

            }
        });

        quemarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //grafo = new Grafo(direccionadoCheckBox2.isSelected(), conPesoCheckBox2.isSelected());
                // Añadir 5 vértices
                /*grafo.addVertice("1");
                grafo.addVertice("2");
                grafo.addVertice("3");
                grafo.addVertice("4");
                grafo.addVertice("5");
                grafo.addVertice("6");

                // Añadir aristas con peso
                grafo.addEdge("1", "2", 3);
                grafo.addEdge("2", "3", 2);
                grafo.addEdge("3", "4", 6);
                grafo.addEdge("1", "3", 4);
                grafo.addEdge("1", "4", 9);
                grafo.addEdge("2", "4", 3);
                grafo.addEdge("3", "5", 7);
                grafo.addEdge("5", "6", 2);

                // Actualizar ComboBox
                comboBox1.removeAllItems();
                comboBox2.removeAllItems();
                comboBox3.removeAllItems();
                cmBoxRemoveVer.removeAllItems();

                for (Vertice vertice : grafo.getVertices()) {
                    String data = vertice.getData();
                    comboBox1.addItem(data);
                    comboBox2.addItem(data);
                    comboBox3.addItem(data);
                    cmBoxRemoveVer.addItem(data);
                }
                mostrarGrafoButton.setEnabled(true);

                JOptionPane.showMessageDialog(null, "Grafo creado");*/
                grafo = new Grafo(true,false);

                grafo.addVertice("1");
                grafo.addVertice("2");
                grafo.addVertice("3");
                grafo.addVertice("4");
                grafo.addVertice("5");

                grafo.addEdge("1", "3", 1);
                grafo.addEdge("1", "4", 1);
                grafo.addEdge("2", "5", 1);
                grafo.addEdge("3", "2", 1);
                grafo.addEdge("3", "4", 1);

                comboBox1.removeAllItems();
                comboBox2.removeAllItems();
                comboBox3.removeAllItems();
                cmBoxRemoveVer.removeAllItems();

                for (Vertice vertice : grafo.getVertices()) {
                    String data = vertice.getData();
                    comboBox1.addItem(data);
                    comboBox2.addItem(data);
                    comboBox3.addItem(data);
                    cmBoxRemoveVer.addItem(data);
                }
                mostrarGrafoButton.setEnabled(true);

                JOptionPane.showMessageDialog(null, "Grafo creado");
            }
        });
        btnAceptRemove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(cmBoxRemoveVer.getSelectedItem()!=null){
                    String vertexToRemove = cmBoxRemoveVer.getSelectedItem().toString();
                    Vertice vertice = grafo.getVertexByValue(vertexToRemove);
                    grafo.removeVertice(vertice);
                    comboBox1.removeItem(vertexToRemove);
                    comboBox2.removeItem(vertexToRemove);
                    comboBox3.removeItem(vertexToRemove);
                    cmBoxRemoveVer.removeItem(vertexToRemove);
                    JOptionPane.showMessageDialog(null, "Vértice eliminado con éxito");
                }else{
                    JOptionPane.showMessageDialog(null, "Valor no válido");
                }

            }
        });
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}



