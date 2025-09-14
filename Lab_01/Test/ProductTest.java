import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    @Test
    void constructorAndSetters_work() {
        Product cheese = new Product("cheddar", "creamy orange", "000001", 5.99);

        // Constructor values
        assertEquals("cheddar", cheese.getName());
        assertEquals("creamy orange", cheese.getDescription());
        assertEquals("000001", cheese.getID());
        assertEquals(5.99, cheese.getCost(), 1e-9);

        // Setter checks
        cheese.setCost(4.99);
        assertEquals(4.99, cheese.getCost(), 1e-9);

        cheese.setName("sharp cheddar");
        assertEquals("sharp cheddar", cheese.getName());
    }

    @Test
    void formats_are_reasonable() {
        Product cheese = new Product("cheddar", "creamy orange", "000001", 5.99);

        // CSV: name,description,ID,cost with no spaces around commas
        assertEquals("cheddar,creamy orange,000001,5.99", cheese.toCSV());

        String json = cheese.toJSON();
        assertTrue(json.contains("\"ID\":\"000001\""));
        assertTrue(json.contains("\"name\":\"cheddar\""));
        assertTrue(json.contains("\"description\":\"creamy orange\""));
        assertTrue(json.contains("\"cost\":5.99"));

        String xml = cheese.toXML();
        assertTrue(xml.contains("<Product>"));
        assertTrue(xml.contains("<ID>000001</ID>"));
        assertTrue(xml.contains("<Name>cheddar</Name>"));
        assertTrue(xml.contains("<Description>creamy orange</Description>"));
        assertTrue(xml.contains("<Cost>5.99</Cost>"));
    }
}
