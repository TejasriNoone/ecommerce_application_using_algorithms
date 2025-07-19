import java.util.*;

public class FraudDetectionDFS {
    private Map<Integer, List<Integer>> transactionGraph = new HashMap<>();
    private Set<Integer> visited = new HashSet<>();

    // Add transaction relation (like user1 sends money to user2)
    public void addTransaction(int from, int to) {
        transactionGraph.putIfAbsent(from, new ArrayList<>());
        transactionGraph.get(from).add(to);
    }

    // DFS method
    public void dfs(int user) {
        visited.add(user);
        System.out.println("Visited User: " + user);

        for (int neighbor : transactionGraph.getOrDefault(user, new ArrayList<>())) {
            if (!visited.contains(neighbor)) {
                dfs(neighbor);
            }
        }
    }

    public static void main(String[] args) {
        FraudDetectionDFS fd = new FraudDetectionDFS();

        // Sample transactions
        fd.addTransaction(1, 2);
        fd.addTransaction(2, 3);
        fd.addTransaction(3, 4);
        fd.addTransaction(4, 2); // Cycle indicating potential fraud

        System.out.println("DFS Traversal starting from user 1:");
        fd.dfs(1);
    }
}
