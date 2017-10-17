package fr.epickiwi.pmf.controller;

import fr.epickiwi.pmf.model.FridgeSettings;
import fr.epickiwi.pmf.model.Model;
import fr.epickiwi.pmf.view.GuiView;
import fr.epickiwi.pmf.view.SerialView;
import javafx.application.Platform;
import javafx.concurrent.Task;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;

public class MainController {

    private Model model;
    private SerialView serialView;
    private GuiView guiView;

    public MainController(Model model) {
        this.model = model;
    }

    public void launchMain(){
        this.guiView.getMainStage().show();
        this.guiView.getInstallStage().close();
    }

    public void dispatchMessage(String json){
        JSONObject parsedJson = null;
        try {
            parsedJson = new JSONObject(json);
        }catch(JSONException e){
            System.err.println("Impossible de parser le message");
            return;
        }

        if(!parsedJson.has("type")){
            System.err.println("Le message n'a pas de type");
            return;
        }
        switch(parsedJson.getString("type")){
            case "refresh-data":
                this.refreshData(parsedJson);
                break;
            default:
                System.err.println("Type inconnu '"+parsedJson.getString("type")+"'");
                break;
        }
    }

    private void refreshData(JSONObject json){
        if(json.has("temperature")){
            model.getSensorValues().setTemperature(json.getDouble("temperature"));
        }
        if(json.has("humidity")){
            model.getSensorValues().setHumidity(json.getDouble("humidity"));
        }
    }

    /* ----- RECEPTION DES DONNEES SERIE ----- */

    /* ----- GETTERS AND SETTERS ----- */

    public void setSerialView(SerialView serialView) {
        this.serialView = serialView;
    }

    public void setGuiView(GuiView guiView) {
        this.guiView = guiView;
    }

    public void incrementOrderTemperature(double amount) {
        FridgeSettings fridgeSettings = this.model.getFridgeSettings();
        double lastValue = fridgeSettings.getOrderTemperature();
        fridgeSettings.setOrderTemperature(lastValue+amount);
    }
}
