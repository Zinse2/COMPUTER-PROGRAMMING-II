import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonTest {

    @Test
    void constructorAndSetters_work() {
        Person sally = new Person("0000001", "Sally", "Williams", "Mrs.", 2006);

        // Constructor values
        assertEquals("0000001", sally.getIDNum());
        assertEquals("Sally", sally.getFirstName());
        assertEquals("Williams", sally.getLastName());
        assertEquals("Mrs.", sally.getTitle());
        assertEquals(2006, sally.getYOB());

        // Setter check
        sally.setLastName("Smith");
        assertEquals("Smith", sally.getLastName());
    }

    @Test
    void names_and_ageSpecific_work() {
        Person sally = new Person("0000001", "Sally", "Smith", "Mrs.", 2006);

        assertEquals("Sally Smith", sally.fullName());
        assertEquals("Mrs. Sally Smith", sally.formalName());

        // Use fixed year to avoid depending on today's date
        assertEquals(6, sally.getAgeSpecific(2012));
    }

    @Test
    void formats_are_reasonable() {
        Person sally = new Person("0000001", "Sally", "Smith", "Mrs.", 2006);

        // CSV should match exact order without spaces around commas
        assertEquals("0000001,Sally,Smith,Mrs.,2006", sally.toCSV());

        // Don't over-assert JSON/XML formatting; just check key pieces exist
        String json = sally.toJSON();
        assertTrue(json.contains("\"ID\":\"0000001\""));
        assertTrue(json.contains("\"firstName\":\"Sally\""));
        assertTrue(json.contains("\"lastName\":\"Smith\""));
        assertTrue(json.contains("\"title\":\"Mrs.\""));
        assertTrue(json.contains("\"YOB\":2006"));

        String xml = sally.toXML();
        assertTrue(xml.contains("<Person>"));
        assertTrue(xml.contains("<ID>0000001</ID>"));
        assertTrue(xml.contains("<FirstName>Sally</FirstName>"));
        assertTrue(xml.contains("<LastName>Smith</LastName>"));
        assertTrue(xml.contains("<Title>Mrs.</Title>"));
        assertTrue(xml.contains("<YOB>2006</YOB>"));
    }
}
