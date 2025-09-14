import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;

public class PersonGenerator {

    public static void main(String[] args) {
        // Collect person records, then write them to a CSV the user names.
        ArrayList<Person> people = new ArrayList<>();
        Scanner in = new Scanner(System.in);

        boolean more = true;
        while (more) {
            String id        = SafeInput.getRegExString(in, "Enter the ID [digits only]: ", "\\d+");
            String firstName = SafeInput.getNonZeroLenString(in, "Enter the First Name: ");
            String lastName  = SafeInput.getNonZeroLenString(in, "Enter the Last Name: ");
            String title     = SafeInput.getNonZeroLenString(in, "Enter the Title: ");
            int yob          = SafeInput.getRangedInt(in, "Enter the Year of Birth ", 1940, 2010);

            people.add(new Person(id, firstName, lastName, title, yob));
            more = SafeInput.getYNConfirm(in, "Add another record? ");
        }

        // Quick echo so the user sees what will be saved
        for (Person p : people) {
            System.out.println(p.toCSV());
        }

        // Ask for output file name and save in project working directory
        String fileName = SafeInput.getNonZeroLenString(in, "Output CSV file name (e.g., people.csv): ");
        Path outPath = Paths.get(System.getProperty("user.dir")).resolve(fileName);

        try (BufferedWriter writer = Files.newBufferedWriter(outPath, CREATE, TRUNCATE_EXISTING)) {
            for (Person p : people) {
                writer.write(p.toCSV());
                writer.newLine();
            }
            System.out.println("Saved " + people.size() + " record(s) to " + outPath.toAbsolutePath());
        } catch (IOException e) {
            System.out.println("Error writing file: " + e.getMessage());
        }
    }
}
