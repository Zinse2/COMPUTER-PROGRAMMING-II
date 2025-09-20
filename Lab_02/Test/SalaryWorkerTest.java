import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SalaryWorkerTest {

    @Test
    void weeklyPay_isAnnualDiv52_ignoresHours() {
        SalaryWorker s = new SalaryWorker("Sal","Emp","S1","Ms.",1990, 0.0, 52_000.0);
        assertEquals(1000.0, s.calculateWeeklyPay(40), 1e-6);
        assertEquals(1000.0, s.calculateWeeklyPay(50), 1e-6);
    }

    @Test
    void formatsContainAnnual() {
        SalaryWorker s = new SalaryWorker("Sal","Emp","S1","Ms.",1990, 0.0, 156_000.0);
        assertTrue(s.toCSV().contains("156000"));
        assertTrue(s.toJSON().contains("\"annualSalary\""));
        assertTrue(s.toXML().contains("<annualSalary>"));
    }
}
