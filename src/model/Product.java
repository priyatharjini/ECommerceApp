package model;

public class Product {
    private int productId;
    private String productName;
    private double price;
    private int stock;

    public Product(int productId, String productName, double price, int stock) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.stock = stock;
    }

    public int getProductId() { return productId; }
    public String getProductName() { return productName; }
    public double getPrice() { return price; }
    public int getStock() { return stock; }
}
