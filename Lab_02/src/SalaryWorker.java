public class SalaryWorker extends Worker {
    private double annualSalary;

    public SalaryWorker(String firstName, String lastName, String id, String title, int yob,
                        double hourlyPayRateIgnored, double annualSalary) {
        super(firstName, lastName, id, title, yob, hourlyPayRateIgnored); // keep for polymorphism
        this.annualSalary = annualSalary;
    }

    public double getAnnualSalary() {
        return annualSalary;
    }

    public void setAnnualSalary(double annualSalary) {
        this.annualSalary = annualSalary;
    }

    /** Weekly pay is a fixed portion of annual salary; hoursWorked is ignored (for polymorphism). */
    @Override
    public double calculateWeeklyPay(double hoursWorked) {
        return annualSalary / 52.0;
    }

    @Override
    public void displayWeeklyPay(double hoursWorked) {
        System.out.printf("%-18s | Salary weekly pay (annual $%.2f / 52) = $%8.2f%n",
                getFirstName() + " " + getLastName(), annualSalary, calculateWeeklyPay(hoursWorked));
    }

    // ----- data format helpers -----
    @Override
    public String toCSV() {
        return super.toCSV() + "," + annualSalary;
    }

    @Override
    public String toJSON() {
        return String.format(
                "{\"firstName\":\"%s\",\"lastName\":\"%s\",\"id\":\"%s\",\"title\":\"%s\",\"yob\":%d,\"annualSalary\":%.2f}",
                getFirstName(), getLastName(), getIDNum(), getTitle(), getYOB(), annualSalary
        );
    }

    @Override
    public String toXML() {
        return String.format(
                "<salaryWorker><firstName>%s</firstName><lastName>%s</lastName><id>%s</id><title>%s</title><yob>%d</yob><annualSalary>%.2f</annualSalary></salaryWorker>",
                getFirstName(), getLastName(), getIDNum(), getTitle(), getYOB(), annualSalary
        );
    }

    @Override
    public String toString() {
        return String.format("SalaryWorker{%s %s, id=%s, title=%s, yob=%d, annual=%.2f}",
                getFirstName(), getLastName(), getIDNum(), getTitle(), getYOB(), annualSalary);
    }
}
