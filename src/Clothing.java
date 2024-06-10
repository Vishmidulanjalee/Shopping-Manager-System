import java.io.Serializable;

public class Clothing extends Product implements Serializable {
    private String size;
    private String colour;
    private int quantity;
    private String info;

    //constructor matching with the superclass
    public Clothing(String productID, String productName, int numOfItems, double price) {
        super(productID, productName, numOfItems, price);
    }

    //getters
    public String getSize() {
        return size;
    }

    public String getColour() {
        return colour;
    }


    //setters
    public void setSize(String size) {
        this.size = size;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }




    public void displayDetails() {
        System.out.println("ProductID: " + getProductID() + "\nCategory: Clothing" + "\nName: " + getProductName() +
                "\nColour:" + getColour() + "\nSize: " + getSize() +
                "\nPrice:" + getPrice() + "\nAvailable products: " + getNumOfItems());
    }

    public String toString() {
        return "ProductID: " + getProductID() + "\nCategory"+getCategory() + "\nName: " + getProductName() +
                "\nColour:" + getColour() + "\nSize: " + getSize() +
                "\nPrice:" + getPrice() + "\nAvailable products: " + getNumOfItems();
    }
}