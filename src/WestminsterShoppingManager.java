import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class WestminsterShoppingManager implements ShoppingManager {
    static ArrayList<Product> productList = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);
    static ShoppingCart shoppingCartGUI;

    public static void main(String[] args) throws Exception {
        shoppingCartGUI = new ShoppingCart(new WestminsterShoppingManager());
        WestminsterShoppingManager shoppingManager = new WestminsterShoppingManager();
        shoppingManager.displayMenu();
    }

    /**
     * Display the main menu
     */
    public void displayMenu() {
        String userType;

        do {
            System.out.println("Please select your user type:");
            System.out.println("1. Manager");
            System.out.println("2. Customer");
            System.out.println("3. Terminate the system");
            System.out.print("Enter your choice: ");
            userType = scanner.nextLine();

            switch (userType) {
                case "1":
                    managerMenu();
                    break;
                case "2":
                    getCustomerDetails();
                    break;
                case "3":
                    saveToFile();
                    System.out.println("Exiting the system. Goodbye!");
                    break;
                default:
                    System.out.println("INVALID INPUT. Please enter a valid choice (1-3).");
            }

        } while (!isValidUserType(userType));
    }

    private static boolean isValidUserType(String userType) {
        return userType.equals("1") || userType.equals("2") || userType.equals("3");
    }

    /**
     * Get customer details from the customer
     */
    public  void getCustomerDetails(){
        System.out.print("Enter your userID: ");
        String userID = scanner.nextLine();

        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        User user = new User(userID, password);

        System.out.println("User details : " + user.getUsername() + ", " + user.getPassword());
        openGUI();
    }

    /**
     * Display the manager Menu
     */
    public void managerMenu() {
        String option = "";

        do {
            System.out.println("\n__Westminster Shopping Manager System__");
            System.out.println("1. Add product to the system");
            System.out.println("2. Remove a product from system");
            System.out.println("3. Print the product list");
            System.out.println("4. Save to the text file");
            System.out.println("5. Read from the text file");
            System.out.println("6. Exit from the system");

            System.out.print("Enter your choice (1-6): ");
            option = scanner.nextLine();

            switch (option) {
                case "1":
                    addProduct();
                    break;
                case "2":
                    removeProduct();
                    break;
                case "3":
                    printProductList();
                    break;
                case "4":
                    saveToFile();
                    break;
                case "5":
                    readFromFile();
                    break;
                case "6":
                    displayMenu();
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 6.");
            }
        } while (!option.equals("6"));
    }

    /**
     * Add products to the Product list
     */
    public void addProduct() {
        System.out.println("Choose product category:");
        System.out.println("1. Electronics");
        System.out.println("2. Clothing");

        if (productList.size() > 50) {
            System.out.println("Exceeded product limit. You cannot add more than 5 products.");
            return;
        }

        try {
            System.out.print("Enter your choice (1 or 2): ");
            int categoryChoice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            String category;
            if (categoryChoice == 1) {
                category = "Electronics";
                addElectronicsProduct();
            } else if (categoryChoice == 2) {
                category = "Clothing";
                addClothingProduct();
            } else {
                System.out.println("Invalid choice. Please enter 1 or 2.");
                return;
            }


            ShoppingCart.updateTable(productList);
            System.out.println(category + " product added successfully!");
        } catch (Exception e) {
            System.out.println("Invalid input. Please enter a valid choice.");
            scanner.nextLine(); // Consume the invalid input
        }
    }

    /**
     * Add electoronics products to the product List
     */
    private static void addElectronicsProduct() {
        try {
            System.out.print("Enter product ID: ");
            String productId = scanner.nextLine();

            System.out.print("Enter product name: ");
            String productName = scanner.nextLine();

            System.out.print("Enter number of items: ");
            int numOfItems = scanner.nextInt();

            System.out.print("Enter price: Rs. ");
            double price = scanner.nextDouble();
            scanner.nextLine(); // Consume the newline character

            System.out.print("Enter brand: ");
            String brand = scanner.nextLine();

            System.out.print("Enter warranty period (in weeks): ");
            int warrantyPeriod = scanner.nextInt();

            Electronics electronicsProduct = new Electronics(productId, productName, numOfItems, price, brand, warrantyPeriod);
            electronicsProduct.setCategory("Electronics");


            productList.add(electronicsProduct);

            System.out.println("Electronics product added successfully!");
            electronicsProduct.displayDetails();

        } catch (Exception e) {
            System.out.println("Invalid input. Please enter valid values.");
            scanner.nextLine(); // Consume the invalid input
        }
    }

    /**
     * Add clothing products to the Product List
     */
    private static void addClothingProduct() {
        try {
            System.out.print("Enter product ID: ");
            String productId = scanner.nextLine();

            System.out.print("Enter product name: ");
            String productName = scanner.nextLine();

            System.out.print("Enter number of items: ");
            int numOfItems = scanner.nextInt();

            System.out.print("Enter price: ");
            double price = scanner.nextDouble();
            scanner.nextLine(); // Consume the newline character

            System.out.print("Enter size: ");
            String size = scanner.nextLine();

            System.out.print("Enter color: ");
            String color = scanner.nextLine();

            Clothing clothingProduct = new Clothing(productId, productName, numOfItems, price);
            clothingProduct.setSize(size);
            clothingProduct.setColour(color);
            clothingProduct.setCategory("Clothing");

            productList.add(clothingProduct);

            System.out.println("Clothing product added successfully!");
            clothingProduct.displayDetails();
        } catch (Exception e) {
            System.out.println("Invalid input. Please enter valid values.");
            scanner.nextLine(); // Consume the invalid input
        }

    }

    /**
     * Remove Products from the product List
     */
    public void removeProduct() {
        System.out.print("Enter the product ID or name to remove: ");
        String productToRemove = scanner.nextLine();

        // Check if the product exists in the productList
        boolean productFound = false;
        for (Product product : productList) {
            if (product.getProductID().equals(productToRemove) || product.getProductName().equals(productToRemove)) {
                productList.remove(product);
                productFound = true;
                System.out.println("Product removed successfully!");
                // Print the total number of products after removal
                System.out.println("Total number of products: " + productList.size());
                break;
            }
        }

        if (!productFound) {
            System.out.println("Product not found. No product removed.");
        }
    }

    /**
     *  Print product List according to the alphabetical order by product ID
     */
    public void printProductList() {
        readFromFile();
        System.out.println("Product List:");

        if (productList.isEmpty()) {
            System.out.println("No products in the list.");
        } else {
            // Sort the productList alphabetically by product name
            Collections.sort(productList, Comparator.comparing(Product::getProductID));

            for (Product product : productList) {
                System.out.println(product);
                System.out.println("---------------------------");
            }
        }
    }

    /**
     * Save products in the File "ProductList.txt"
     */
    public void saveToFile() {
        try (FileWriter writer = new FileWriter("productList.txt")) {
            for (Product product : productList) {
                writer.write(product.toString());
                writer.write("\n"); // Adding a newline between product entries
            }

            System.out.println("Product list saved to file successfully.");

        } catch (IOException e) {
            System.out.println("Error: Unable to save product list to file.");
            e.printStackTrace();
        }
    }

    /**
     *Reads product details from a file and populates the productList.
     * @return An ArrayList of Product objects loaded from the file.
     */
    public  ArrayList<Product> readFromFile() {
        ArrayList<Product> loadedProductList = new ArrayList<>();

        try (FileReader fileReader = new FileReader("productList.txt");
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] productDetails = line.split(";");

                // Check if the array has enough elements before accessing them
                if (productDetails.length < 7) {
                    System.out.println( line);
                    continue; // Skip this line and move to the next one
                }

                String productType = productDetails[0];
                String productID = productDetails[1];
                String productName = productDetails[2];
                int numOfItems = Integer.parseInt(productDetails[3]);
                double price = Double.parseDouble(productDetails[4]);

                // Add the specific product details based on the product type
                switch (productType) {
                    case "Clothing":
                        String size = productDetails[5];
                        String color = productDetails[6];
                        Clothing clothingProduct = new Clothing(productID, productName, numOfItems, price);
                        clothingProduct.setSize(size);
                        clothingProduct.setColour(color);
                        productList.add(clothingProduct);
                        break;
                    case "Electronics":
                        String brand = productDetails[5];
                        int warrantyPeriod = Integer.parseInt(productDetails[6]);
                        Electronics electronicsProduct = new Electronics(productID, productName, numOfItems, price, brand, warrantyPeriod);
                        productList.add(electronicsProduct);
                        break;
                }
            }

            System.out.println("Product list read from file successfully.");

        } catch (FileNotFoundException e) {
            System.out.println("File not found. No existing product details loaded.");
        } catch (IOException e) {
            System.out.println("Error reading from file.");
            e.printStackTrace();
        }
        return productList;
    }

    /**
     * Open the Graphical User Interface
     */
    public void openGUI() {
        ShoppingCart.updateTable(productList);
        shoppingCartGUI.setVisible(true);
        displayMenu();
    }

    public ArrayList<Product> getProductList() {
        return productList;
    }

}