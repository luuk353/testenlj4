import org.example.Calculations;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CalculationsTest {

    @Test
    void testGetRentePercentage() {
        Assertions.assertEquals(0.02, Calculations.getRentePercentage(1), 0.001);
        assertEquals(0.03, Calculations.getRentePercentage(5), 0.001);
        assertEquals(0.035, Calculations.getRentePercentage(10), 0.001);
        assertEquals(0.045, Calculations.getRentePercentage(20), 0.001);
        assertEquals(0.05, Calculations.getRentePercentage(30), 0.001);

        assertEquals(-1, Calculations.getRentePercentage(50), 0.001);
        assertEquals(-1, Calculations.getRentePercentage(-5), 0.001);
    }

    @Test
    void testBerekenMaximaleLeningZonderStudieschuld() {
        double maandInkomen = 4000;
        double verwachtMaxLening = 204000;
        assertEquals(verwachtMaxLening, Calculations.berekenMaximaleLening(maandInkomen, false), 0.001);
    }

    @Test
    void testBerekenMaximaleLeningMetStudieschuld() {
        double maandInkomen = 4000;
        double verwachtMaxLeningMetSchuld = 153000;
        assertEquals(verwachtMaxLeningMetSchuld, Calculations.berekenMaximaleLening(maandInkomen, true), 0.001);
    }

    @Test
    void testBerekenMaandlasten() {
        double lening = 200000;
        double rente = 0.05;
        int looptijd = 30;

        double maandlastenVerwacht = (lening * rente / 12) + (lening / (looptijd * 12));
        assertEquals(maandlastenVerwacht, Calculations.berekenMaandlasten(lening, rente, looptijd), 0.001);
    }

    @Test
    void testIsVerbodenPostcode() {
        assertTrue(Calculations.isVerbodenPostcode("9679"));
        assertTrue(Calculations.isVerbodenPostcode("9681"));
        assertTrue(Calculations.isVerbodenPostcode("9682"));

        assertFalse(Calculations.isVerbodenPostcode("1234"));
        assertFalse(Calculations.isVerbodenPostcode("5678"));
    }
}
