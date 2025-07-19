import java.util.*;

class Product {
    private final int id;
    private final String name;
    private final double price;

    Product(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    int getId() {
        return id;
    }

    String getName() {
        return name;
    }

    double getPrice() {
        return price;
    }
}

class CartItem {
    private final Product product;
    private int quantity;

    CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    void addQuantity(int qty) {
        this.quantity += qty;
    }

    double getSubtotal() {
        return product.getPrice() * quantity;
    }

    Product getProduct() {
        return product;
    }

    int getQuantity() {
        return quantity;
    }
}

class ShoppingCart {
    private final Map<Integer, CartItem> items = new HashMap<>();

    void addItem(Product product, int quantity) {
        items.merge(
            product.getId(),
            new CartItem(product, quantity),
            (existingItem, newItem) -> {
                existingItem.addQuantity(quantity);
                return existingItem;
            });
    }

    void displayCart() {
        if (items.isEmpty()) {
            System.out.println("Cart is empty.");
            return;
        }

        double total = 0;
        System.out.println("\nCart Items:");
        System.out.println("-------------------------------------------");
        System.out.printf("%-20s %-10s %-10s%n", "Product", "Quantity", "Subtotal");
        System.out.println("-------------------------------------------");

        for (CartItem item : items.values()) {
            double subtotal = item.getSubtotal();
            total += subtotal;
            System.out.printf("%-20s %-10d ₹%.2f%n",
                    item.getProduct().getName(), item.getQuantity(), subtotal);
        }

        System.out.println("-------------------------------------------");
        System.out.printf("Total Amount: ₹%.2f%n", total);
    }
}

public class HashMapEcommerce {
    private static final Map<Integer, Product> catalog = new HashMap<>();
    private static final Map<Integer, ShoppingCart> userCarts = new HashMap<>();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        initializeCatalog();

        System.out.print("Enter your User ID: ");
        int userId = scanner.nextInt();
        ShoppingCart cart = userCarts.computeIfAbsent(userId, id -> new ShoppingCart());

        int choice;
        do {
            printMenu();
            choice = scanner.nextInt();
            handleChoice(choice, cart);
        } while (choice != 4);

        scanner.close();
    }

    private static void initializeCatalog() {
        System.out.print("Enter number of products to add to catalog: ");
        int productCount = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        for (int i = 1; i <= productCount; i++) {
            System.out.println("\nEnter details for Product " + i + ":");

            System.out.print("Product ID: ");
            int id = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (catalog.containsKey(id)) {
                System.out.println("Product ID already exists. Skipping this product.");
                continue;
            }

            System.out.print("Product Name: ");
            String name = scanner.nextLine();

            System.out.print("Product Price: ");
            double price = scanner.nextDouble();

            catalog.put(id, new Product(id, name, price));
        }
    }

    private static void printMenu() {
        System.out.println("\n===== E-Commerce Menu =====");
        System.out.println("1. View Product Catalog");
        System.out.println("2. Add Product to Cart");
        System.out.println("3. View Cart");
        System.out.println("4. Checkout and Exit");
        System.out.print("Enter your choice: ");
    }

    private static void handleChoice(int choice, ShoppingCart cart) {
        switch (choice) {
            case 1:
                printCatalog();
                break;
            case 2:
                addProductToCart(cart);
                break;
            case 3:
                cart.displayCart();
                break;
            case 4:
                cart.displayCart();
                System.out.println("Checkout complete. Thank you for shopping!");
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    private static void printCatalog() {
        if (catalog.isEmpty()) {
            System.out.println("Product catalog is empty.");
            return;
        }

        System.out.println("\nAvailable Products:");
        System.out.println("-------------------------------------------");
        System.out.printf("%-10s %-20s %-10s%n", "ID", "Name", "Price");
        System.out.println("-------------------------------------------");

        catalog.values().forEach(product ->
            System.out.printf("%-10d %-20s ₹%.2f%n",
                product.getId(), product.getName(), product.getPrice())
        );

        System.out.println("-------------------------------------------");
    }

    private static void addProductToCart(ShoppingCart cart) {
        System.out.print("Enter Product ID: ");
        int productId = scanner.nextInt();

        Product product = catalog.get(productId);
        if (product == null) {
            System.out.println("Invalid Product ID.");
            return;
        }

        System.out.print("Enter Quantity: ");
        int quantity = scanner.nextInt();

        if (quantity <= 0) {
            System.out.println("Invalid quantity.");
            return;
        }

        cart.addItem(product, quantity);
        System.out.println("Product added to cart successfully.");
    }
}
