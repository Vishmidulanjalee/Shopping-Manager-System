import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class ShoppingCart extends JFrame {

    private static JTable productTable; //declaration of a JTab
    private JComboBox<String> categoryComboBox; 
    private JTextArea additionalTextArea;
    private ArrayList<Product> productList;
    private ArrayList<Product> shoppingCart;

    ShoppingCart(ShoppingManager shoppingManager) {
        this.productList = shoppingManager.getProductList();
        setTitle("Westminster Shopping Center");
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setSize(800, 500);
        setLayout(new BorderLayout());


        this.productList = (productList != null) ? productList : new ArrayList<>(); // Handle null productList
        this.shoppingCart = new ArrayList<>(); // Initialize the shopping cart


        // Create columns for the table
        String[] columns = {"Product ID", "Product Name", "Category", "Price", "Info"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);

        // Create the table
        productTable = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(productTable);
        scrollPane.setPreferredSize(new Dimension(800, 200));

        // Create a combo box for selecting category
        String[] categoryOptions = {"All", "Electronics", "Clothing"};
        categoryComboBox = new JComboBox<>(categoryOptions);
        categoryComboBox.addActionListener(e -> filterByCategory((String) categoryComboBox.getSelectedItem()));

        // Update the table with the initial product list
        updateTable(this.productList);

        // Create a panel for the combo box
        JPanel comboBoxPanel = new JPanel();
        comboBoxPanel.add(new JLabel("Select Category: "));
        comboBoxPanel.add(categoryComboBox);

        // Create a panel for the combo box and the table in a vertical arrangement
        JPanel comboAndTablePanel = new JPanel(new BorderLayout());
        comboAndTablePanel.add(comboBoxPanel, BorderLayout.NORTH);
        comboAndTablePanel.add(scrollPane, BorderLayout.CENTER);
        comboAndTablePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Add padding

        // Create a panel for additional content (replace this with your own content)
        JPanel additionalPanel = new JPanel(new BorderLayout());
        additionalPanel.setPreferredSize(new Dimension(800, 300)); // Set the preferred size as needed

        // Create a JTextArea for displaying product details
        additionalTextArea = new JTextArea();
        additionalTextArea.setEditable(false);
        JScrollPane additionalScrollPane = new JScrollPane(additionalTextArea);

        additionalPanel.add(additionalScrollPane, BorderLayout.CENTER);

        // Create an "Add to Cart" button
        JButton addToCartButton = new JButton("Add to Cart");
        addToCartButton.addActionListener(e -> addToCart()); // Implement the addToCart method

        // Create a panel for the "Add to Cart" button with FlowLayout
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(addToCartButton);

        // Add the button panel to the additional panel at the bottom
        additionalPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Create a "Shopping Cart" button
        JButton shoppingCartButton = new JButton("Shopping Cart");
        shoppingCartButton.addActionListener(e -> openShoppingCartWindow());

        // Create a panel for the "Shopping Cart" button at the top right corner
        JPanel shoppingCartButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        shoppingCartButtonPanel.add(shoppingCartButton);

        // Create a main panel for the comboAndTablePanel and additionalPanel in a vertical arrangement
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(comboAndTablePanel, BorderLayout.CENTER);
        mainPanel.add(additionalPanel, BorderLayout.SOUTH);
        mainPanel.add(shoppingCartButtonPanel, BorderLayout.NORTH);

        // Add the main panel to the main frame
        add(mainPanel, BorderLayout.CENTER);

        // Add a ListSelectionListener to the table to update additional content based on the selected row
        productTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = productTable.getSelectedRow();
                    if (selectedRow != -1) {
                        // Get the data from the selected row
                        String productDetails = getProductDetailsFromRow(selectedRow);

                        // Update the additional panel with the selected product details
                        additionalTextArea.setText(productDetails);
                    }
                }
            }
        });

        // Set a custom renderer to highlight rows in red if available items are below 3
        productTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                // Check if the "Available Items" are below 3
                int availableItems = getProductList().get(row).getNumOfItems();
                if (availableItems < 3) {
                    c.setBackground(Color.RED);
                    c.setForeground(Color.WHITE);
                } else {
                    c.setBackground(table.getBackground());
                    c.setForeground(table.getForeground());
                }

                return c;
            }
        });

        pack();
        setLocationRelativeTo(null);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    }

    public ArrayList<Product> getProductList() {
        return productList;
    }

    private void filterByCategory(String selectedCategory) {
        ArrayList<Product> filteredList;

        if (selectedCategory.equals("All")) {
            filteredList = productList;
        } else {
            filteredList = new ArrayList<>();
            for (Product product : productList) {
                if (product.getCategory().equals(selectedCategory)) {
                    filteredList.add(product);
                }
            }
        }

        updateTable(filteredList);
    }
    private String getProductDetailsFromRow(int row) {
        String productID = productTable.getValueAt(row, 0).toString();
        String productName = productTable.getValueAt(row, 1).toString();
        String category = productTable.getValueAt(row, 2).toString();
        String price = productTable.getValueAt(row, 3).toString();
        int availableItems = 0;

        // Find the selected product in the original list
        for (Product product : productList) {
            if (product.getProductID().equals(productID)) {
                availableItems = product.getNumOfItems();
                break;
            }
        }

        StringBuilder detailsBuilder = new StringBuilder("*Selected Product - Details*\n");
        detailsBuilder.append(String.format("\nProduct ID: %s\nProduct Name: %s\nCategory: %s\nPrice: %s", productID, productName, category, price));

        // Check the category and include specific details
        if (category.equals("Electronics")) {
            // Find the corresponding Electronics product in the original list
            for (Product product : productList) {
                if (product.getProductID().equals(productID) && product instanceof Electronics) {
                    Electronics electronicsProduct = (Electronics) product;
                    detailsBuilder.append(String.format("\nBrand: %s\nWarranty Period: %d weeks", electronicsProduct.getBrand(), electronicsProduct.getWarantyPeriod()));
                    break;
                }
            }
        } else if (category.equals("Clothing")) {
            // Find the corresponding Clothing product in the original list
            for (Product product : productList) {
                if (product.getProductID().equals(productID) && product instanceof Clothing) {
                    Clothing clothingProduct = (Clothing) product;
                    detailsBuilder.append(String.format("\nSize: %s\nColor: %s", clothingProduct.getSize(), clothingProduct.getColour()));
                    break;
                }
            }
        }
        detailsBuilder.append(String.format("\nAvailable Items: %d", availableItems));

        return detailsBuilder.toString();
    }

    private void addToCart() {
        // Implement the logic to add the selected product to the shopping cart
        int selectedRow = productTable.getSelectedRow();
        if (selectedRow != -1) {
            Product selectedProduct = productList.get(selectedRow);

            // Check if there are available items to add to the cart
            if (selectedProduct.getNumOfItems() > 0) {
                // Reduce the quantity by 1
                selectedProduct.setNumOfItems(selectedProduct.getNumOfItems() - 1);

                // Add the selected product to the shopping cart
                shoppingCart.add(selectedProduct);

                // Update the table to reflect the reduced quantity
                updateTable(productList);

                JOptionPane.showMessageDialog(this, "Item added to the cart successfully");
            } else {
                JOptionPane.showMessageDialog(this, "No more available items for " + selectedProduct.getProductName(), "Out of Stock", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    private double applyFirstPurchaseDiscount(double originalPrice) {
        return originalPrice * 0.1; // 10% discount

    }

    private double applyCategoryDiscount(double originalPrice, String category) {
        // Check the number of products in the same category
        long productsInCategory = shoppingCart.stream()
                .filter(product -> product.getCategory().equals(category))
                .count();

        // Apply the category discount only if there are at least three products in the category
        if (productsInCategory >= 3) {
            return originalPrice * 0.2; // 20% discount
        } else {
            return 0.0; // No discount if fewer than three products in the category
        }
    }

    public static void updateTable(ArrayList<Product> updatedList) {
        if (updatedList == null) {
            return; // Avoid NullPointerException
        }

        DefaultTableModel model = (DefaultTableModel) productTable.getModel();
        model.setRowCount(0);

        for (Product product : updatedList) {
            String productID = product.getProductID();
            String productName = product.getProductName();
            String category = product.getCategory();
            double price = product.getPrice();
            String info = getProductInfo(product);

            Object[] rowData = {productID, productName, category, price, info};
            model.addRow(rowData);
        }
    }

    private static String getProductInfo(Product product) {
        if (product instanceof Electronics) {
            Electronics electronicsProduct = (Electronics) product;
            return String.format(" %s,   %d weeks warranty", electronicsProduct.getBrand(), electronicsProduct.getWarantyPeriod());
        } else if (product instanceof Clothing) {
            Clothing clothingProduct = (Clothing) product;
            return String.format(" %s,   %s ", clothingProduct.getSize(), clothingProduct.getColour());
        } else {
            return "";
        }
    }

    private void openShoppingCartWindow() {
        // Create a new window to display the shopping cart items
        JFrame shoppingCartFrame = new JFrame("Shopping Cart");
        shoppingCartFrame.setSize(600, 400);
        shoppingCartFrame.setLayout(new BorderLayout());

        // Create columns for the shopping cart table
        String[] cartColumns = {"Product", "Quantity", "Price"};
        DefaultTableModel cartModel = new DefaultTableModel(cartColumns, 0);

        // Create the shopping cart table
        JTable cartTable = new JTable(cartModel);
        JScrollPane cartScrollPane = new JScrollPane(cartTable);

        // Add the shopping cart table to the frame
        shoppingCartFrame.add(cartScrollPane, BorderLayout.CENTER);

        // Iterate through products in the shopping cart and update the table
        for (Product cartProduct : shoppingCart) {
            int indexOfTheRow = findRowIndex(cartProduct, cartModel);

            if (indexOfTheRow != -1) {
                // Product is already in the table, update the quantity in the existing row
                int newQuantity = getProductQuantity(cartProduct);
                cartModel.setValueAt(newQuantity, indexOfTheRow, 1);
            } else {
                // Product is not in the table, add a new row
                Object[] cartRowData = getProductDetailsForCart(cartProduct, getProductQuantity(cartProduct));
                cartModel.addRow(cartRowData);
            }
        }

        double totalCost = calculateTotalCost();
        double firstPurchaseDiscount = applyFirstPurchaseDiscount(totalCost);
        double clothingDiscount = applyCategoryDiscount(totalCost, "Clothing");
        double electronicsDiscount = applyCategoryDiscount(totalCost, "Electronics");

        double categoryDiscount = clothingDiscount + electronicsDiscount;

        double finalTotal = totalCost - firstPurchaseDiscount - categoryDiscount;

        JLabel totalCostLabel = new JLabel("Total Cost: Rs. " + totalCost);
        JLabel firstPurchaseDiscountLabel = new JLabel("First Purchase discount (10%): Rs. " + firstPurchaseDiscount);
        JLabel categoryDiscountLabel = new JLabel("Category Discount (20%): Rs. " + categoryDiscount);
        JLabel finalTotalLabel = new JLabel("Final Total : Rs. " + finalTotal);

        // Combine total cost and discount labels into a single panel with vertical orientation
        JPanel costAndDiscountPanel = new JPanel();
        costAndDiscountPanel.setLayout(new BoxLayout(costAndDiscountPanel, BoxLayout.Y_AXIS));
        costAndDiscountPanel.add(totalCostLabel);
        costAndDiscountPanel.add(firstPurchaseDiscountLabel);
        costAndDiscountPanel.add(categoryDiscountLabel);
        costAndDiscountPanel.add(finalTotalLabel);

        // Add the combined panel to the frame
        shoppingCartFrame.add(costAndDiscountPanel, BorderLayout.AFTER_LAST_LINE);

        shoppingCartFrame.setVisible(true);
    }

    private int getProductQuantity(Product product) {
        // Calculate and return the total quantity for the given product in the shopping cart
        int totalQuantity = 0;
        for (Product cartProduct : shoppingCart) {
            if (cartProduct == product) {
                totalQuantity++;
            }
        }
        return totalQuantity;
    }

    private int findRowIndex(Product product, DefaultTableModel model) {
        for (int i = 0; i < model.getRowCount(); i++) {
            if (model.getValueAt(i, 0).equals(product.getProductID())) {
                return i;
            }
        }
        return -1;
    }

    private double calculateTotalCost() {
        double totalCost = 0.0;

        for (Product cartProduct : shoppingCart) {
            totalCost += cartProduct.getPrice();
        }

        return totalCost;
    }

    private Object[] getProductDetailsForCart(Product product, int numOfItems) {
        String productName = product.getProductName();
        int quantity = numOfItems;
        double price = product.getPrice();

        // Check the category and include specific details
        if (product instanceof Electronics) {
            Electronics electronicsProduct = (Electronics) product;
            return new Object[]{productName + "\n  Brand: " + electronicsProduct.getBrand() + "\n Warranty: " + electronicsProduct.getWarantyPeriod() + " weeks", quantity, price};
        } else if (product instanceof Clothing) {
            Clothing clothingProduct = (Clothing) product;
            return new Object[]{productName + "\n  Size: " + clothingProduct.getSize() + "\nColor: " + clothingProduct.getColour(), quantity, price};
        } else {
            return new Object[]{productName, quantity, price};
        }
    }
}

