import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.Serializable;

public class Product implements Serializable {

    private String name;
    private String description;
    private String id;    // should never change
    private double cost;

    // Fixed lengths (in characters)
    public static final int NAME_LENGTH = 35;
    public static final int DESC_LENGTH = 75;
    public static final int ID_LENGTH = 6;

    // Each char is 2 bytes in writeChar/readChar + 8 bytes for double
    public static final int RECORD_SIZE = (NAME_LENGTH + DESC_LENGTH + ID_LENGTH) * 2 + 8;

    public Product() {
    }

    public Product(String name, String description, String id, double cost) {
        this.name = name;
        this.description = description;
        this.id = id;
        this.cost = cost;
    }

    // Getters / setters
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getId() {
        return id;
    }

    public double getCost() {
        return cost;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    // Utility: pad or trim a string to exactly len chars
    private static String fixLength(String s, int len) {
        if (s == null) {
            s = "";
        }
        s = s.trim();
        if (s.length() > len) {
            return s.substring(0, len);
        }
        StringBuilder sb = new StringBuilder(s);
        while (sb.length() < len) {
            sb.append(' ');
        }
        return sb.toString();
    }

    // Write this product as a fixed-length record
    public void writeToFile(RandomAccessFile raf) throws IOException {
        // name
        String fixedName = fixLength(name, NAME_LENGTH);
        for (int i = 0; i < NAME_LENGTH; i++) {
            raf.writeChar(fixedName.charAt(i));
        }

        // description
        String fixedDesc = fixLength(description, DESC_LENGTH);
        for (int i = 0; i < DESC_LENGTH; i++) {
            raf.writeChar(fixedDesc.charAt(i));
        }

        // id
        String fixedId = fixLength(id, ID_LENGTH);
        for (int i = 0; i < ID_LENGTH; i++) {
            raf.writeChar(fixedId.charAt(i));
        }

        // cost
        raf.writeDouble(cost);
    }

    // Read a product from the current file pointer
    public static Product readFromFile(RandomAccessFile raf) throws IOException {
        char[] nameChars = new char[NAME_LENGTH];
        for (int i = 0; i < NAME_LENGTH; i++) {
            nameChars[i] = raf.readChar();
        }

        char[] descChars = new char[DESC_LENGTH];
        for (int i = 0; i < DESC_LENGTH; i++) {
            descChars[i] = raf.readChar();
        }

        char[] idChars = new char[ID_LENGTH];
        for (int i = 0; i < ID_LENGTH; i++) {
            idChars[i] = raf.readChar();
        }

        double cost = raf.readDouble();

        String name = new String(nameChars).trim();
        String desc = new String(descChars).trim();
        String id = new String(idChars).trim();

        return new Product(name, desc, id, cost);
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", id='" + id + '\'' +
                ", cost=" + cost +
                '}';
    }
}
