package fr.epickiwi.pmf.controller;

import fr.epickiwi.pmf.model.FridgeSettings;
import fr.epickiwi.pmf.model.Model;
import fr.epickiwi.pmf.view.GuiView;
import fr.epickiwi.pmf.view.SerialView;

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
