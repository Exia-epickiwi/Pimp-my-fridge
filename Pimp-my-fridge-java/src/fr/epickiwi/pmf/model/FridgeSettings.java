package fr.epickiwi.pmf.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleFloatProperty;

public class FridgeSettings {

    private FloatProperty orderTemperature;
    private BooleanProperty avoidCondensation;

    public FridgeSettings() {
        this.orderTemperature = new SimpleFloatProperty(0);
        this.avoidCondensation = new SimpleBooleanProperty(false);
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
}
