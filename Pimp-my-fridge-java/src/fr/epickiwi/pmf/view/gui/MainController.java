package fr.epickiwi.pmf.view.gui;

import fr.epickiwi.pmf.view.View;
import fr.epickiwi.pmf.view.converter.PercentageConverter;
import fr.epickiwi.pmf.view.converter.TemperatureConverter;
import fr.epickiwi.pmf.view.converter.TimeConverter;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;

public class MainController extends ViewController {

    private static final int CHART_AXIS_SIZE = 50;

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

    /* ----- EVENT LISTENERS ----- */

    private class OnCurrentTemperatureChange implements ChangeListener<Number>{
        private int increment = 0;
        @Override
        public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
            temperatureSerie.getData().add(new XYChart.Data<>(this.increment, t1));
            orderTemperatureSerie.getData().add(new XYChart.Data<>(this.increment, orderTemperatureProperty.get()));
            dewPointSerie.getData().add(new XYChart.Data<>(this.increment, currentDewPointProperty.get()));
            ((NumberAxis) temperatureLineChart.getXAxis()).setUpperBound((double) this.increment);
            ((NumberAxis) temperatureLineChart.getXAxis()).setLowerBound((double) this.increment-CHART_AXIS_SIZE);
            this.increment++;
        }
    }

    private class OnCurrentHumidityChange implements ChangeListener<Number>{
        private int increment = 0;
        @Override
        public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
            humiditySerie.getData().add(new XYChart.Data<>(this.increment, t1));
            ((NumberAxis) humidityLineChart.getXAxis()).setUpperBound((double) this.increment);
            ((NumberAxis) humidityLineChart.getXAxis()).setLowerBound((double) this.increment-CHART_AXIS_SIZE);
            this.increment++;
        }
    }

}
