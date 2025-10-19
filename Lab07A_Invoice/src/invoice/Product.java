package invoice;

public record Product(String name, Money unitPrice) {
    public Product {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("name");
        if (unitPrice == null) throw new IllegalArgumentException("unitPrice");
    }
}
