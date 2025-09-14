import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

public class PersonReader {

    public static void main(String[] args) {
        // Pick a CSV file
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileNameExtensionFilter("CSV files", "csv"));

        // Default to project/src for convenience
        Path start = new File(System.getProperty("user.dir")).toPath().resolve("src");
        chooser.setCurrentDirectory(start.toFile());

        try {
            if (chooser.showOpenDialog(null) != JFileChooser.APPROVE_OPTION) {
                System.out.println("No file selected. Exiting.");
                return;
            }

            Path csvPath = chooser.getSelectedFile().toPath();

            // Read lines -> ArrayList<Person>
            ArrayList<Person> people = new ArrayList<>();
            try (Scanner inFile = new Scanner(csvPath.toFile())) {
                while (inFile.hasNextLine()) {
                    String line = inFile.nextLine().trim();
                    if (line.isEmpty()) continue;

                    // Expected order: ID,firstName,lastName,title,YOB
                    String[] parts = line.split("\\s*,\\s*");
                    if (parts.length != 5) continue; // skip malformed rows

                    String id        = parts[0];
                    String firstName = parts[1];
                    String lastName  = parts[2];
                    String title     = parts[3];
                    int yob          = Integer.parseInt(parts[4]);

                    people.add(new Person(id, firstName, lastName, title, yob));
                }
            }

            // Formatted table output
            System.out.printf("%-8s %-12s %-12s %-8s %-4s%n", "ID", "FirstName", "LastName", "Title", "YOB");
            System.out.println("-----------------------------------------------------------");
            for (Person p : people) {
                System.out.printf("%-8s %-12s %-12s %-8s %-4d%n",
                        p.getIDNum(), p.getFirstName(), p.getLastName(), p.getTitle(), p.getYOB());
            }

        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.out.println("Bad YOB value in CSV.");
            e.printStackTrace();
        }
    }
}
