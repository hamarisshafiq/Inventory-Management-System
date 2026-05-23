
import java.util.Scanner;
import java.io.*;

public class InventoryManagement {

    static int[] itemIDs = new int[100];
    static String[] itemNames = new String[100];
    static int[] quantities = new int[100];
    static double[] prices = new double[100];
    static int itemCount = 0;

    // Price validation using throw and throws
    public static void validatePrice(double price) throws Exception {
        if (price < 0) {
            throw new Exception("Price cannot be negative!");
        }
    }

    // Function for Add Item
    public static void addItem(Scanner input) {
        
        System.out.print("Enter Item ID: ");
        itemIDs[itemCount] = input.nextInt();
        input.nextLine();

        System.out.print("Enter Item Name: ");
        itemNames[itemCount] = input.nextLine();

        System.out.print("Enter Quantity: ");
        quantities[itemCount] = input.nextInt();

        System.out.print("Enter Price: ");
        double price = input.nextDouble();

        try {
            validatePrice(price);
            prices[itemCount] = price;
            itemCount++;
            
            System.out.println("Item added successfully!");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Function for View Items
    public static void viewItems() {
        if (itemCount == 0) {
            System.out.println("Inventory is empty.");
        } else {
            System.out.println("\n=========== Inventory Items ===========");
            System.out.printf("%-10s %-20s %-10s %-10s\n", "Item ID", "Item Name", "Quantity", "Price");

            for (int i = 0; i < itemCount; i++) {
                System.out.printf("%-10d %-20s %-10d %-10.2f\n",
                        itemIDs[i], itemNames[i], quantities[i], prices[i]);
            }
        }
    }

    // Function for Search Item
    public static void searchItem(Scanner input) {
        if (itemCount == 0) {
            System.out.println("Inventory is empty.");
        } else {
            System.out.println("Search by:");
            System.out.println("1. Item Name");
            System.out.println("2. Item ID");
            System.out.print("Enter your choice: ");
            int searchChoice = input.nextInt();
            input.nextLine();

            if (searchChoice == 1) {
                System.out.print("Enter Item Name to search: ");
                String searchName = input.nextLine();
                
                boolean found = false;

                for (int i = 0; i < itemCount; i++) {
                    if (itemNames[i].equals(searchName)) {
                        System.out.println("Item Found:");
                        System.out.println("ID: " + itemIDs[i]);
                        System.out.println("Name: " + itemNames[i]);
                        System.out.println("Quantity: " + quantities[i]);
                        System.out.println("Price: " + prices[i]);
                        
                        found = true;
                        break;
                    }
                }

                if (!found) {
                    System.out.println("Item not found with name: " + searchName);
                }

            } else if (searchChoice == 2) {
                System.out.print("Enter Item ID to search: ");
                int searchID = input.nextInt();
                
                boolean found = false;

                for (int i = 0; i < itemCount; i++) {
                    if (itemIDs[i] == searchID) {
                        System.out.println("Item Found:");
                        System.out.println("ID: " + itemIDs[i]);
                        System.out.println("Name: " + itemNames[i]);
                        System.out.println("Quantity: " + quantities[i]);
                        System.out.println("Price: " + prices[i]);
                        
                        found = true;
                        break;
                    }
                }

                if (!found) {
                    System.out.println("Item not found with ID: " + searchID);
                }

            } else {
                System.out.println("Invalid search choice.");
            }
        }
    }

    // Function for Update Item
    public static void updateItem(Scanner input) {
        System.out.print("Enter Item ID to update: ");
        int updateID = input.nextInt();
        
        boolean updated = false;

        for (int i = 0; i < itemCount; i++) {
            if (itemIDs[i] == updateID) {
                input.nextLine();
                System.out.print("Enter new Item Name: ");
                itemNames[i] = input.nextLine();

                System.out.print("Enter new Quantity: ");
                quantities[i] = input.nextInt();

                System.out.print("Enter new Price: ");
                double newPrice = input.nextDouble();

                try {
                    validatePrice(newPrice);
                    prices[i] = newPrice;
                    System.out.println("Item updated successfully!");
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }

                updated = true;
                break;
            }
        }

        if (!updated) {
            System.out.println("Item not found with ID: " + updateID);
        }
    }

    // Function for Delete Item
    public static void deleteItem(Scanner input) {
        System.out.print("Enter Item ID to delete: ");
        int deleteID = input.nextInt();
        
        boolean deleted = false;

        for (int i = 0; i < itemCount; i++) {
            if (itemIDs[i] == deleteID) {
                
                for (int j = i; j < itemCount - 1; j++) {
                    itemIDs[j] = itemIDs[j + 1];
                    itemNames[j] = itemNames[j + 1];
                    quantities[j] = quantities[j + 1];
                    prices[j] = prices[j + 1];
                }
                itemCount--;
                
                System.out.println("Item deleted successfully!");
                
                deleted = true;
                break;
            }
        }

        if (!deleted) {
            System.out.println("Item not found with ID: " + deleteID);
        }
    }

    // Function for Save Inventory to File
    public static void saveToFile() {
        try {
            FileWriter writer = new FileWriter("inventory.txt");
            for (int i = 0; i < itemCount; i++) {
                writer.write(itemIDs[i] + "," + itemNames[i] + "," + quantities[i] + "," + prices[i] + "\n");
            }
            writer.close();
            System.out.println("Inventory saved to inventory.txt successfully!");
            
        } catch (IOException e) {
            System.out.println("Error saving inventory to file.");
        }
    }

    // Function for Load Inventory from File
    public static void loadFromFile() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("inventory.txt"));
            String line;
            itemCount = 0;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                itemIDs[itemCount] = Integer.parseInt(parts[0]);
                itemNames[itemCount] = parts[1];
                quantities[itemCount] = Integer.parseInt(parts[2]);
                prices[itemCount] = Double.parseDouble(parts[3]);
                itemCount++;
            }
            reader.close();
            System.out.println("Inventory loaded from inventory.txt successfully!");
        } catch (IOException e) {
            System.out.println("Error loading inventory from file.");
        }
    }

    // Function for Create Bill / Purchase Items
    public static void createBill(Scanner input) {
        double totalBill = 0;
        String morePurchase;

        do {
            System.out.print("Enter Item ID to purchase: ");
            int purchaseID = input.nextInt();
            boolean purchaseFound = false;

            for (int i = 0; i < itemCount; i++) {
                if (itemIDs[i] == purchaseID) {
                    System.out.println("Item: " + itemNames[i]);
                    System.out.println("Available Quantity: " + quantities[i]);
                    System.out.print("Enter quantity to purchase: ");
                    int qty = input.nextInt();

                    if (qty <= quantities[i]) {
                        double itemTotal = qty * prices[i];
                        totalBill += itemTotal;
                        quantities[i] -= qty;
                        System.out.println("Added to bill. Item Total: " + itemTotal);
                    } else {
                        System.out.println("Not enough quantity available.");
                    }

                    purchaseFound = true;
                    break;
                }
            }

            if (!purchaseFound) {
                System.out.println("Item not found with ID: " + purchaseID);
            }

            System.out.print("Do you want to purchase another item? (yes/no): ");
            input.nextLine();
            morePurchase = input.nextLine();

        } while (morePurchase.equals("yes"));

        System.out.println("Total Bill Amount: " + totalBill);
    }

    
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n========= Inventory Management System =========");
            System.out.println("1. Add New Item");
            System.out.println("2. View All Items");
            System.out.println("3. Search Item");
            System.out.println("4. Update Item");
            System.out.println("5. Delete Item");
            System.out.println("6. Save Inventory to File");
            System.out.println("7. Load Inventory from File");
            System.out.println("8. Create Bill / Purchase Items");
            System.out.println("9. Exit");
            System.out.println("===============================================");
            
            System.out.print("Enter your choice: ");
            choice = input.nextInt();
            input.nextLine();

            switch (choice) {
                case 1:
                    addItem(input);
                    break;
                    
                case 2:
                    viewItems();
                    break;
                    
                case 3:
                    searchItem(input);
                    break;
                    
                case 4:
                    updateItem(input);
                    break;
                    
                case 5:
                    deleteItem(input);
                    break;
                    
                case 6:
                    saveToFile();
                    break;
                    
                case 7:
                    loadFromFile();
                    break;
                    
                case 8:
                    createBill(input);
                    break;
                    
                case 9:
                    System.out.println("Exiting... Thank you!");
                    break;
                    
                default:
                    System.out.println("Invalid choice. Please try again.");
            }

        } while (choice != 9);

        input.close();
    }
}

