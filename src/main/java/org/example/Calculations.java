package org.example;

public class Calculations {

    public static double getRentePercentage(int jaren) {
        return switch (jaren) {
            case 1 -> 0.02;
            case 5 -> 0.03;
            case 10 -> 0.035;
            case 20 -> 0.045;
            case 30 -> 0.05;
            default -> -1;
        };
    }

    public static double berekenMaximaleLening(double totaalMaandInkomen, boolean studieschuld) {
        double totaalJaarInkomen = totaalMaandInkomen * 12;
        double maximaalTeLenen = totaalJaarInkomen * 4.25;

        if (studieschuld) {
            maximaalTeLenen *= 0.75;
        }

        return maximaalTeLenen;
    }

    public static double berekenMaandlasten(double lening, double rente, int looptijdInJaren) {
        double maandRente = lening * rente / 12;
        double maandAflossing = lening / (looptijdInJaren * 12);
        return maandRente + maandAflossing;
    }

    public static boolean isVerbodenPostcode(String postcode) {
        return postcode.equals("9679") || postcode.equals("9681") || postcode.equals("9682");
    }
}
