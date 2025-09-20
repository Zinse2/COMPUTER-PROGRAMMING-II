import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

public class BigRectLister {
    public static void main(String[] args) {
        // 1) Build a list of 10 rectangles (some <=10, some >10 perimeter)
        List<Rectangle> rects = new ArrayList<>();
        rects.add(new Rectangle(1, 1));   // P=4
        rects.add(new Rectangle(2, 1));   // P=6
        rects.add(new Rectangle(2, 2));   // P=8
        rects.add(new Rectangle(3, 1));   // P=8
        rects.add(new Rectangle(3, 2));   // P=10
        rects.add(new Rectangle(3, 3));   // P=12  (big)
        rects.add(new Rectangle(4, 2));   // P=12  (big)
        rects.add(new Rectangle(5, 1));   // P=12  (big)
        rects.add(new Rectangle(5, 3));   // P=16  (big)
        rects.add(new Rectangle(7, 2));   // P=18  (big)

        // 2) Filter for big perimeters (>10)
        Filter f = new BigRectangleFilter(); // default threshold 10
        List<Rectangle> biggies = new ArrayList<>();
        for (Rectangle r : rects) {
            if (f.accept(r)) biggies.add(r);
        }

        // 3) Show results
        System.out.println("All Rectangles (w x h) and perimeters:");
        for (Rectangle r : rects) {
            int p = 2 * (r.width + r.height);
            System.out.printf(" (%d x %d)  P=%d%s%n", r.width, r.height, p,
                    p > 10 ? "  <- BIG" : "");
        }

        System.out.println("\nFiltered (perimeter > 10): " + biggies.size());
        System.out.println("-".repeat(36));
        for (Rectangle r : biggies) {
            int p = 2 * (r.width + r.height);
            System.out.printf(" (%d x %d)  P=%d%n", r.width, r.height, p);
        }
    }
}
