package fr.epickiwi.pmf.view.gui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public abstract class TimeChangeListener implements ChangeListener<Number> {
    protected long startTime = System.currentTimeMillis();

    protected long getCurrentSeconds(){
        return (System.currentTimeMillis() - startTime)/1000;
    }

    @Override
    abstract public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1);
}
