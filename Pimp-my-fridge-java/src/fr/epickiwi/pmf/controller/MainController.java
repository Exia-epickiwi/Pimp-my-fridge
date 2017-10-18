package fr.epickiwi.pmf.controller;

import fr.epickiwi.pmf.model.FridgeSettings;
import fr.epickiwi.pmf.model.Model;
import fr.epickiwi.pmf.view.GuiView;
import fr.epickiwi.pmf.view.SerialView;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.scene.control.Alert;
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
        this.model.getSensorValues().dewPointProperty().addListener(new OnDewPointChange());

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
        double lastDewPoint = this.model.getSensorValues().getDewPoint();
        boolean isAvoiding = this.model.getFridgeSettings().isAvoidCondensation();
        if((lastValue + amount < lastDewPoint + 2.0) && isAvoiding) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Attention !");
            alert.setHeaderText("Vous avez coché \"Empêcher la condensation\"");
            alert.setContentText("La température demandée est trop proche du point de rosée !");

            alert.showAndWait();
            return;
        }
        fridgeSettings.setOrderTemperature(lastValue + amount);

    }

    public class OnDewPointChange implements ChangeListener<Number>{
        @Override
        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
            FridgeSettings fridgeSettings = model.getFridgeSettings();
            double newDew = (double)newValue;
            double lastTemp = fridgeSettings.getOrderTemperature();
            boolean isAvoiding = model.getFridgeSettings().isAvoidCondensation();
            if((newDew > lastTemp - 2.0) && isAvoiding){
                fridgeSettings.setOrderTemperature(lastTemp+2.0);
                return;
            }
        }
    }

    }
