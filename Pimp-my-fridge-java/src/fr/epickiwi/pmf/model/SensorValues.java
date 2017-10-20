package fr.epickiwi.pmf.model;

import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Date;

import static java.lang.StrictMath.sqrt;

public class SensorValues {

    private DoubleProperty temperature;
    private DoubleProperty humidity;

    private IntegerProperty remainingTime;    //COMPUTED
    private DoubleProperty dewPoint;         //COMPUTED

    private ObservableList<HistoryValue<Double>> tempertatureHistory;
    private ObservableList<HistoryValue<Double>> humidityHistory;
    private Model model;

    public SensorValues(Model model) {
        this.model = model;
        this.temperature = new SimpleDoubleProperty(0);
        this.humidity = new SimpleDoubleProperty(0);
        this.remainingTime = new SimpleIntegerProperty(0);
        this.dewPoint = new SimpleDoubleProperty(0);
        this.tempertatureHistory = FXCollections.observableArrayList();
        this.humidityHistory = FXCollections.observableArrayList();

        this.temperature.addListener(new OnValueChange());
        this.humidity.addListener(new OnValueChange());
    }

    /* ----- COMPUTED VALUES ----- */

    private void refreshRemainingTime(){
        int valueAmount = this.tempertatureHistory.size();
        if(valueAmount < 2)
            return;

        HistoryValue<Double> v1 = this.tempertatureHistory.get(valueAmount-1);
        HistoryValue<Double> v2 = this.tempertatureHistory.get(valueAmount-2);

        double diffTemps = v2.getSysMili() - v1.getSysMili();
        double diffValeurs = v2.getValue() - v1.getValue();
        double coef = diffValeurs/diffTemps;

        double restant = this.getTemperature() - this.model.getFridgeSettings().getOrderTemperature();

        int result = (int) Math.round(coef*restant);

        this.setRemainingTime(result);
    }

    private void refreshDewPoint(){
        double ta = this.getTemperature();
        double ur = this.getHumidity()/100;
        double k = ((17.27*ta)/(237.7+ta))+Math.log(ur);
        double tr = (237.7*k)/(17.27-k);
        if(!Double.isNaN(tr) && ta > 0 && ta < 60)
            this.setDewPoint(tr);
    }

    private class OnValueChange implements ChangeListener<Number> {
        @Override
        public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
            refreshDewPoint();
            refreshRemainingTime();
        }
    }

    /* ----- GETTERS AND SETTERS ----- */

    public void setTemperature(double temperature) {
        this.tempertatureHistory.add(new HistoryValue<>(System.currentTimeMillis(),this.getTemperature()));
        this.temperature.set(temperature);
    }

    public void setHumidity(double humidity) {
        this.humidityHistory.add(new HistoryValue<>(System.currentTimeMillis(),this.getHumidity()));
        this.humidity.set(humidity);
    }

    public double getTemperature() {
        return temperature.get();
    }

    public DoubleProperty temperatureProperty() {
        return temperature;
    }

    public double getHumidity() {
        return humidity.get();
    }

    public DoubleProperty humidityProperty() {
        return humidity;
    }

    public int getRemainingTime() {
        return remainingTime.get();
    }

    public IntegerProperty remainingTimeProperty() {
        return remainingTime;
    }

    public void setRemainingTime(int remainingTime) {
        this.remainingTime.set(remainingTime);
    }

    public double getDewPoint() {
        return dewPoint.get();
    }

    public DoubleProperty dewPointProperty() {
        return dewPoint;
    }

    public void setDewPoint(double dewPoint) {
        this.dewPoint.set(dewPoint);
    }

    public ObservableList<HistoryValue<Double>> getTempertatureHistory() {
        return tempertatureHistory;
    }

    public void setTempertatureHistory(ObservableList<HistoryValue<Double>> tempertatureHistory) {
        this.tempertatureHistory = tempertatureHistory;
    }

    public ObservableList<HistoryValue<Double>> getHumidityHistory() {
        return humidityHistory;
    }

    public void setHumidityHistory(ObservableList<HistoryValue<Double>> humidityHistory) {
        this.humidityHistory = humidityHistory;
    }
}
