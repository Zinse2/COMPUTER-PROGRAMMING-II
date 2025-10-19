package invoice;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class LineItemTableModel extends AbstractTableModel {
    private final List<LineItem> data;
    private static final String[] COLS = {"Product", "Unit Price", "Qty", "Line Total"};

    public LineItemTableModel(List<LineItem> data) { this.data = data; }

    @Override public int getRowCount() { return data.size(); }
    @Override public int getColumnCount() { return COLS.length; }
    @Override public String getColumnName(int column) { return COLS[column]; }

    @Override public Object getValueAt(int row, int col) {
        LineItem li = data.get(row);
        return switch (col) {
            case 0 -> li.product().name();
            case 1 -> li.product().unitPrice().toString();
            case 2 -> li.quantity();
            case 3 -> li.getLineTotal().toString();
            default -> "";
        };
    }
}
