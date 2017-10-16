package fr.epickiwi.pmf.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class FridgeSettings {

    private FloatProperty orderTemperature;
    private BooleanProperty avoidCondensation;
    private ObservableList<TemperaturePreset> presets;

    public FridgeSettings() {
        this.orderTemperature = new SimpleFloatProperty(0);
        this.avoidCondensation = new SimpleBooleanProperty(false);
        this.presets = FXCollections.observableArrayList();
    }

    /* ----- GETTERS AND SETTERS ----- */

    public float getOrderTemperature() {
        return orderTemperature.get();
    }

    public FloatProperty orderTemperatureProperty() {
        return orderTemperature;
    }

    public void setOrderTemperature(float orderTemperature) {
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
