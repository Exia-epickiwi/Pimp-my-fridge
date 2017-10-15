package fr.epickiwi.pmf.view.gui;

import fr.epickiwi.pmf.view.View;
import fr.epickiwi.pmf.view.converter.CommPortIdentifierConverter;
import fr.epickiwi.pmf.view.window.install.InstallState;
import gnu.io.CommPortIdentifier;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

public class InstallController extends ViewController {

    private InstallState state;

    @FXML
    private ComboBox<CommPortIdentifier> serialSetComboBox;

    @FXML
    private Button nextButton;

    @FXML
    private Node helloPanel;

    @FXML
    private Node portSelectionPanel;

    @FXML
    private Node portConnectionPanel;

    @FXML
    private Node finishedPanel;

    @FXML
    private void initialize(){
        serialSetComboBox.setConverter(new CommPortIdentifierConverter());
        this.setState(InstallState.WELCOME);
    }
    @Override
    public void setView(View view) {
        super.setView(view);
        this.view.getModel().getSerialConnection().connectedProperty().addListener(new OnPortConnected());
        serialSetComboBox.setItems(this.view.getModel().getSerialConnection().getAvailablePorts());
    }

    private void setState(InstallState state){
        this.state = state;
        helloPanel.setVisible(false);
        portConnectionPanel.setVisible(false);
        finishedPanel.setVisible(false);
        portSelectionPanel.setVisible(false);
        nextButton.setDisable(false);

        switch(state){
            case WELCOME:
                helloPanel.setVisible(true);
                break;
            case SELECT_PORT:
                portSelectionPanel.setVisible(true);
                if(serialSetComboBox.getValue() == null)
                    nextButton.setDisable(true);
                break;
            case CONNECTING:
                portConnectionPanel.setVisible(true);
                nextButton.setDisable(true);
                break;
            case FINISHED:
                finishedPanel.setVisible(true);
                nextButton.setText("Lancer l'application");
                break;
        }
    }

    @FXML
    private void onCloseButtonAction(){
        this.view.getController().closeAllWindows();
    }

    @FXML
    private void onPortSelected(){
        if(serialSetComboBox.getValue() == null)
            nextButton.setDisable(true);
        else
            nextButton.setDisable(false);
    }

    @FXML
    private void onNextButtonAction(){
        switch(this.state){
            case WELCOME:
                this.setState(InstallState.SELECT_PORT);
                break;
            case SELECT_PORT:
                this.setState(InstallState.CONNECTING);
                this.view.getController().getInstallController().connectToPort(serialSetComboBox.getValue());
                break;
            case CONNECTING:
                this.setState(InstallState.FINISHED);
                break;
            case FINISHED:
                this.view.getController().getMainController().launchMain();
                break;
        }
    }

    /* ----- EVENT LISTENERS ----- */

    private class OnPortConnected implements ChangeListener<Boolean>{
        @Override
        public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
            if(state == InstallState.CONNECTING && t1){
                setState(InstallState.FINISHED);
            }
        }
    }
}
