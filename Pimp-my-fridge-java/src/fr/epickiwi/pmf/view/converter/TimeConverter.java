package fr.epickiwi.pmf.view.converter;

import javafx.util.StringConverter;

public class TimeConverter extends StringConverter<Number> {
    @Override
    public String toString(Number integer) {
        return integer+" s";
    }

    @Override
    public Number fromString(String s) {
        return null;
    }
}
