package fr.epickiwi.pmf.model;

/**
 * Le view de l'application en charge du stockage des données de l'application
 */
public class Model {

    private SerialConnection serialConnection;
    private SensorValues sensorValues;
    private FridgeSettings fridgeSettings;

    public Model() {
        this.serialConnection = new SerialConnection();
        this.sensorValues = new SensorValues();
        this.fridgeSettings = new FridgeSettings();
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
}
