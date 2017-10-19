package fr.epickiwi.pmf.view.gui;

import fr.epickiwi.pmf.model.FridgeSettings;
import fr.epickiwi.pmf.model.Model;
import fr.epickiwi.pmf.view.View;
import fr.epickiwi.pmf.view.converter.PercentageConverter;
import fr.epickiwi.pmf.view.converter.TemperatureConverter;
import fr.epickiwi.pmf.view.converter.TimeConverter;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

public class MainController extends ViewController {

    private static final int CHART_AXIS_SIZE = 50;

    @FXML
    private ComboBox<Double> preSetTemp;
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
    private LineChart<Number,Number> temperatureLineChart;
    @FXML
    private LineChart<Number,Number> humidityLineChart;

    private XYChart.Series<Number,Number> temperatureSerie;
    private XYChart.Series<Number,Number> orderTemperatureSerie;
    private XYChart.Series<Number,Number> humiditySerie;
    private XYChart.Series<Number, Number> dewPointSerie;

    private DoubleProperty orderTemperatureProperty;
    private DoubleProperty currentDewPointProperty;

    @FXML
    private void initialize(){
    }

    @Override
    public void setView(View view) {
        super.setView(view);
        this.orderTemperatureProperty = this.view.getModel().getFridgeSettings().orderTemperatureProperty();
        BooleanProperty avoidCondensationProperty = this.view.getModel().getFridgeSettings().avoidCondensationProperty();
        DoubleProperty currentTempertureProperty = this.view.getModel().getSensorValues().temperatureProperty();
        IntegerProperty remainingTimeProperty = this.view.getModel().getSensorValues().remainingTimeProperty();
        DoubleProperty currentHumidityProperty = this.view.getModel().getSensorValues().humidityProperty();
        this.currentDewPointProperty = this.view.getModel().getSensorValues().dewPointProperty();

        bigOrderLabel.textProperty().bindBidirectional(orderTemperatureProperty,new TemperatureConverter(0));
        orderLabel.textProperty().bindBidirectional(orderTemperatureProperty,new TemperatureConverter(0));
        humidityLabel.textProperty().bindBidirectional(currentHumidityProperty,new PercentageConverter());

        currentTemperatureLabel.textProperty().bindBidirectional(currentTempertureProperty,new TemperatureConverter(2));
        dewPointLabel.textProperty().bindBidirectional(currentDewPointProperty,new TemperatureConverter(2));
        remainingTimeLabel.textProperty().bindBidirectional(remainingTimeProperty,new TimeConverter());

        avoidCondensation.selectedProperty().bindBidirectional(avoidCondensationProperty);
        //ComboBox
        preSetTemp.setItems(this.view.getModel().getPreSetTemp());

        this.temperatureSerie = new XYChart.Series<>();
        this.temperatureSerie.setName("Température actuelle");
        this.orderTemperatureSerie = new XYChart.Series<>();
        this.orderTemperatureSerie.setName("Consigne");
        this.dewPointSerie = new XYChart.Series<>();
        this.dewPointSerie.setName("Point de rosée");
        this.humiditySerie = new XYChart.Series<>();
        this.humiditySerie.setName("Humidité");
        currentTempertureProperty.addListener(new OnCurrentTemperatureChange());
        currentHumidityProperty.addListener(new OnCurrentHumidityChange());
        currentDewPointProperty.addListener(new OnCurrentDewPointChange());
        currentDewPointProperty.addListener(new OnCurrentOrderTemperatureChange());
        orderTemperatureProperty.addListener(new OnCurrentOrderTemperatureChange());

        this.temperatureLineChart.getData().add(this.temperatureSerie);
        this.temperatureLineChart.getData().add(this.orderTemperatureSerie);
        this.temperatureLineChart.getData().add(this.dewPointSerie);
        this.humidityLineChart.getData().add(this.humiditySerie);
        ((NumberAxis) temperatureLineChart.getXAxis()).setAutoRanging(false);
        ((NumberAxis) humidityLineChart.getXAxis()).setAutoRanging(false);
    }

    @FXML
    private void OnOrderPlusButtonAction(){
        this.view.getController().getMainController().incrementOrderTemperature(1);
    }

    @FXML
    private void OnOrderMinusButtonAction(){
        this.view.getController().getMainController().incrementOrderTemperature(-1);
    }

    @FXML
    private void OnPreSetSelect() {
        FridgeSettings fridgeSettings = this.view.getModel().getFridgeSettings();
        fridgeSettings.setOrderTemperature(preSetTemp.getSelectionModel().getSelectedItem());
    }

    /* ----- EVENT LISTENERS ----- */

    private class OnCurrentTemperatureChange extends TimeChangeListener{
        @Override
        public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
            long currentTick = this.getCurrentSeconds();

            temperatureSerie.getData().add(new XYChart.Data<>(currentTick, t1));
            ((NumberAxis) temperatureLineChart.getXAxis()).setUpperBound((double) currentTick);
            ((NumberAxis) temperatureLineChart.getXAxis()).setLowerBound((double) currentTick-CHART_AXIS_SIZE);
        }
    }

    private class OnCurrentDewPointChange extends TimeChangeListener{
        @Override
        public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
            long currentTick = this.getCurrentSeconds();

            dewPointSerie.getData().add(new XYChart.Data<>(currentTick, currentDewPointProperty.get()));
            ((NumberAxis) temperatureLineChart.getXAxis()).setUpperBound((double) currentTick);
            ((NumberAxis) temperatureLineChart.getXAxis()).setLowerBound((double) currentTick-CHART_AXIS_SIZE);
        }
    }

    private class OnCurrentOrderTemperatureChange extends TimeChangeListener{
        @Override
        public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
            long currentTick = this.getCurrentSeconds();

            orderTemperatureSerie.getData().add(new XYChart.Data<>(currentTick, orderTemperatureProperty.get()));
            ((NumberAxis) temperatureLineChart.getXAxis()).setUpperBound((double) currentTick);
            ((NumberAxis) temperatureLineChart.getXAxis()).setLowerBound((double) currentTick-CHART_AXIS_SIZE);
        }
    }

    private class OnCurrentHumidityChange extends TimeChangeListener{
        @Override
        public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
            long currentTick = this.getCurrentSeconds();

            humiditySerie.getData().add(new XYChart.Data<>(currentTick, t1));
            ((NumberAxis) humidityLineChart.getXAxis()).setUpperBound((double) currentTick);
            ((NumberAxis) humidityLineChart.getXAxis()).setLowerBound((double) currentTick-CHART_AXIS_SIZE);
        }
    }

    private void preSetTemp(){
        preSetTemp.setItems(this.view.getModel().getPreSetTemp());
    }

}
