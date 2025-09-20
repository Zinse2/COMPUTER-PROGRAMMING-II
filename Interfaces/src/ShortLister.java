import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class ShortLister {
    public static void main(String[] args) throws Exception {
        // 1) Pick a text file
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Select a .txt file");
        chooser.setFileFilter(new FileNameExtensionFilter("Text files", "txt"));
        if (chooser.showOpenDialog(null) != JFileChooser.APPROVE_OPTION) {
            System.out.println("No file selected. Exiting.");
            return;
        }
        File file = chooser.getSelectedFile();

        // 2) Read words (simple split on whitespace)
        String content = Files.readString(file.toPath());
        String[] tokens = content.split("\\s+");

        // 3) Filter using ShortWordFilter (length < 5)
        Filter f = new ShortWordFilter(); // default = <=4 letters
        List<String> shortWords = new ArrayList<>();
        for (String t : tokens) {
            if (f.accept(t)) shortWords.add(t);
        }

        // 4) Display results
        System.out.println("File: " + file.getAbsolutePath());
        System.out.println("Total tokens: " + tokens.length);
        System.out.println("Short words (len < 5): " + shortWords.size());
        System.out.println("-".repeat(60));
        int col = 0;
        for (String w : shortWords) {
            System.out.printf("%-12s", w.replaceAll("[^A-Za-z]", ""));
            if (++col % 6 == 0) System.out.println();
        }
        if (col % 6 != 0) System.out.println();
    }
}
