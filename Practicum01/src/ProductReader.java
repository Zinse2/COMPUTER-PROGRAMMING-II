import javax.swing.JFileChooser;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class ProductReader {
    public static void main(String[] args) {
        System.out.println("== ProductReader ==");

        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Select a Product data file...");
        if (chooser.showOpenDialog(null) != JFileChooser.APPROVE_OPTION) {
            System.out.println("No file selected. Exiting.");
            return;
        }

        Path file = chooser.getSelectedFile().toPath();
        ArrayList<String> lines = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(file)) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.isBlank()) lines.add(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
            return;
        }

        // Simple formatted columns
        System.out.printf("%-6s %-14s %-40s %10s%n", "ID#", "Name", "Description", "Cost");
        System.out.println("====================================================================================");
        for (String rec : lines) {
            String[] parts = rec.split(",\\s*", 4); // ID, Name, Desc, Cost
            if (parts.length == 4) {
                System.out.printf("%-6s %-14s %-40s %10s%n",
                        parts[0], parts[1], parts[2], parts[3]);
            } else {
                System.out.println("Skipping malformed line: " + rec);
            }
        }
    }
}
