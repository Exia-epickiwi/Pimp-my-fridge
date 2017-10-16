package fr.epickiwi.pmf.view.gui;

import fr.epickiwi.pmf.view.View;

public abstract class ViewController {

    protected View view;

    public void setView(View view) {
        this.view = view;
    }

}
