import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;
import static java.nio.file.StandardOpenOption.CREATE;

public class ProductWriter {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        ArrayList<String> products = new ArrayList<>();

        System.out.println("== ProductWriter ==");

        boolean more = true;
        while (more) {
            String id   = SafeInput.getNonZeroLenString(in, "Enter Product ID");
            String name = SafeInput.getNonZeroLenString(in, "Enter Name");
            String desc = SafeInput.getNonZeroLenString(in, "Enter Description");
            double cost = SafeInput.getDouble(in, "Enter Cost");

            products.add(id + ", " + name + ", " + desc + ", " + cost);
            more = SafeInput.getYNConfirm(in, "Add another?");
        }

        if (products.isEmpty()) return;

        String fileName = SafeInput.getNonZeroLenString(in, "File name to save (e.g., ProductTestData.txt)");
        if (!fileName.toLowerCase().endsWith(".txt")) fileName += ".txt";

        Path file = Path.of(fileName);
        try (BufferedWriter writer = Files.newBufferedWriter(file, CREATE)) {
            for (String p : products) {
                writer.write(p);
                writer.newLine();
            }
            System.out.println("Saved " + products.size() + " record(s) to " + file.toAbsolutePath());
        } catch (IOException e) {
            System.out.println("Error writing file: " + e.getMessage());
        }
    }
}
