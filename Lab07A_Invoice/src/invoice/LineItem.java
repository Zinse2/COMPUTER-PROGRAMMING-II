package invoice;

public record LineItem(Product product, int quantity) {
    public LineItem {
        if (product == null) throw new IllegalArgumentException("product");
        if (quantity <= 0) throw new IllegalArgumentException("quantity must be > 0");
    }
    public Money getLineTotal() { return product.unitPrice().times(quantity); }
}
