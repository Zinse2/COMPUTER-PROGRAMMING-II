import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;
import static java.nio.file.StandardOpenOption.CREATE;

public class PersonGenerator {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        ArrayList<String> people = new ArrayList<>();

        boolean more = true;
        while (more) {
            String id = SafeInput.getNonZeroLenString(in, "Enter ID");
            String first = SafeInput.getNonZeroLenString(in, "Enter First Name");
            String last = SafeInput.getNonZeroLenString(in, "Enter Last Name");
            String title = SafeInput.getNonZeroLenString(in, "Enter Title");
            int yob = SafeInput.getInt(in, "Enter Year of Birth");

            people.add(id + ", " + first + ", " + last + ", " + title + ", " + yob);
            more = SafeInput.getYNConfirm(in, "Add another?");
        }

        if (people.isEmpty()) return;

        String fileName = SafeInput.getNonZeroLenString(in, "File name to save (e.g., PersonTestData.txt)");
        if (!fileName.toLowerCase().endsWith(".txt")) fileName += ".txt";

        Path file = Path.of(fileName);
        try (BufferedWriter writer = Files.newBufferedWriter(file, CREATE)) {
            for (String p : people) {
                writer.write(p);
                writer.newLine();
            }
            System.out.println("Saved " + people.size() + " record(s).");
        } catch (IOException e) {
            System.out.println("Error writing file.");
        }
    }
}
