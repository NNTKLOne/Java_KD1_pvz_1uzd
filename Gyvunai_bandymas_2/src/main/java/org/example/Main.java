package org.example;

import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        List<Animal> animals = new ArrayList<>();

        // Failo nuskaitymas
        try {
            InputStream inputStream = Main.class.getClassLoader().getResourceAsStream("duomenys.txt");
            if (inputStream == null) {
                System.out.println("Nepavyko rasti failo: duomenys.txt");
                return;
            }
            Scanner scanner = new Scanner(inputStream);

            // Pirmoje eilutėje gyvūnų vardai
            String[] animalNames = scanner.nextLine().replace("Gyvūnas: ", "").split(";");

            // Sekančiose eilutėse kita informacija apie gyvūnus
            int index = 0;
            while (scanner.hasNextLine()) {
                String[] data = scanner.nextLine().split("\\+");
                String continent = data[0];
                Animal.MammalStatus mammalStatus = Animal.MammalStatus.valueOf(data[1]);
                double weight = Double.parseDouble(data[2]);
                double lifespan = Double.parseDouble(data[3]);

                // Sukuriame ir pridedame gyvūnus į sąrašą
                animals.add(new Animal(animalNames[index], mammalStatus, continent, weight, lifespan));
                index++;
            }

            // Filtruojame gyvūnus, kurie sveria daugiau nei 100kg
            List<Animal> filteredAnimals = new ArrayList<>();
            for (Animal animal : animals) {
                if (animal.getWeight() > 100) {
                    filteredAnimals.add(animal);
                }
            }

            // Surikiuojame gyvūnus pagal pavadinimo ilgį, svorį ir žemyną
            filteredAnimals.sort((a1, a2) -> {
                int nameComparison = Integer.compare(a2.getName().length(), a1.getName().length()); // pagal varda nuo ilgiausio iki trumpiausio (maz tvarka)
                if (nameComparison != 0) return nameComparison;

                int weightComparison = Double.compare(a2.getWeight(), a1.getWeight()); // pagal svori nuo sunkiausio iki lengviausio (maz tvarka)
                if (weightComparison != 0) return weightComparison;

                return a2.getContinent().compareTo(a1.getContinent()); // pagal varda nuo Z iki A (did tvarka)
            });

            // Spausdiname į ekraną
            for (Animal animal : filteredAnimals) {
                System.out.println(animal);
            }

            // Įrašymas į failą pasirinktų gyvūnų
            Scanner inputScanner = new Scanner(System.in);
            System.out.print("Įveskite gyvūno pavadinimą: ");
            String chosenName = inputScanner.nextLine();

            double chosenWeight = -1;
            while (chosenWeight < 0) {
                System.out.print("Įveskite minimalų svorį: ");
                if (inputScanner.hasNextDouble()) {
                    chosenWeight = inputScanner.nextDouble();
                } else {
                    System.out.println("Neteisingas svorio įvestis. Bandykite dar kartą.");
                    inputScanner.next(); // Pašalina blogą įvestį
                }
            }

            boolean foundAnimal = false;
            try (PrintWriter writer = new PrintWriter("rezultatai.txt")) {
                for (Animal animal : filteredAnimals) {
                    if (animal.getName().equalsIgnoreCase(chosenName) && animal.getWeight() > chosenWeight) {
                        writer.println(animal);
                        foundAnimal = true;
                    }
                }
                if (!foundAnimal) {
                    System.out.println("Nėra gyvūno, atitinkančio nurodytą pavadinimą ir svorį.");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}