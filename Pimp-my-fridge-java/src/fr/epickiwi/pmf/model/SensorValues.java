package fr.epickiwi.pmf.model;

import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SensorValues {

    public static final String TEMPERATURE_JSON_KEY = "temp";
    public static final String HUMIDITY_JSON_KEY = "hum";
    public static final double DELTA_SECOND = 1.0;

    private FloatProperty temperature;
    private FloatProperty humidity;

    private FloatProperty remainingTime;    //COMPUTED
    private FloatProperty dewPoint;         //COMPUTED

    private ObservableList<Float> tempertatureHistory;
    private ObservableList<Float> humidityHistory;

    public SensorValues() {
        this.temperature = new SimpleFloatProperty(0);
        this.humidity = new SimpleFloatProperty(0);
        this.remainingTime = new SimpleFloatProperty(0);
        this.remainingTime = new SimpleFloatProperty(0);
        this.tempertatureHistory = FXCollections.observableArrayList();
        this.humidityHistory = FXCollections.observableArrayList();
    }

    /* ----- COMPUTED VALUES ----- */

    public void refreshRemainingTime(){
        //TODO implement
        this.setRemainingTime(0);
    }

    public void refreshDewPoint(){
        //TODO implement
        this.setDewPoint(0);
    }

    /* ----- GETTERS AND SETTERS ----- */

    public float getTemperature() {
        return temperature.get();
    }

    public FloatProperty temperatureProperty() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.tempertatureHistory.add(this.getTemperature());
        this.temperature.set(temperature);
        this.refreshDewPoint();
        this.refreshRemainingTime();
    }

    public float getHumidity() {
        return humidity.get();
    }

    public FloatProperty humidityProperty() {
        return humidity;
    }

    public void setHumidity(float humidity) {
        this.humidityHistory.add(this.getHumidity());
        this.humidity.set(humidity);
        this.refreshDewPoint();
        this.refreshRemainingTime();
    }

    public float getRemainingTime() {
        return remainingTime.get();
    }

    public FloatProperty remainingTimeProperty() {
        return remainingTime;
    }

    private void setRemainingTime(float remainingTime) {
        this.remainingTime.set(remainingTime);
    }

    public float getDewPoint() {
        return dewPoint.get();
    }

    public FloatProperty dewPointProperty() {
        return dewPoint;
    }

    private void setDewPoint(float dewPoint) {
        this.dewPoint.set(dewPoint);
    }

    public ObservableList<Float> getTempertatureHistory() {
        return tempertatureHistory;
    }

    public ObservableList<Float> getHumidityHistory() {
        return humidityHistory;
    }
}
