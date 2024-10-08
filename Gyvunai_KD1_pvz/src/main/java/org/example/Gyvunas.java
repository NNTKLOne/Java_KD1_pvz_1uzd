package org.example;

public class Gyvunas {
    String vardas;
    String veisle;
    double svoris; // kilogramais
    int gimimoMetai;

    public Gyvunas(String vardas, String veisle, double svoris, int gimimoMetai) {
        this.vardas = vardas;
        this.veisle = veisle;
        this.svoris = svoris;
        this.gimimoMetai = gimimoMetai;
    }

    public int amzius(int metai) {
        return metai - this.gimimoMetai;
    }

    public String formatuotiVeisle() {
        StringBuilder formatuotaVeisle = new StringBuilder();
        for (int i = 0; i < veisle.length(); i++) {
            if (i % 2 == 0) {
                formatuotaVeisle.append(Character.toLowerCase(veisle.charAt(i)));
            } else {
                formatuotaVeisle.append(Character.toUpperCase(veisle.charAt(i)));
            }
        }
        return formatuotaVeisle.toString();
    }

    @Override
    public String toString() {
        return vardas.toUpperCase() + " " + formatuotiVeisle() + " " + amzius(2019) + " " + String.format("%.2f", svoris);
    }
}
