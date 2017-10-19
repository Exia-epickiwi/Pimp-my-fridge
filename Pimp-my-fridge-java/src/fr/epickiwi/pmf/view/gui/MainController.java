package fr.epickiwi.pmf.view.gui;

import fr.epickiwi.pmf.model.FridgeSettings;
import fr.epickiwi.pmf.model.Model;
import fr.epickiwi.pmf.view.View;
import fr.epickiwi.pmf.view.converter.PercentageConverter;
import fr.epickiwi.pmf.view.converter.TemperatureConverter;
import fr.epickiwi.pmf.view.converter.TimeConverter;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
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
    private DoubleProperty currentTempertureProperty;
    private DoubleProperty currentHumidityProperty;
    private MainController.OnTimerTick chartTimer;

    @FXML
    private void initialize(){
        this.chartTimer = new OnTimerTick();
        new Thread(this.chartTimer).start();
    }

    @Override
    public void setView(View view) {
        super.setView(view);
        this.orderTemperatureProperty = this.view.getModel().getFridgeSettings().orderTemperatureProperty();
        BooleanProperty avoidCondensationProperty = this.view.getModel().getFridgeSettings().avoidCondensationProperty();
        this.currentTempertureProperty = this.view.getModel().getSensorValues().temperatureProperty();
        IntegerProperty remainingTimeProperty = this.view.getModel().getSensorValues().remainingTimeProperty();
        this.currentHumidityProperty = this.view.getModel().getSensorValues().humidityProperty();
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

        this.temperatureLineChart.getData().add(this.temperatureSerie);
        this.temperatureLineChart.getData().add(this.orderTemperatureSerie);
        this.temperatureLineChart.getData().add(this.dewPointSerie);
        this.humidityLineChart.getData().add(this.humiditySerie);
        temperatureLineChart.getXAxis().setAutoRanging(false);
        humidityLineChart.getXAxis().setAutoRanging(false);
        humidityLineChart.getYAxis().setAutoRanging(false);
        ((NumberAxis) humidityLineChart.getYAxis()).setUpperBound(100);
        ((NumberAxis) humidityLineChart.getYAxis()).setLowerBound(0);
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
        FridgeSettings settings = this.view.getModel().getFridgeSettings();
        if(preSetTemp.getSelectionModel().getSelectedItem() != null && preSetTemp.getSelectionModel().getSelectedItem() instanceof Double)
            settings.setOrderTemperature(preSetTemp.getSelectionModel().getSelectedItem());
    }

    public void stopChartTimer(){
        this.chartTimer.setRefreshingCharts(false);
    }

    /* ----- EVENT LISTENERS ----- */

    private class OnTimerTick extends Task<Void>{
        private long startTime = System.currentTimeMillis();
        private boolean refreshingCharts = true;
        @Override
        protected Void call() throws Exception {
            while(this.refreshingCharts) {
                long currentTick = (System.currentTimeMillis() - startTime)/1000;

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        temperatureSerie.getData().add(new XYChart.Data<>(currentTick, currentTempertureProperty.get()));
                        dewPointSerie.getData().add(new XYChart.Data<>(currentTick, currentDewPointProperty.get()));
                        orderTemperatureSerie.getData().add(new XYChart.Data<>(currentTick, orderTemperatureProperty.get()));
                        humiditySerie.getData().add(new XYChart.Data<>(currentTick, currentHumidityProperty.get()));

                        ((NumberAxis) temperatureLineChart.getXAxis()).setUpperBound((double) currentTick);
                        ((NumberAxis) temperatureLineChart.getXAxis()).setLowerBound((double) currentTick - CHART_AXIS_SIZE);
                        ((NumberAxis) humidityLineChart.getXAxis()).setUpperBound((double) currentTick);
                        ((NumberAxis) humidityLineChart.getXAxis()).setLowerBound((double) currentTick - CHART_AXIS_SIZE);
                    }
                });

                Thread.sleep(1000);
            }
            return null;
        }

        public boolean isRefreshingCharts() {
            return refreshingCharts;
        }

        public void setRefreshingCharts(boolean refreshingCharts) {
            this.refreshingCharts = refreshingCharts;
        }
    }

    /*private void preSetTemp(){
        preSetTemp.setItems(this.view.getModel().getPreSetTemp());
    }*/

}
