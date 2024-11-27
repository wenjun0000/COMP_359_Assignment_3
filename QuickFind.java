package uf;

import uf.QuickFind.UnionStrategy;

public class QuickFind {
	private int[] parent; // Parent array representing the connected components
	private int[] rank; // Rank array for rank-based union
	private int[] size; // Size array for size-based union
	private boolean[][] allowedConnections; // Matrix of allowed connections
	private UnionStrategy strategy; // Selected union strategy

	// Enum to define union strategies
	public enum UnionStrategy {
	PATH_COMPRESSION_RANK,
	PATH_COMPRESSION_SIZE
	}

	// Constructor to initialize QuickFind data structure
	public QuickFind(int n, boolean[][] connections) {
		parent = new int[n];
		rank = new int[n];
		size = new int[n];
		for (int i = 0; i < n; i++) {
			parent[i] = i; // Each node starts as its own root
			rank[i] = 0;
			size[i] = 1;
	}
		this.allowedConnections = connections; // Set allowed connections
		this.strategy = UnionStrategy.PATH_COMPRESSION_RANK; // Default strategy
	}
	
	// Set the union strategy
	public void setStrategy(UnionStrategy strategy) {
	    this.strategy = strategy;
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
		
		if (rootX == rootY) {
			return true; // Already in the same set
		}
		
		// Apply union based on the selected strategy
		switch (strategy) {
		case PATH_COMPRESSION_RANK:
		// Union by rank
		if (rank[rootX] < rank[rootY]) {
			parent[rootX] = rootY;
		} else {
			parent[rootY] = rootX;
		if (rank[rootX] == rank[rootY]) {
			rank[rootX]++;
		}
		}
		break;
	
	case PATH_COMPRESSION_SIZE:
	// Union by size
		if (size[rootX] >= size[rootY]) {
			parent[rootY] = rootX;
			size[rootX] += size[rootY];
		} else {
			parent[rootX] = rootY;
			size[rootY] += size[rootX];
		}
		break;
	 }
		return true;
	}
	
	// Reset all nodes to their original state
	public void clear() {
		for (int i = 0; i < parent.length; i++) {
			parent[i] = i;
			rank[i] = 0;
			size[i] = 1;
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
		
	// Getter for the current strategy
	public UnionStrategy getStrategy() {
		return strategy;
	}
}
