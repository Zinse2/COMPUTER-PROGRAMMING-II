import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class WorkerTest {

    @Test
    void weeklyPay_noOvertime() {
        Worker w = new Worker("Test","Worker","X1","Mr.",1990, 20.00);
        assertEquals(800.0, w.calculateWeeklyPay(40), 1e-6);
    }

    @Test
    void weeklyPay_withOvertime() {
        Worker w = new Worker("Test","Worker","X1","Mr.",1990, 20.00);
        // 40*20 + 10*30 = 800 + 300 = 1100
        assertEquals(1100.0, w.calculateWeeklyPay(50), 1e-6);
    }

    @Test
    void formatsContainRate() {
        Worker w = new Worker("A","B","ID1","Ms.",2000, 33.25);
        assertTrue(w.toCSV().contains("33.25"));
        assertTrue(w.toJSON().contains("\"hourlyPayRate\""));
        assertTrue(w.toXML().contains("<hourlyPayRate>"));
    }
}
