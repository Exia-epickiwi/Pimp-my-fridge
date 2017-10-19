package fr.epickiwi.pmf.controller;

import fr.epickiwi.pmf.model.Model;
import fr.epickiwi.pmf.view.GuiView;
import fr.epickiwi.pmf.view.SerialView;
import fr.epickiwi.pmf.view.View;
import gnu.io.CommPortIdentifier;
import javafx.application.Platform;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * Le controlleur de l'application en charge du traitement des données
 */
public class Controller {

    /**
     * Le view de l'application
     */
    private Model model;

    /**
     * La vue en charge de la communication série
     */
    private SerialView serialView;

    /**
     * La vue en charge de la communication IHM
     */
    private GuiView guiView;

    /**
     * Controlleur en charge de l'installation
     */
    private InstallController installController;

    /**
     * Controlleur en charge de l'application de base
     */
    private MainController mainController;

    /**
     * Construit le controlleur
     * @param model
     * Le modèl de l'application
     */
    public Controller(Model model) {
        this.model = model;
        this.installController = new InstallController(this.model);
        this.mainController = new MainController(this.model);
    }

    private void configureView(View view){
        view.setController(this);
    }

    /**
     * Démarre l'application
     */
    public void start(){

        //Recherche des ports disponibles
        ArrayList<CommPortIdentifier> ports = this.serialView.searchPorts();
        this.model.getSerialConnection().getAvailablePorts().addAll(ports);

        //Affichage de la fenetre de configuration
        this.guiView.getInstallStage().show();
    }

    public void closeAllWindows(){
        this.guiView.getInstallStage().close();
        this.guiView.getMainStage().close();
        this.serialView.disconnectTimer();
    }

    public void stopApplication() {
        this.serialView.disconnectPort();
    }

    public void setSerialView(SerialView serialView) {
        this.serialView = serialView;
        this.configureView(this.serialView);
        this.installController.setSerialView(this.serialView);
        this.mainController.setSerialView(this.serialView);
    }

    public void setGuiView(GuiView guiView) {
        this.guiView = guiView;
        this.configureView(this.guiView);
        this.installController.setGuiView(this.guiView);
        this.mainController.setGuiView(this.guiView);
    }

    /* ----- GETTERS AND SETTERS ----- */

    public InstallController getInstallController() {
        return installController;
    }

    public MainController getMainController() {
        return mainController;
    }
}
