package fr.epickiwi.pmf.model;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SerialConnection {

    private ObservableList<CommPortIdentifier> availablePorts;
    private ObjectProperty<SerialPort> connectedPort;
    private BooleanProperty connected;

    public SerialConnection()
    {
        this.availablePorts = FXCollections.observableArrayList();
        this.connected = new SimpleBooleanProperty(false);
        this.connectedPort = new SimpleObjectProperty<>();
    }

    /* ----- GETTERS AND SETTERS ----- */

    public ObservableList<CommPortIdentifier> getAvailablePorts() {
        return availablePorts;
    }

    public void setAvailablePorts(ObservableList<CommPortIdentifier> availablePorts) {
        this.availablePorts = availablePorts;
    }

    public SerialPort getConnectedPort() {
        return connectedPort.get();
    }

    public ObjectProperty<SerialPort> connectedPortProperty() {
        return connectedPort;
    }

    public void setConnectedPort(SerialPort connectedPort) {
        this.connectedPort.set(connectedPort);
    }

    public boolean isConnected() {
        return connected.get();
    }

    public BooleanProperty connectedProperty() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected.set(connected);
    }
}
