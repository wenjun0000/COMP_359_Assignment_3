package UF;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        QuickFind sets = new QuickFind(10); // Set up 9 nodes
        sets.shuffle(); // O(n^2) due to shuffle's quadratic complexity

        System.out.println("Number of connected components: " + sets.count());
        sets.print(); // O(n)

        // Print all connected nodes
        sets.printAllConnections();

        Scanner scanner = new Scanner(System.in);
        
        int maxNode = sets.parents.length - 1; // Corrected range based on array length
        
        while (true) {
            System.out.println("Enter two nodes to check if they are connected (or type 'exit' to quit).");
            System.out.println("Note: Valid nodes are between 0 and " + maxNode + ".");
            
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("exit")) break;
            
            String[] nodes = input.split(" ");
            
            // Check if the user entered two numbers
            if (nodes.length != 2) {
                System.out.println("Please enter exactly two numbers separated by a space.");
                continue;
            }
            
            try {
                int p = Integer.parseInt(nodes[0]);
                int q = Integer.parseInt(nodes[1]);
                
                // Validate if the nodes are within range
                if (p < 0 || p > maxNode || q < 0 || q > maxNode) {
                    System.out.println("Invalid input. Please enter numbers between 0 and " + maxNode + ".");
                } else {
                    System.out.println("Are " + p + " and " + q + " connected? " + sets.connected(p, q));
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter two valid numbers.");
            }
        }
        
        scanner.close();
    }
}
