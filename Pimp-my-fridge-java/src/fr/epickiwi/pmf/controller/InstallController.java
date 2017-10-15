package fr.epickiwi.pmf.controller;

import fr.epickiwi.pmf.model.Model;
import fr.epickiwi.pmf.view.GuiView;
import fr.epickiwi.pmf.view.SerialView;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

public class InstallController {

    private Model model;
    private SerialView serialView;
    private GuiView guiView;

    public InstallController(Model model) {
        this.model = model;
    }

    public void connectToPort(CommPortIdentifier port){
        SerialPort connectedPort = this.serialView.connectToPort(port);
        this.model.getSerialConnection().setConnectedPort(connectedPort);
    }

    /* ----- GETTERS AND SETTERS ----- */

    public void setSerialView(SerialView serialView) {
        this.serialView = serialView;
    }

    public void setGuiView(GuiView guiView) {
        this.guiView = guiView;
    }
}
