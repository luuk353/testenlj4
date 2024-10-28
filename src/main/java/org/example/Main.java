package org.example;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Voer je maandinkomen in (in EUR): ");
        double maandInkomen = scanner.nextDouble();

        System.out.print("Heb je een partner? (ja/nee): ");
        String heeftPartner = scanner.next();

        double partnerMaandInkomen = 0.0;
        if (heeftPartner.equalsIgnoreCase("ja")) {
            System.out.print("Voer het maandinkomen van je partner in (in EUR): ");
            partnerMaandInkomen = scanner.nextDouble();
        }

        System.out.print("Kies een rentevaste periode (1, 5, 10, 20, 30 jaar): ");
        int rentevastePeriode = scanner.nextInt();

        double rentePercentage = Calculations.getRentePercentage(rentevastePeriode);
        if (rentePercentage == -1) {
            System.out.println("Ongeldige rentevaste periode. Kies 1, 5, 10, 20 of 30 jaar.");
            return;
        }

        System.out.print("Heb je een studieschuld? (ja/nee): ");
        String heeftStudieschuld = scanner.next();

        boolean studieschuld = heeftStudieschuld.equalsIgnoreCase("ja");

        System.out.print("Voer je postcode in: ");
        String postcode = scanner.next();

        if (Calculations.isVerbodenPostcode(postcode)) {
            System.out.println("Hypotheekaanvragen in dit postcodegebied worden niet geaccepteerd.");
            return;
        }

        double totaalMaandInkomen = maandInkomen + partnerMaandInkomen;
        double maximaalTeLenenBedrag = Calculations.berekenMaximaleLening(totaalMaandInkomen, studieschuld);

        double maandlasten = Calculations.berekenMaandlasten(maximaalTeLenenBedrag, rentePercentage, rentevastePeriode);

        System.out.printf("Je kunt maximaal EUR %.2f lenen.%n", maximaalTeLenenBedrag);
        System.out.printf("Je maandelijkse hypotheeklasten zijn EUR %.2f.%n", maandlasten);

        double totaleKosten = maandlasten * rentevastePeriode * 12;
        System.out.printf("Als je de hypotheek volledig aflost over %d jaar, betaal je in totaal EUR %.2f.%n", rentevastePeriode, totaleKosten);
    }
}
