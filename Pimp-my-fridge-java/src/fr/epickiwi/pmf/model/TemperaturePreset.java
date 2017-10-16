package fr.epickiwi.pmf.model;

public class TemperaturePreset {

    private String name;
    private float temperature;

    public TemperaturePreset(String name, float temperature) {
        this.name = name;
        this.temperature = temperature;
    }

    public String getName() {
        return name;
    }

    public float getTemperature() {
        return temperature;
    }
}
