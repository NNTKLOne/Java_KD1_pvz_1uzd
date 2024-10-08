package org.example;

public class Animal {
    public enum MammalStatus {
        M, // Žinduolis
        NM // Ne žinduolis
    }

    private String name;
    private MammalStatus mammalStatus;
    private String continent;
    private double weight;
    private double lifespan;

    public Animal(String name, MammalStatus mammalStatus, String continent, double weight, double lifespan) {
        this.name = name;
        this.mammalStatus = mammalStatus;
        this.continent = continent;
        this.weight = weight;
        this.lifespan = lifespan;
    }

    public boolean isMammal() {
        return this.mammalStatus == MammalStatus.M;
    }

    public String getName() {
        return name;
    }

    public double getWeight() {
        return weight;
    }

    public String getContinent() {
        return continent;
    }

    @Override
    public String toString() {
        return String.format("%s(%.2f/%.2f[%s]):%s", capitalizeFirstLetter(name), weight, lifespan, isMammal(), continent);
    }

    // Capitalize the first letter of the name (jei reiktu)
    private String capitalizeFirstLetter(String text) {
        if (text == null || text.isEmpty()) {
            return text;
        }
        return text.substring(0, 1).toUpperCase() + text.substring(1).toLowerCase();
    }
}
