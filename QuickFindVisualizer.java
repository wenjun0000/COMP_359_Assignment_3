package uf;

import javax.swing.*;
import java.awt.*;
import java.util.*;

@SuppressWarnings("serial")
public class QuickFindVisualizer extends JPanel {
    private QuickFind quickFind; // Reference to QuickFind instance
    private Map<Integer, Point> nodeLocations; // Map of node locations on the panel
    private Set<Integer> highlightedNodes; // Nodes to be highlighted
    private Set<Pair<Integer, Integer>> highlightedEdges; // Edges to be highlighted

    // Constructor initializes visualizer with QuickFind instance
    public QuickFindVisualizer(QuickFind quickFind) {
        this.quickFind = quickFind;
        this.nodeLocations = new HashMap<>();
        this.highlightedNodes = new HashSet<>();
        this.highlightedEdges = new HashSet<>();
        setNodeLocations(); // Set initial positions of nodes
    }

    // Arrange nodes in a circular layout
    private void setNodeLocations() {
        int size = quickFind.getParents().length;
        int radius = 250; // Radius of the circle
        Point center = new Point(400, 300); // Center of the panel

        for (int i = 0; i < size; i++) {
            double angle = 2 * Math.PI * i / size; // Compute angle for each node
            int x = center.x + (int) (radius * Math.cos(angle));
            int y = center.y + (int) (radius * Math.sin(angle));
            nodeLocations.put(i, new Point(x, y));
        }
    }

    // Paint the visualization of the graph
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Draw allowed connections in light gray
        boolean[][] allowedConnections = quickFind.getAllowedConnections();
        for (int i = 0; i < allowedConnections.length; i++) {
            for (int j = i + 1; j < allowedConnections[i].length; j++) {
                if (allowedConnections[i][j]) {
                    Point p1 = nodeLocations.get(i);
                    Point p2 = nodeLocations.get(j);
                    g2d.setColor(Color.LIGHT_GRAY);
                    g2d.setStroke(new BasicStroke(1));
                    g2d.drawLine(p1.x, p1.y, p2.x, p2.y);
                }
            }
        }

        // Draw union connections in black
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(3));
        for (int i = 0; i < quickFind.getParents().length; i++) {
            int parent = quickFind.find(i);
            if (i != parent) { // If not a self-loop
                Point child = nodeLocations.get(i);
                Point root = nodeLocations.get(parent);
                g2d.drawLine(child.x, child.y, root.x, root.y);
            }
        }

        // Highlight edges in red
        g2d.setColor(Color.RED);
        g2d.setStroke(new BasicStroke(2));
        for (Pair<Integer, Integer> edge : highlightedEdges) {
            Point p1 = nodeLocations.get(edge.getKey());
            Point p2 = nodeLocations.get(edge.getValue());
            g2d.drawLine(p1.x, p1.y, p2.x, p2.y);
        }

        // Draw nodes with highlights and labels
        for (Map.Entry<Integer, Point> entry : nodeLocations.entrySet()) {
            int node = entry.getKey();
            Point location = entry.getValue();

            // Draw node with green highlight if selected
            if (highlightedNodes.contains(node)) {
                g2d.setColor(Color.GREEN);
            } else {
                g2d.setColor(Color.ORANGE);
            }
            g2d.fillOval(location.x - 20, location.y - 20, 40, 40);

            // Draw border and label
            g2d.setColor(Color.BLACK);
            g2d.drawOval(location.x - 20, location.y - 20, 40, 40);
            g2d.drawString(node + " (" + quickFind.find(node) + ")", location.x - 30, location.y + 30);
        }
    }

    // Highlight the path from a node to its root
    public void highlightFindPath(int start) {
        int current = start;
        highlightedNodes.clear();
        highlightedEdges.clear();
        while (current != quickFind.find(current)) {
            int parent = quickFind.find(current);
            highlightedNodes.add(current);
            highlightedEdges.add(new Pair<>(current, parent));
            current = parent;
        }
        highlightedNodes.add(current);
        repaint();
    }

    // Clear all highlights
    public void clearHighlights() {
        highlightedNodes.clear();
        highlightedEdges.clear();
        repaint();
    }

    // Refresh the visualization
    public void refresh() {
        repaint();
    }

    // Pair class to represent edges between nodes
    private static class Pair<K, V> {
        private K key;
        private V value;

        public Pair(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }
    }
}
