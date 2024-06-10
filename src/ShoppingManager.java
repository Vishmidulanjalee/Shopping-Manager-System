import java.util.ArrayList;

public interface ShoppingManager {
    void managerMenu();
    void addProduct();
    void removeProduct();
    void printProductList();
    void saveToFile();
    ArrayList<Product> getProductList();
    ArrayList<Product> readFromFile();
    void openGUI();
}
