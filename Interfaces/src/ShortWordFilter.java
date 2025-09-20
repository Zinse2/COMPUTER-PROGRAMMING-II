public class ShortWordFilter implements Filter {

    private final int maxLen;

    public ShortWordFilter() {
        this(4); // “short” = length < 5  →  maxLen = 4
    }

    public ShortWordFilter(int maxLen) {
        this.maxLen = maxLen;
    }

    @Override
    public boolean accept(Object x) {
        if (!(x instanceof String s)) return false;
        // normalize to letters only for length check (strip punctuation)
        String clean = s.replaceAll("[^A-Za-z]", "");
        return !clean.isEmpty() && clean.length() <= maxLen;
    }
}
