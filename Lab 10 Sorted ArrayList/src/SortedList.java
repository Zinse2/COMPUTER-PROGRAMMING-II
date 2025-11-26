import java.util.ArrayList;
import java.util.List;

/**
 * A simple sorted list of Strings that always keeps elements
 * in ascending lexicographic order. Uses manual binary search
 * for insert and search.
 */
public class SortedList {

    private final List<String> data = new ArrayList<>();

    public int size() {
        return data.size();
    }

    public String get(int index) {
        return data.get(index);
    }

    /**
     * Adds a value into the list in sorted order.
     * Uses binary search to find the correct insertion point.
     */
    public void add(String value) {
        if (data.isEmpty()) {
            data.add(value);
            return;
        }

        int index = findInsertionPoint(value);
        data.add(index, value);
    }

    /**
     * Binary search:
     * If found, returns the index where the value is.
     * If NOT found, returns -(insertionPoint + 1),
     * where insertionPoint is where it would go.
     */
    public int search(String target) {
        int low = 0;
        int high = data.size() - 1;

        while (low <= high) {
            int mid = (low + high) / 2;
            String midVal = data.get(mid);
            int cmp = midVal.compareTo(target);

            if (cmp < 0) {
                low = mid + 1;      // target is after mid
            } else if (cmp > 0) {
                high = mid - 1;     // target is before mid
            } else {
                return mid;         // found exact match
            }
        }

        // not found: low is the insertion point
        return -(low + 1);
    }

    /**
     * Helper for insertion.
     * Always returns the index where value should be inserted.
     */
    private int findInsertionPoint(String value) {
        int low = 0;
        int high = data.size() - 1;

        while (low <= high) {
            int mid = (low + high) / 2;
            String midVal = data.get(mid);
            int cmp = midVal.compareTo(value);

            if (cmp < 0) {
                low = mid + 1;
            } else if (cmp > 0) {
                high = mid - 1;
            } else {
                // equal: insert here (could also choose mid + 1)
                return mid;
            }
        }

        return low; // insertion point
    }

    /**
     * Returns all elements, one per line, for display.
     */
    public String toMultilineString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < data.size(); i++) {
            sb.append(i)
                    .append(": ")
                    .append(data.get(i))
                    .append("\n");
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return data.toString();
    }
}
