import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;

public class ProductWriter {

    public static void main(String[] args) {
        // Collect product records, then write them to a CSV the user names.
        ArrayList<Product> products = new ArrayList<>();
        Scanner in = new Scanner(System.in);

        boolean more = true;
        while (more) {
            // Basic prompts; keep ID free-form unless you want a regex
            String id          = SafeInput.getNonZeroLenString(in, "Enter the ID");
            String name        = SafeInput.getNonZeroLenString(in, "Enter the Name");
            String description = SafeInput.getNonZeroLenString(in, "Enter the Description");
            double cost        = SafeInput.getDouble(in, "Enter the Cost");

            products.add(new Product(name, description, id, cost));
            more = SafeInput.getYNConfirm(in, "Add another record? ");
        }

        // Quick echo so the user can see what will be saved
        for (Product p : products) {
            System.out.println(p.toCSV());
        }

        // Ask for output file name and save in project working directory
        String fileName = SafeInput.getNonZeroLenString(in, "Output CSV file name (e.g., products.csv):");
        Path outPath = Paths.get(System.getProperty("user.dir")).resolve(fileName);

        try (BufferedWriter writer = Files.newBufferedWriter(outPath, CREATE, TRUNCATE_EXISTING)) {
            for (Product p : products) {
                writer.write(p.toCSV());
                writer.newLine();
            }
            System.out.println("Saved " + products.size() + " record(s) to " + outPath.toAbsolutePath());
        } catch (IOException e) {
            System.out.println("Error writing file: " + e.getMessage());
        }
    }
}
