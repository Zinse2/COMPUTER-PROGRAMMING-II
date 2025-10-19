package invoice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Invoice {
    private final List<LineItem> items = new ArrayList<>();
    public void add(LineItem li) { items.add(li); }
    public void clear() { items.clear(); }
    public List<LineItem> getItems() { return Collections.unmodifiableList(items); }
    public Money getTotal() {
        Money sum = Money.ofDollars(0);
        for (LineItem li : items) sum = sum.plus(li.getLineTotal());
        return sum;
    }
}
