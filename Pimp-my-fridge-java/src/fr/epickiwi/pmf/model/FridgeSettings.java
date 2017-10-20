package fr.epickiwi.pmf.model;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class FridgeSettings {

    private DoubleProperty orderTemperature;
    private BooleanProperty avoidCondensation;
    private ObservableList<TemperaturePreset> presets;

    public FridgeSettings() {
        this.orderTemperature = new SimpleDoubleProperty(18);
        this.avoidCondensation = new SimpleBooleanProperty(false);
        this.presets = FXCollections.observableArrayList();
    }

    /* ----- GETTERS AND SETTERS ----- */

    public double getOrderTemperature() {
        return orderTemperature.get();
    }

    public DoubleProperty orderTemperatureProperty() {
        return orderTemperature;
    }

    public void setOrderTemperature(double orderTemperature) {
        this.orderTemperature.set(orderTemperature);
    }

    public boolean isAvoidCondensation() {
        return avoidCondensation.get();
    }

    public BooleanProperty avoidCondensationProperty() {
        return avoidCondensation;
    }

    public void setAvoidCondensation(boolean avoidCondensation) {
        this.avoidCondensation.set(avoidCondensation);
    }

    public ObservableList<TemperaturePreset> getPresets() {
        return presets;
    }

    public void setPresets(ObservableList<TemperaturePreset> presets) {
        this.presets = presets;
    }
}
