import java.io.Serializable;

public class Electronics extends Product implements Serializable {
    private String brand;
    private int warantyPeriod;

    //constructor matching with the superclass
    public Electronics(String productID, String productName, int numOfItems, double price, String brand, int warantyPeriod) {
        super(productID, productName, numOfItems, price);
        this.brand = brand;
        this.warantyPeriod = warantyPeriod;
    }

    //getters
    public String getBrand(){
        return brand;
    }
    public int getWarantyPeriod(){
        return warantyPeriod;
    }


    public void displayDetails(){
        System.out.println("ProductID: " + getProductID()+ "\nCategory: Electronics" +"\nName: "+
                getProductName()+"\nBrand:" +getBrand()+"\nWaranty Period: "+getWarantyPeriod()+" weeks "+"\nPrice:"+getPrice()+"\nAvailabale products: "+getNumOfItems());
    }
    public String toString() {
        return "ProductID: " + getProductID() + "\nCategory: "+getCategory() + "\nName: " +
                getProductName() + "\nBrand:" + getBrand() + "\nWarranty Period: " +getWarantyPeriod() + " weeks " + "\nPrice:" + getPrice() + "\nAvailable products: " + getNumOfItems();
    }
}
