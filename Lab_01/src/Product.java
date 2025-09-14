public class Product {
    private String name;
    private String description;
    private String ID;
    private double cost;

    // Constructor with all fields
    public Product(String name, String description, String ID, double cost) {
        this.name = name;
        this.description = description;
        this.ID = ID;
        this.cost = cost;
    }

    // Getters
    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getID() { return ID; }
    public double getCost() { return cost; }

    // Setters
    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setID(String ID) { this.ID = ID; }
    public void setCost(double cost) { this.cost = cost; }

    // Readable string version of the object
    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", ID='" + ID + '\'' +
                ", cost=" + cost +
                '}';
    }

    // CSV output: name,description,ID,cost
    public String toCSV() {
        return name + "," + description + "," + ID + "," + cost;
    }

    // JSON output
    public String toJSON() {
        return "{"
                + "\"ID\":\"" + escapeJson(ID) + "\","
                + "\"name\":\"" + escapeJson(name) + "\","
                + "\"description\":\"" + escapeJson(description) + "\","
                + "\"cost\":" + cost
                + "}";
    }

    // XML output
    public String toXML() {
        return "<Product>"
                + "<ID>" + escapeXml(ID) + "</ID>"
                + "<Name>" + escapeXml(name) + "</Name>"
                + "<Description>" + escapeXml(description) + "</Description>"
                + "<Cost>" + cost + "</Cost>"
                + "</Product>";
    }

    // Helpers for json/xml
    private static String escapeJson(String s) {
        return s.replace("\\", "\\\\").replace("\"", "\\\"");
    }

    private static String escapeXml(String s) {
        return s.replace("&","&amp;").replace("<","&lt;").replace(">","&gt;")
                .replace("\"","&quot;").replace("'","&apos;");
    }
}
