package fr.epickiwi.pmf.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Le view de l'application en charge du stockage des données de l'application
 */
public class Model {

    private SerialConnection serialConnection;
    private SensorValues sensorValues;
    private FridgeSettings fridgeSettings;

    private ObservableList<Double> preSetTemp = FXCollections.observableArrayList();


    public Model() {
        this.serialConnection = new SerialConnection();
        this.sensorValues = new SensorValues();
        this.fridgeSettings = new FridgeSettings();

        preSetTemp.add(5.0);
        preSetTemp.add(13.0);
        preSetTemp.add(15.0);
        preSetTemp.add(20.0);

    }

    /* ----- GETTERS AND SETTERS ----- */

    public SerialConnection getSerialConnection() {
        return serialConnection;
    }

    public SensorValues getSensorValues() {
        return sensorValues;
    }

    public FridgeSettings getFridgeSettings() {
        return fridgeSettings;
    }

    public ObservableList<Double> getPreSetTemp() {
        return preSetTemp;
    }
}
