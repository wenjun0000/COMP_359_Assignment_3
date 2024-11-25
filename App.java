package uf;

import javax.swing.*;
import java.awt.*;

public class App {
    public static void main(String[] args) {
        // Define allowed connections as a 6x6 adjacency matrix
        boolean[][] allowedConnections = new boolean[6][6];
        for (int i = 0; i < 6; i++) {
            allowedConnections[i][(i + 1) % 6] = true; // Allow connections to adjacent nodes in a circular manner
            allowedConnections[(i + 1) % 6][i] = true; // Ensure symmetry
        }

        // Define additional allowed connections manually
        allowedConnections[3][0] = allowedConnections[0][3] = true;
        allowedConnections[4][2] = allowedConnections[2][4] = true;
        allowedConnections[5][1] = allowedConnections[1][5] = true;
        allowedConnections[4][1] = allowedConnections[1][4] = true;
        allowedConnections[5][2] = allowedConnections[2][5] = true;
        allowedConnections[3][5] = allowedConnections[5][3] = true;
        allowedConnections[4][0] = allowedConnections[0][4] = true;
        allowedConnections[3][1] = allowedConnections[1][3] = true;
        allowedConnections[2][0] = allowedConnections[0][2] = true;

        // Initialize the QuickFind data structure with 6 nodes
        QuickFind quickFind = new QuickFind(6, allowedConnections);

        // Create the main JFrame
        JFrame frame = new JFrame("QuickFind Visualization");
        QuickFindVisualizer visualizer = new QuickFindVisualizer(quickFind);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 700);
        frame.setLayout(new BorderLayout());

        // Create a control panel for user interaction
        JPanel controlPanel = new JPanel();
        controlPanel.setBackground(Color.LIGHT_GRAY);

        // Add input fields and buttons to the control panel
        JTextField node1Field = new JTextField(5); // Field for the first node
        JTextField node2Field = new JTextField(5); // Field for the second node
        JButton unionButton = new JButton("Union"); // Button to perform union operation

        JTextField findField = new JTextField(5); // Field for the node to find
        JButton findButton = new JButton("Find"); // Button to perform find operation
        JButton clearButton = new JButton("Clear"); // Button to reset the data structure

        // Add input fields and buttons to the control panel
        controlPanel.add(new JLabel("Node 1:"));
        controlPanel.add(node1Field);
        controlPanel.add(new JLabel("Node 2:"));
        controlPanel.add(node2Field);
        controlPanel.add(unionButton);
        controlPanel.add(new JLabel("Find:"));
        controlPanel.add(findField);
        controlPanel.add(findButton);
        controlPanel.add(clearButton);

        // Create a log area to display operations and updates
        JTextArea logArea = new JTextArea();
        logArea.setEditable(false); // Prevent user edits
        JScrollPane logScrollPane = new JScrollPane(logArea);
        logScrollPane.setPreferredSize(new Dimension(200, 700));

        // Add components to the frame
        frame.add(visualizer, BorderLayout.CENTER); // Visualization area
        frame.add(controlPanel, BorderLayout.SOUTH); // Control panel
        frame.add(logScrollPane, BorderLayout.EAST); // Log area

        // Display the initial state of the parent array
        logArea.append("Initial Parent Nodes:\n");
        logArea.append(formatParentArray(quickFind.getParents()) + "\n");

        // Define action for the Union button
        unionButton.addActionListener(e -> {
            try {
                int p = Integer.parseInt(node1Field.getText());
                int q = Integer.parseInt(node2Field.getText());
                if (p >= 0 && p < 6 && q >= 0 && q < 6) { // Check valid input range
                    if (quickFind.union(p, q)) { // Perform union if allowed
                        visualizer.refresh();
                        logArea.append("Union(" + p + ", " + q + ") performed.\n");
                        logArea.append("Updated Parent Nodes:\n");
                        logArea.append(formatParentArray(quickFind.getParents()) + "\n");
                    } else {
                        logArea.append("Union(" + p + ", " + q + ") not allowed.\n");
                    }
                } else {
                    logArea.append("Invalid nodes for Union: " + p + ", " + q + ".\n");
                }
            } catch (NumberFormatException ex) {
                logArea.append("Invalid input for Union operation.\n");
            }
        });

        // Define action for the Find button
        findButton.addActionListener(e -> {
            try {
                int p = Integer.parseInt(findField.getText());
                if (p >= 0 && p < 6) { // Check valid input range
                    int root = quickFind.find(p); // Find the root of the node
                    visualizer.highlightFindPath(p);
                    logArea.append("Find(" + p + "): Root is " + root + ".\n");
                } else {
                    logArea.append("Invalid node for Find: " + p + ".\n");
                }
            } catch (NumberFormatException ex) {
                logArea.append("Invalid input for Find operation.\n");
            }
        });

        // Define action for the Clear button
        clearButton.addActionListener(e -> {
            quickFind.clear(); // Reset all nodes
            visualizer.clearHighlights(); // Remove highlights
            visualizer.refresh(); // Redraw the visualization
            logArea.append("All unions cleared.\n");
            logArea.append("Parent Nodes Reset:\n");
            logArea.append(formatParentArray(quickFind.getParents()) + "\n");
        });

        frame.setVisible(true); // Display the frame
    }

    // Utility method to format the parent array for display in the log
    private static String formatParentArray(int[] parents) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < parents.length; i++) {
            sb.append(i).append(" -> ").append(parents[i]).append("\n");
        }
        return sb.toString();
    }
}
