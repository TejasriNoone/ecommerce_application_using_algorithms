import java.util.*;

// Order class implementing Comparable to sort by priority
class Order implements Comparable<Order> {
    int orderId;
    String customerName;
    int priority; // Higher value = higher priority
    String itemName;

    public Order(int orderId, String customerName, int priority, String itemName) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.priority = priority;
        this.itemName = itemName;
    }

    // Sorting orders in descending order of priority
    @Override
    public int compareTo(Order o) {
        return o.priority - this.priority;
    }

    @Override
    public String toString() {
        return "Order ID: " + orderId +
               ", Customer: " + customerName +
               ", Item: " + itemName +
               ", Priority: " + priority;
    }
}

public class ECommerceOrderScheduler {
    public static void main(String[] args) {
        // Priority Queue to manage orders
        PriorityQueue<Order> orderQueue = new PriorityQueue<>();

        // Adding some orders
        orderQueue.add(new Order(101, "Sravya", 3, "Smartphone"));
        orderQueue.add(new Order(102, "Arjun", 1, "Book"));
        orderQueue.add(new Order(103, "Riya", 5, "Laptop"));
        orderQueue.add(new Order(104, "Vikram", 2, "Shoes"));
        orderQueue.add(new Order(105, "Anjali", 4, "Tablet"));

        System.out.println("Order processing based on priority:\n");

        // Processing orders in priority order
        while (!orderQueue.isEmpty()) {
            Order current = orderQueue.poll();
            System.out.println(current);
        }
    }
}
