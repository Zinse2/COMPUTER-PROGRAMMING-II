import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

public class ProductReader {

    public static void main(String[] args) {
        // Pick a CSV file.
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileNameExtensionFilter("CSV files", "csv"));

        // Default to project/src for convenience.
        Path start = new File(System.getProperty("user.dir")).toPath().resolve("src");
        chooser.setCurrentDirectory(start.toFile());

        try {
            if (chooser.showOpenDialog(null) != JFileChooser.APPROVE_OPTION) {
                System.out.println("No file selected. Exiting.");
                return;
            }

            Path csvPath = chooser.getSelectedFile().toPath();

            // Read lines into an ArrayList<Product>.
            ArrayList<Product> products = new ArrayList<>();
            try (Scanner inFile = new Scanner(csvPath.toFile())) {
                while (inFile.hasNextLine()) {
                    String line = inFile.nextLine().trim();
                    if (line.isEmpty()) continue;

                    // Expected order: name,description,ID,cost
                    String[] parts = line.split("\\s*,\\s*");
                    if (parts.length != 4) continue;  // Skip malformed rows.

                    String name = parts[0];
                    String description = parts[1];
                    String id = parts[2];
                    double cost = Double.parseDouble(parts[3]);

                    products.add(new Product(name, description, id, cost));
                }
            }

            // Print a formatted table.
            System.out.printf("%-20s %-30s %-12s %10s%n", "Name", "Description", "ID", "Cost");
            System.out.println("----------------------------------------------------------------------");
            for (Product p : products) {
                System.out.printf("%-20s %-30s %-12s %10.2f%n",
                        p.getName(), p.getDescription(), p.getID(), p.getCost());
            }

        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.out.println("Bad cost value in CSV.");
            e.printStackTrace();
        }
    }
}
