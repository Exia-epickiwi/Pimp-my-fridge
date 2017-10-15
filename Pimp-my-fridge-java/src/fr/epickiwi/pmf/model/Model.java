package fr.epickiwi.pmf.model;

/**
 * Le view de l'application en charge du stockage des données de l'application
 */
public class Model {

    private SerialConnection serialConnection;

    public Model() {
        serialConnection = new SerialConnection();
    }

    /* ----- GETTERS AND SETTERS ----- */

    public SerialConnection getSerialConnection() {
        return serialConnection;
    }

    public void setSerialConnection(SerialConnection serialConnection) {
        this.serialConnection = serialConnection;
    }
}
