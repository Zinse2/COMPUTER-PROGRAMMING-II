import java.awt.Rectangle;

public class BigRectangleFilter implements Filter {

    private final int minPerimeter;

    public BigRectangleFilter() {
        this(10); // “big” if perimeter > 10
    }

    public BigRectangleFilter(int minPerimeter) {
        this.minPerimeter = minPerimeter;
    }

    @Override
    public boolean accept(Object x) {
        if (!(x instanceof Rectangle r)) return false;
        int perim = 2 * (r.width + r.height);
        return perim > minPerimeter;
    }
}
