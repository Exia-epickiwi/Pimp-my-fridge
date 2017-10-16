package fr.epickiwi.pmf.view.gui;

import fr.epickiwi.pmf.view.View;
import fr.epickiwi.pmf.view.converter.PercentageConverter;
import fr.epickiwi.pmf.view.converter.TemperatureConverter;
import fr.epickiwi.pmf.view.converter.TimeConverter;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.util.converter.NumberStringConverter;

public class MainController extends ViewController {

    @FXML
    private Label bigOrderLabel;
    @FXML
    private Label currentTemperatureLabel;
    @FXML
    private Label orderLabel;
    @FXML
    private Label remainingTimeLabel;
    @FXML
    private Label humidityLabel;
    @FXML
    private Label dewPointLabel;
    @FXML
    private CheckBox avoidCondensation;

    @FXML
    private void initialize(){
    }

    @Override
    public void setView(View view) {
        super.setView(view);
        DoubleProperty orderTemperatureProperty = this.view.getModel().getFridgeSettings().orderTemperatureProperty();
        BooleanProperty avoidCondensationProperty = this.view.getModel().getFridgeSettings().avoidCondensationProperty();
        DoubleProperty currentTempertureProperty = this.view.getModel().getSensorValues().temperatureProperty();
        IntegerProperty remainingTimeProperty = this.view.getModel().getSensorValues().remainingTimeProperty();
        DoubleProperty currentHumidityProperty = this.view.getModel().getSensorValues().humidityProperty();
        DoubleProperty currentDewPointProperty = this.view.getModel().getSensorValues().dewPointProperty();

        bigOrderLabel.textProperty().bindBidirectional(orderTemperatureProperty,new TemperatureConverter(0));
        orderLabel.textProperty().bindBidirectional(orderTemperatureProperty,new TemperatureConverter(0));
        humidityLabel.textProperty().bindBidirectional(currentHumidityProperty,new PercentageConverter());

        currentTemperatureLabel.textProperty().bindBidirectional(currentTempertureProperty,new TemperatureConverter(2));
        dewPointLabel.textProperty().bindBidirectional(currentDewPointProperty,new TemperatureConverter(2));
        remainingTimeLabel.textProperty().bindBidirectional(remainingTimeProperty,new TimeConverter());

        avoidCondensation.selectedProperty().bindBidirectional(avoidCondensationProperty);
    }

    @FXML
    private void OnOrderPlusButtonAction(){
        this.view.getController().getMainController().incrementOrderTemperature(1);
    }

    @FXML
    private void OnOrderMinusButtonAction(){
        this.view.getController().getMainController().incrementOrderTemperature(-1);
    }

}
