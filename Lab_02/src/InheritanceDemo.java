import java.util.ArrayList;
import java.util.List;

public class InheritanceDemo {
    public static void main(String[] args) {
        List<Worker> staff = new ArrayList<>();

        // 3 hourly workers
        staff.add(new Worker("Ada",   "Lovelace", "P001", "Ms.", 1998, 28.50));
        staff.add(new Worker("Alan",  "Turing",   "P002", "Mr.", 1996, 31.25));
        staff.add(new Worker("Katherine","Johnson","P003","Ms.", 1995, 35.00));

        // 3 salary workers (hourly rate arg is ignored by SalaryWorker)
        staff.add(new SalaryWorker("Grace","Hopper", "S101","Dr.", 1988, 0.0, 120_000));
        staff.add(new SalaryWorker("Linus","Torvalds","S102","Mr.", 1970, 0.0, 156_000));
        staff.add(new SalaryWorker("Margaret","Hamilton","S103","Ms.", 1936, 0.0, 200_000));

        int[] weekHours = {40, 50, 40}; // Week 1, Week 2 crunch, Week 3 normal

        for (int w = 0; w < weekHours.length; w++) {
            int hrs = weekHours[w];
            System.out.println("\n=== Week " + (w + 1) + " (" + hrs + " hours) ===");
            System.out.printf("%-18s | %-70s%n", "Employee", "Pay Breakdown");
            System.out.println("-".repeat(96));
            for (Worker wk : staff) {
                wk.displayWeeklyPay(hrs); // polymorphic: Worker vs SalaryWorker
            }
        }
    }
}
