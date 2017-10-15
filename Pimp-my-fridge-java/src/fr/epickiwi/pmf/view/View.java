package fr.epickiwi.pmf.view;

import fr.epickiwi.pmf.controller.Controller;
import fr.epickiwi.pmf.model.Model;

/**
 * Class représentant une vue de l'application
 */
public abstract class View{

    /**
     * Le view de l'application
     */
    protected Model model;

    /**
     * Le controlleur de l'application
     */
    protected Controller controller;

    /**
     * Construit la vue
     * @param model
     * Le view de l'application
     */
    public View(Model model) {
        this.model = model;
    }

    /* ----- GETTETS AND SETTERS ----- */

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public Controller getController() {
        return controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }
}
