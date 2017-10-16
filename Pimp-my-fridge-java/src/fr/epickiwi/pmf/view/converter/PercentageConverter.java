package fr.epickiwi.pmf.view.converter;

import javafx.util.StringConverter;

public class PercentageConverter extends StringConverter<Number> {
    @Override
    public String toString(Number number) {
        return ((int)Math.round((double) number))+" %";
    }

    @Override
    public Number fromString(String s) {
        return null;
    }
}
