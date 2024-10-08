package org.example;

import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        List<Gyvunas> gyvunai = new ArrayList<>();

        // Failo nuskaitymas iš resursų
        try {
            InputStream inputStream = Main.class.getClassLoader().getResourceAsStream("duomenys.txt");
            if (inputStream == null) {
                System.out.println("Nepavyko rasti failo: duomenys.txt");
                return;
            }
            Scanner scanner = new Scanner(inputStream);
            String veisle = "";
            while (scanner.hasNextLine()) {
                String eilute = scanner.nextLine().trim();

                if (eilute.isEmpty()) {
                    continue; // Jei eilutė tuščia, praleiskime ją
                }

                if (eilute.endsWith(":")) {
                    // Nauja veislė
                    veisle = eilute.replace(":", "").trim();
                    continue;
                }

                // Gyvūno duomenys
                String[] dalys = eilute.split(", ");
                if (dalys.length < 3) {
                    System.out.println("Blogas įrašas: " + eilute);
                    continue; // Jei nėra pakankamai duomenų, praleiskime įrašą
                }

                String vardas = dalys[0];
                int gimimoMetai;
                double svoris;
                try {
                    gimimoMetai = Integer.parseInt(dalys[1]);
                    svoris = Double.parseDouble(dalys[2]);
                } catch (NumberFormatException e) {
                    System.out.println("Klaidingi duomenys: " + eilute);
                    continue; // Jei įvesti duomenys yra neteisingi, praleiskime
                }

                gyvunai.add(new Gyvunas(vardas, veisle, svoris, gimimoMetai));
            }
            scanner.close();

            // Surikiavimas pagal nurodytus kriterijus
            gyvunai.sort((g1, g2) -> {
                if (g1.svoris != g2.svoris) {
                    return Double.compare(g2.svoris, g1.svoris); // nuo sunkiausiu iki lengviausiu (maz tvarka pagal svori)
                } else if (g1.gimimoMetai != g2.gimimoMetai) {
                    return Integer.compare(g1.gimimoMetai, g2.gimimoMetai); // nuo jaunausiu iki vyriausiu (did tvarka pagal metus)
                } else {
                    return g1.vardas.compareTo(g2.vardas); // pagal varda nuo A iki Z (did tvarka)
                }
            });

            // Spausdinimas į ekraną
            for (Gyvunas gyvunas : gyvunai) {
                System.out.println(gyvunas);
            }

            // Įrašymas į failą rezultatai.txt
            PrintWriter writer = new PrintWriter(new File("rezultatai.txt"));
            for (Gyvunas gyvunas : gyvunai) {
                if (gyvunas.amzius(2019) < gyvunas.svoris) {
                    writer.println(gyvunas);
                }
            }
            writer.close();

        } catch (Exception e) {
            System.out.println("Nepavyko rasti failo: " + e.getMessage());
        }
    }
}
