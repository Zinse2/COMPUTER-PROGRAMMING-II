public class Worker extends Person {
    private double hourlyPayRate;

    public Worker(String firstName, String lastName, String id, String title, int yob, double hourlyPayRate) {
        super(firstName, lastName, id, title, yob);
        this.hourlyPayRate = hourlyPayRate;
    }

    public double getHourlyPayRate() {
        return hourlyPayRate;
    }

    public void setHourlyPayRate(double hourlyPayRate) {
        this.hourlyPayRate = hourlyPayRate;
    }

    /** Regular up to 40 hrs at 1.0x, OT above 40 at 1.5x */
    public double calculateWeeklyPay(double hoursWorked) {
        double reg = Math.min(40.0, hoursWorked);
        double ot  = Math.max(0.0, hoursWorked - 40.0);
        return reg * hourlyPayRate + ot * hourlyPayRate * 1.5;
    }

    /** Prints a simple breakdown: regular vs OT and totals */
    public void displayWeeklyPay(double hoursWorked) {
        double reg = Math.min(40.0, hoursWorked);
        double ot  = Math.max(0.0, hoursWorked - 40.0);
        double regPay = reg * hourlyPayRate;
        double otPay  = ot * hourlyPayRate * 1.5;
        double total  = regPay + otPay;

        System.out.printf(
                "%-18s | Hours: %5.1f | Reg: %4.1f @ $%.2f = $%7.2f | OT: %4.1f @ $%.2f = $%7.2f | TOTAL: $%8.2f%n",
                getFirstName() + " " + getLastName(),
                hoursWorked, reg, hourlyPayRate, regPay, ot, hourlyPayRate * 1.5, otPay, total
        );
    }

    // ----- data format helpers -----
    @Override
    public String toCSV() {
        // Appends Worker field after your Person CSV
        return super.toCSV() + "," + hourlyPayRate;
    }

    @Override
    public String toJSON() {
        return String.format(
                "{\"firstName\":\"%s\",\"lastName\":\"%s\",\"id\":\"%s\",\"title\":\"%s\",\"yob\":%d,\"hourlyPayRate\":%.2f}",
                getFirstName(), getLastName(), getIDNum(), getTitle(), getYOB(), hourlyPayRate
        );
    }

    @Override
    public String toXML() {
        return String.format(
                "<worker><firstName>%s</firstName><lastName>%s</lastName><id>%s</id><title>%s</title><yob>%d</yob><hourlyPayRate>%.2f</hourlyPayRate></worker>",
                getFirstName(), getLastName(), getIDNum(), getTitle(), getYOB(), hourlyPayRate
        );
    }

    @Override
    public String toString() {
        return String.format("Worker{%s %s, id=%s, title=%s, yob=%d, rate=%.2f}",
                getFirstName(), getLastName(), getIDNum(), getTitle(), getYOB(), hourlyPayRate);
    }
}
