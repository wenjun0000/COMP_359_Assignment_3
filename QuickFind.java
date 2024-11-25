package uf;

public class QuickFind {
    private int[] parent; // Parent array representing the connected components
    private boolean[][] allowedConnections; // Matrix of allowed connections

    // Constructor to initialize QuickFind data structure
    public QuickFind(int n, boolean[][] connections) {
        parent = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i; // Each node starts as its own root
        }
        this.allowedConnections = connections; // Set allowed connections
    }

    // Path compression for finding the root of a node
    public int find(int x) {
        if (parent[x] != x) {
            parent[x] = find(parent[x]); // Recursively compress path
        }
        return parent[x];
    }

    // Union operation to merge two components
    public boolean union(int x, int y) {
        if (!allowedConnections[x][y]) {
            return false; // Return false if the connection is not allowed
        }

        int rootX = find(x); // Find root of x
        int rootY = find(y); // Find root of y

        if (rootX != rootY) {
            parent[rootY] = rootX; // Merge sets by pointing y's root to x's root
        }
        return true;
    }

    // Reset all nodes to their original state
    public void clear() {
        for (int i = 0; i < parent.length; i++) {
            parent[i] = i;
        }
    }

    // Get a copy of the parent array
    public int[] getParents() {
        // Apply path compression for consistency
        for (int i = 0; i < parent.length; i++) {
            find(i); // Ensure all nodes point to the correct root
        }
        return parent.clone();
    }

    // Return the allowed connections matrix
    public boolean[][] getAllowedConnections() {
        return allowedConnections;
    }
}
