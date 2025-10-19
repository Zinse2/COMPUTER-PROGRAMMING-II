package invoice;

import java.text.NumberFormat;

public final class Money implements Comparable<Money> {
    private final long cents;

    public Money(long cents) { this.cents = cents; }
    public Money(double dollars) { this.cents = Math.round(dollars * 100.0); }

    public static Money ofDollars(double d) { return new Money(d); }

    public Money plus(Money other) { return new Money(this.cents + other.cents); }
    public Money times(int n) { return new Money(this.cents * n); }
    public double toDouble() { return cents / 100.0; }
    public long toCents() { return cents; }

    @Override public String toString() {
        return NumberFormat.getCurrencyInstance().format(toDouble());
    }
    @Override public int compareTo(Money o) { return Long.compare(this.cents, o.cents); }
}
