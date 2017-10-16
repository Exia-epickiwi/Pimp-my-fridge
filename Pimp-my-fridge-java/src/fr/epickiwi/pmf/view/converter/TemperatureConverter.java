package fr.epickiwi.pmf.view.converter;

import javafx.util.StringConverter;

public class TemperatureConverter extends StringConverter<Number> {

    private int maxDecimals;

    public TemperatureConverter(int maxDecimals) {
        this.maxDecimals = maxDecimals;
    }

    public String getFormatedNumber(Number number) {
        double power = Math.pow(10,this.maxDecimals);
        double result = Math.round(((double) number)*power)/Math.pow(10,this.maxDecimals);

        if(this.maxDecimals > 0)
            return ""+result;
        return ""+((int)result);
    }

    @Override
    public String toString(Number number) {
        return this.getFormatedNumber(number)+"°C";
    }

    @Override
    public Number fromString(String s) {
        return Double.parseDouble(s.replace("°C",""));
    }
}
