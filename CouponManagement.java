import java.util.HashMap;
import java.util.Scanner;

class Coupon {
    String code;
    int discount;       
    double minPrice;    
    double maxPrice;    

    public Coupon(String code, int discount, double minPrice, double maxPrice) {
        this.code = code;
        this.discount = discount;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
    }

    public boolean isEligible(double price) {
        return price >= minPrice && price <= maxPrice;
    }
}

public class CouponManagement {
    private HashMap<String, Coupon> coupons;

    public CouponManagement() {
        coupons = new HashMap<>();
    }

    public void addCoupon(String code, int discount, double minPrice, double maxPrice) {
        coupons.put(code, new Coupon(code, discount, minPrice, maxPrice));
        System.out.println("Coupon " + code + " added with " + discount + "% off (₹" + minPrice + " - ₹" + maxPrice + ")");
    }

    public boolean validateCoupon(String code) {
        return coupons.containsKey(code);
    }

    public double applyCoupon(String code, double price) {
        if (!validateCoupon(code)) {
            System.out.println(" Invalid coupon code.");
            return price;
        }

        Coupon coupon = coupons.get(code);

        if (!coupon.isEligible(price)) {
            System.out.println(" This coupon is not applicable for your order amount.");
            return price;
        }

        double discountedPrice = price - (price * coupon.discount / 100.0);
        System.out.printf(" Coupon applied! You saved ₹%.2f\n", (price - discountedPrice));
        return discountedPrice;
    }

    public void removeCoupon(String code) {
        if (coupons.remove(code) != null) {
            System.out.println("Coupon " + code + " removed.");
        } else {
            System.out.println("Coupon " + code + " does not exist.");
        }
    }

    public void showAllCoupons() {
        if (coupons.isEmpty()) {
            System.out.println("No coupons available.");
        } else {
            System.out.println(" Available Coupons:");
            for (Coupon c : coupons.values()) {
                System.out.println(c.code + " => " + c.discount + "% off for ₹" + c.minPrice + " to ₹" + c.maxPrice);
            }
        }
    }

    public static void main(String[] args) {
        CouponManagement cm = new CouponManagement();
        Scanner sc = new Scanner(System.in);

        cm.addCoupon("WELCOME10", 10, 1001, 5000);        
        cm.addCoupon("FESTIVE20", 20, 5001, 20000);      
        cm.addCoupon("MEGA50", 50, 20001, Double.MAX_VALUE); 

        System.out.print("\nEnter product price: ₹");
        double price = sc.nextDouble();
        sc.nextLine(); 

        if (price > 1000) {
            cm.showAllCoupons(); 
            System.out.print("Enter coupon code: ");
            String code = sc.nextLine();

            double finalPrice = cm.applyCoupon(code, price);
            System.out.printf(" Final price: ₹%.2f\n", finalPrice);
        } else {
            System.out.println(" Coupons are not available for orders ₹1000 or below.");
            System.out.printf(" Final price: ₹%.2f\n", price);
        }

        sc.close();
    }
}
