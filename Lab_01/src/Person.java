import java.util.Calendar;

public class Person {
    private String ID;
    private String firstName;
    private String lastName;
    private String title;
    private int YOB;

    // Constructor with all fields
    public Person(String IDNum, String firstName, String lastName, String title, int YOB) {
        this.ID = IDNum;
        this.firstName = firstName;
        this.lastName = lastName;
        this.title = title;
        this.YOB = YOB;
    }

    // Getters
    public String getIDNum() { return ID; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getTitle() { return title; }
    public int getYOB() { return YOB; }

    // Setters
    public void setIDNum(String IDNum) { this.ID = IDNum; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setTitle(String title) { this.title = title; }
    public void setYOB(int YOB) { this.YOB = YOB; }

    // Readable string version of the object
    @Override
    public String toString() {
        return "Person{" +
                "IDNum='" + ID + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", title='" + title + '\'' +
                ", YOB=" + YOB +
                '}';
    }

    // Combine name fields
    public String fullName() {
        return firstName + " " + lastName;
    }

    // Formal title + full name
    public String formalName() {
        return title + " " + fullName();
    }

    // Age based on current year
    public int getAge() {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        return currentYear - YOB;
    }

    // Age based on a specific year
    public int getAgeSpecific(int year) {
        return year - YOB;
    }

    // CSV output
    public String toCSV() {
        return ID + "," + firstName + "," + lastName + "," + title + "," + YOB;
    }

    // JSON output
    public String toJSON() {
        return "{"
                + "\"ID\":\"" + escapeJson(ID) + "\","
                + "\"firstName\":\"" + escapeJson(firstName) + "\","
                + "\"lastName\":\"" + escapeJson(lastName) + "\","
                + "\"title\":\"" + escapeJson(title) + "\","
                + "\"YOB\":" + YOB
                + "}";
    }

    // XML output
    public String toXML() {
        return "<Person>"
                + "<ID>" + escapeXml(ID) + "</ID>"
                + "<FirstName>" + escapeXml(firstName) + "</FirstName>"
                + "<LastName>" + escapeXml(lastName) + "</LastName>"
                + "<Title>" + escapeXml(title) + "</Title>"
                + "<YOB>" + YOB + "</YOB>"
                + "</Person>";
    }

    // Escape helpers for JSON and XML
    private static String escapeJson(String s) {
        return s.replace("\\", "\\\\").replace("\"", "\\\"");
    }

    private static String escapeXml(String s) {
        return s.replace("&","&amp;").replace("<","&lt;").replace(">","&gt;")
                .replace("\"","&quot;").replace("'","&apos;");
    }
}
