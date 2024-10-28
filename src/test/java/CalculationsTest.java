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

    // Integratietest 1: Volledige berekening zonder studieschuld
    @Test
    void integrationTestFullLoanZonderSchulden() {
        double maandInkomen = 5000;
        double partnerMaandInkomen = 2000;
        int rentevastePeriode = 20;
        boolean heeftStudieschuld = false;
        String postcode = "1234";

        // Stap 1: Controleer dat de postcode is toegestaan
        assertFalse(Calculations.isVerbodenPostcode(postcode));

        // Stap 2: Bereken het totale maandinkomen
        double totaalMaandInkomen = maandInkomen + partnerMaandInkomen;

        // Stap 3: Haal het rentepercentage op voor de gekozen periode
        double rentePercentage = Calculations.getRentePercentage(rentevastePeriode);
        assertEquals(0.045, rentePercentage, 0.001);

        // Stap 4: Bereken het maximaal te lenen bedrag
        double maximaalTeLenenBedrag = Calculations.berekenMaximaleLening(totaalMaandInkomen, heeftStudieschuld);
        assertEquals(297500, maximaalTeLenenBedrag, 0.001);

        // Stap 5: Bereken de maandelijkse lasten
        double maandlasten = Calculations.berekenMaandlasten(maximaalTeLenenBedrag, rentePercentage, rentevastePeriode);
        assertEquals(1661.46, maandlasten, 0.01); // Verwachte maandlasten afronden tot 2 decimalen
    }

    // Integratietest 2: Volledige berekening met een verboden postcode
    @Test
    void integrationTestMetVerbodePostcode() {
        double maandInkomen = 4000;
        double partnerMaandInkomen = 1500;
        String verbodenPostcode = "9679";

        // Stap 1: Controleer dat de postcode verboden is
        assertTrue(Calculations.isVerbodenPostcode(verbodenPostcode));

        // Stap 2: Als de postcode verboden is, zou verdere berekening niet uitgevoerd moeten worden.
        // We simuleren hier dat de berekening stopt als de postcode verboden is.
        double totaalMaandInkomen = maandInkomen + partnerMaandInkomen;
        double maximaalTeLenenBedrag = Calculations.berekenMaximaleLening(totaalMaandInkomen, false);

        // Controleer of de maximaal te lenen bedrag geen waarde heeft toegewezen, wat aangeeft dat de berekening is gestopt
        assertEquals(0, maximaalTeLenenBedrag, "De berekening zou niet uitgevoerd moeten worden voor een verboden postcode");
    }
}
