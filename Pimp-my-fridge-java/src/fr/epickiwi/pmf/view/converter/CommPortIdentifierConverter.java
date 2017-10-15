package fr.epickiwi.pmf.view.converter;

import gnu.io.CommPortIdentifier;
import javafx.util.StringConverter;

public class CommPortIdentifierConverter extends StringConverter<CommPortIdentifier> {
    @Override
    public String toString(CommPortIdentifier commPortIdentifier) {
        return commPortIdentifier.getName();
    }

    @Override
    public CommPortIdentifier fromString(String s) {
        return null;
    }
}
