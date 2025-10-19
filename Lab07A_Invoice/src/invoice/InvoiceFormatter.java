package invoice;

public class InvoiceFormatter {
    public String format(Invoice invoice) {
        StringBuilder sb = new StringBuilder();
        sb.append("=========================================\n");
        sb.append(String.format("%-20s %8s %6s %10s%n", "Product", "Price", "Qty", "Total"));
        sb.append("-----------------------------------------\n");
        for (LineItem li : invoice.getItems()) {
            String name = li.product().name();
            String price = li.product().unitPrice().toString();
            int qty = li.quantity();
            String total = li.getLineTotal().toString();
            sb.append(String.format("%-20s %8s %6d %10s%n", name, price, qty, total));
        }
        sb.append("-----------------------------------------\n");
        sb.append(String.format("%-36s %10s%n", "Total:", invoice.getTotal()));
        sb.append("=========================================\n");
        return sb.toString();
    }
}
