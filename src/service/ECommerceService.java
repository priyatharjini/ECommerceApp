package service;

import db.DBConnection;
import java.sql.*;
import java.util.Scanner;

public class ECommerceService {

    Scanner sc = new Scanner(System.in);

    // View Products
    public void viewProducts() {
        try {
            Connection con = DBConnection.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM products");

            System.out.println("\nID\tName\t\tPrice\tStock");
            while (rs.next()) {
                System.out.println(
                    rs.getInt(1) + "\t" +
                    rs.getString(2) + "\t" +
                    rs.getDouble(3) + "\t" +
                    rs.getInt(4)
                );
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Place Order
    public void placeOrder(int userId) {
        try {
            Connection con = DBConnection.getConnection();

            System.out.print("Enter Product ID: ");
            int pid = sc.nextInt();

            System.out.print("Enter Quantity: ");
            int qty = sc.nextInt();

            PreparedStatement ps = con.prepareStatement(
                "SELECT price, stock FROM products WHERE product_id=?"
            );
            ps.setInt(1, pid);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                double price = rs.getDouble(1);
                int stock = rs.getInt(2);

                if (qty > stock) {
                    System.out.println("Not enough stock!");
                    return;
                }

                double total = price * qty;

                PreparedStatement orderPs = con.prepareStatement(
                    "INSERT INTO orders(user_id, total_amount, order_date) VALUES (?, ?, NOW())",
                    Statement.RETURN_GENERATED_KEYS
                );
                orderPs.setInt(1, userId);
                orderPs.setDouble(2, total);
                orderPs.executeUpdate();

                ResultSet orderRs = orderPs.getGeneratedKeys();
                orderRs.next();
                int orderId = orderRs.getInt(1);

                PreparedStatement itemPs = con.prepareStatement(
                    "INSERT INTO order_items(order_id, product_id, quantity, price) VALUES (?, ?, ?, ?)"
                );
                itemPs.setInt(1, orderId);
                itemPs.setInt(2, pid);
                itemPs.setInt(3, qty);
                itemPs.setDouble(4, price);
                itemPs.executeUpdate();

                PreparedStatement updateStock = con.prepareStatement(
                    "UPDATE products SET stock=stock-? WHERE product_id=?"
                );
                updateStock.setInt(1, qty);
                updateStock.setInt(2, pid);
                updateStock.executeUpdate();

                System.out.println("Order placed successfully!");
                System.out.println("Total Bill: ₹" + total);
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // View Purchase History
    public void viewHistory(int userId) {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                "SELECT * FROM orders WHERE user_id=?"
            );
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            System.out.println("\nOrder ID\tAmount\t\tDate");
            while (rs.next()) {
                System.out.println(
                    rs.getInt(1) + "\t\t" +
                    rs.getDouble(3) + "\t\t" +
                    rs.getTimestamp(4)
                );
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
