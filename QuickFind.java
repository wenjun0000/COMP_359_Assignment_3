package UF;

	// Based on: https://regenerativetoday.com/union-find-data-structure-quick-find-algorithm/

public class QuickFind {
    private int count; // number of connected components
    int[] parents; // stores tree structure

    public QuickFind(int n) {
        count = n;
        parents = new int[n];
        for (int i = 0; i < n; i++) {
            parents[i] = i; // O(n): initializing the array
        }
    }

    // Returns the number of connected components
    public int count() {
        return count;
    }

    // Find method - O(1) (constant time)
    public int find(int p) {
        return parents[p];
    }

    // Union method - O(n) (linear time)
    public void union(int p, int q) {
        int rootP = find(p);
        int rootQ = find(q);

        if (rootP == rootQ) return;

        for (int i = 0; i < parents.length; i++) {
            if (parents[i] == rootQ) {
                parents[i] = rootP;
            }
        }
        count--;
    }

    // Checks if two nodes are in the same component - O(1)
    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }

    // Shuffle method - O(n^2) (quadratic time)
    public void shuffle() {
        int j;
        for (int i = 0; i < parents.length - 1; i++) {
            j = (int) (Math.random() * (parents.length - i) + i);
            union(i, j);
        }
    }

    // Print the current state of the parents array - O(n)
    public void print() {
        System.out.print("{ ");
        for (int i = 0; i < parents.length; i++) {
            System.out.print(parents[i] + " ");
        }
        System.out.println("}");
    }

    // Print all connected nodes
    public void printAllConnections() {
        System.out.println("All connected nodes:");
        for (int i = 0; i < parents.length; i++) {
            for (int j = i + 1; j < parents.length; j++) {
                if (connected(i, j)) {
                    System.out.println(i + " is connected to " + j);
                }
            }
        }
    }
}
