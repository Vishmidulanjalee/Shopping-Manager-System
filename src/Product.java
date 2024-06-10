import java.io.Serializable;

public  abstract class Product implements Serializable {
    private String productID;
    private String productName;
    private int numOfItems;
    private double price;
    private String category;

    // Constructor
    public Product(String productID, String productName, int numOfItems, double price) {
        this.productID = productID;
        this.productName = productName;
        this.numOfItems = numOfItems;
        this.price = price;
    }

    // Getters
    public String getProductID() {
        return productID;
    }

    public String getProductName() {
        return productName;
    }

    public int getNumOfItems() {
        return numOfItems;
    }

    public double getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }
    private boolean isFirstPurchase = true;

    public void setNumOfItems(int numOfItems) {
        this.numOfItems = numOfItems;
    }

    public void setCategory(String category) {
        this.category = category;
    }


    @Override
    public String toString() {
        return "Product ID: " + productID +
                "\nProduct Name: " + productName +
                "\nCategory: " + category +
                "\nNumber of Items: " + numOfItems +
                "\nPrice: " + price;
    }
}