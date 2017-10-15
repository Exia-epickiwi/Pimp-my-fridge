package fr.epickiwi.pmf.view;

import fr.epickiwi.pmf.model.Model;
import gnu.io.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Enumeration;

/**
 * Une vue de l'application en charge de communiquer avec le port série
 */
public class SerialView extends fr.epickiwi.pmf.view.View {

    private InputStreamReader inputStream;
    private OutputStreamWriter outputStream;

    /**
     * Construit la vue
     *
     * @param model Le view de l'application
     */
    public SerialView(Model model) {
        super(model);
        this.model.getSerialConnection().connectedPortProperty().addListener(new OnSerialPortConnect());
    }

    public ArrayList<CommPortIdentifier> searchPorts(){
        Enumeration portList =  CommPortIdentifier.getPortIdentifiers();
        ArrayList<CommPortIdentifier> availablePorts = new ArrayList<>();

        while(portList.hasMoreElements()){
            CommPortIdentifier port = (CommPortIdentifier) portList.nextElement();

            if(port.getPortType() == CommPortIdentifier.PORT_SERIAL){
                availablePorts.add(port);
            }

        }

        return availablePorts;
    }

    public SerialPort connectToPort(CommPortIdentifier selectedPort){

        SerialPort commPort = null;
        try{
            commPort = (SerialPort) selectedPort.open("Pimp my fridge",10);
        } catch (Exception e){
            System.out.println("Impossible d'ouvrir le port "+selectedPort.getName());
            System.err.println(e);
            return null;
        }
        return commPort;
    }

    public void disconnectPort() {
        if(this.model.getSerialConnection().getConnectedPort() == null)
            return;
        this.model.getSerialConnection().getConnectedPort().close();
        this.model.getSerialConnection().setConnected(false);
        this.model.getSerialConnection().setConnectedPort(null);
        System.out.println("Déconnecté du port série");
    }

    /* ----- EVENT LISTENERS ----- */

    private class OnSerialPortConnect implements ChangeListener<SerialPort> {
        @Override
        public void changed(ObservableValue<? extends SerialPort> observableValue, SerialPort serialPort, SerialPort t1) {
            if(t1 == null)
                return;
            try {
                inputStream = new InputStreamReader(t1.getInputStream());
                outputStream = new OutputStreamWriter(t1.getOutputStream());
                System.out.println("Streams connectés");
                t1.addEventListener(new OnSerialEvent());
                t1.notifyOnDataAvailable(true);
                System.out.println("Communicateur connecté");
                model.getSerialConnection().setConnected(true);
            } catch(Exception e){
                System.err.println("Impossible de se connecter au port série");
                e.printStackTrace();
            }
        }
    }

    private class OnSerialEvent implements SerialPortEventListener {
        @Override
        public void serialEvent(SerialPortEvent serialPortEvent) {
        }
    }

    /* ----- GETTERS AND SETTERS ----- */

}
