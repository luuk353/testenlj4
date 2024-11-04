import org.example.Main;
import org.junit.jupiter.api.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class MainIntegrationTest {

    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
        outputStream.reset();
    }

    private void setInput(String input) {
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
    }

    @Test
    void testValidSingleIncomeNoPartnerNoDebt() {
        String input = String.join(System.lineSeparator(),
                "3500",    // maandInkomen
                "nee",     // heeftPartner
                "10",      // rentevastePeriode
                "nee",     // heeftStudieschuld
                "1234AB"   // postcode
        );

        setInput(input);

        Main.main(new String[]{});

        String output = outputStream.toString();
        assertTrue(output.contains("Je kunt maximaal EUR"));
        assertTrue(output.contains("Je maandelijkse hypotheeklasten zijn EUR"));
        assertTrue(output.contains("Als je de hypotheek volledig aflost over 10 jaar, betaal je in totaal EUR"));
    }

    @Test
    void testWithPartnerIncomeAndStudyDebt() {
        String input = String.join(System.lineSeparator(),
                "4000",    // maandInkomen
                "ja",      // heeftPartner
                "3000",    // partnerMaandInkomen
                "20",      // rentevastePeriode
                "ja",      // heeftStudieschuld
                "1234AB"   // postcode
        );

        setInput(input);

        Main.main(new String[]{});

        String output = outputStream.toString();
        assertTrue(output.contains("Je kunt maximaal EUR"));
        assertTrue(output.contains("Je maandelijkse hypotheeklasten zijn EUR"));
        assertTrue(output.contains("Als je de hypotheek volledig aflost over 20 jaar, betaal je in totaal EUR"));
    }

    @Test
    void testInvalidInterestPeriod() {
        String input = String.join(System.lineSeparator(),
                "4000",    // maandInkomen
                "nee",     // heeftPartner
                "2",       // invalid rentevastePeriode
                "nee",     // heeftStudieschuld
                "1234AB"   // postcode
        );

        setInput(input);

        Main.main(new String[]{});

        String output = outputStream.toString();
        assertTrue(output.contains("Ongeldige rentevaste periode. Kies 1, 5, 10, 20 of 30 jaar."));
    }

    @Test
    void testForbiddenPostcode() {
        String input = String.join(System.lineSeparator(),
                "4000",    // maandInkomen
                "nee",     // heeftPartner
                "10",      // rentevastePeriode
                "nee",     // heeftStudieschuld
                "9999ZZ"   // forbidden postcode
        );

        setInput(input);

        Main.main(new String[]{});

        String output = outputStream.toString();
        assertTrue(output.contains("Hypotheekaanvragen in dit postcodegebied worden niet geaccepteerd."));
    }

    @Test
    void testMaximumLoanCalculation() {
        String input = String.join(System.lineSeparator(),
                "6000",    // maandInkomen
                "ja",      // heeftPartner
                "4000",    // partnerMaandInkomen
                "30",      // rentevastePeriode
                "nee",     // heeftStudieschuld
                "1234AB"   // valid postcode
        );

        setInput(input);

        Main.main(new String[]{});

        String output = outputStream.toString();
        assertTrue(output.contains("Je kunt maximaal EUR"));
        assertTrue(output.contains("Je maandelijkse hypotheeklasten zijn EUR"));
        assertTrue(output.contains("Als je de hypotheek volledig aflost over 30 jaar, betaal je in totaal EUR"));
    }
}
