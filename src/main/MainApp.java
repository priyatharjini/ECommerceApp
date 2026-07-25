package main;

import service.ECommerceService;
import java.util.Scanner;

public class MainApp {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        ECommerceService service = new ECommerceService();

        int userId = 1; // Dummy logged-in user

        while (true) {
            System.out.println("\n--- E-Commerce Order Management ---");
            System.out.println("1. View Products");
            System.out.println("2. Place Order");
            System.out.println("3. View Purchase History");
            System.out.println("4. Exit");
            System.out.print("Enter choice: ");

            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    service.viewProducts();
                    break;
                case 2:
                    service.placeOrder(userId);
                    break;
                case 3:
                    service.viewHistory(userId);
                    break;
                case 4:
                    System.out.println("Thank you for shopping!");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}
