package fr.epickiwi.pmf.view;

import fr.epickiwi.pmf.model.Model;
import gnu.io.*;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import org.json.JSONObject;

import java.io.IOException;
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
    private DoubleProperty orderTehmperatureProperty;
    private DoubleProperty dataTemperatureProperty;
    private DoubleProperty dataHumidityProperty;

    /**
     * Construit la vue
     *
     * @param model Le view de l'application
     */
    public SerialView(Model model) {
        super(model);
        this.model.getSerialConnection().connectedPortProperty().addListener(new OnSerialPortConnect());
        this.orderTehmperatureProperty = this.model.getFridgeSettings().orderTemperatureProperty();
        this.orderTehmperatureProperty.addListener(new OnOrderTemperatureChange());

        this.dataTemperatureProperty = this.model.getSensorValues().temperatureProperty();
        this.dataHumidityProperty = this.model.getSensorValues().temperatureProperty();
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

    private void sendSettings(){
        JSONObject json = new JSONObject();
        json.accumulate("type","refresh-settings");
        json.accumulate("temperature",this.orderTehmperatureProperty.get());
        this.sendMessage(json.toString());
    }

    private void refreshData(JSONObject json){
        if(json.has("temperature")){
            //this.dataTemperatureProperty.set(json.getDouble("temperature"));
        }
        if(json.has("humidity")){
            //this.dataHumidityProperty.set(json.getDouble("humidity"));
        }
    }

    private void sendMessage(String message){
        if(this.model.getSerialConnection().getConnectedPort() == null){
            System.err.println("Impossible d'envoyer les données à un port non connecté");
            return;
        }
        try {
            this.outputStream.write(message+"\n",0,message.length()+1);
            this.outputStream.flush();
        } catch (IOException e) {
            System.err.println("Impossible d'envoyer les données");
            e.printStackTrace();
        }
        System.out.println("<<< "+message);
    }

    private void dispatchMessage(String json){
        JSONObject parsedJson = new JSONObject(json);
        if(!parsedJson.has("type")){
            System.err.println("Le message n'a pas de type");
            return;
        }

        switch(parsedJson.getString("type")){
            case "refresh-data":
                refreshData(parsedJson);
                break;
            default:
                System.err.println("Type inconnu '"+parsedJson.getString("type")+"'");
                break;
        }
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
        public void serialEvent(SerialPortEvent event) {
            if(event.getEventType() != SerialPortEvent.DATA_AVAILABLE)
                return;

            try {

                StringBuilder textBuilder = new StringBuilder();
                char data = (char)inputStream.read();
                while(data != '\n'){
                    textBuilder.append((char)data);
                    data = (char)inputStream.read();
                }
                String text = textBuilder.toString().replace("\r","");
                System.out.println(">>> "+text);
                dispatchMessage(text);
            } catch (IOException e) {
                System.err.println("Impossible de lire le flux série");
                e.printStackTrace();
            }
        }
    }

    private class OnOrderTemperatureChange implements ChangeListener<Number>{
        @Override
        public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
            sendSettings();
        }
    }

}
