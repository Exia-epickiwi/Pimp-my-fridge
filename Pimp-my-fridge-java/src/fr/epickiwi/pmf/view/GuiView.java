package fr.epickiwi.pmf.view;

import fr.epickiwi.pmf.model.Model;
import fr.epickiwi.pmf.view.window.install.InstallStage;
import fr.epickiwi.pmf.view.window.MainStage;

/**
 * Une vue de l'application pour la communication avec l'interface d'utilisateur
 */
public class GuiView extends fr.epickiwi.pmf.view.View {

    /**
     * La fenêtre principale du projet
     */
    private MainStage mainStage;

    /**
     * La fenêtre d'installation de l'application
     */
    private InstallStage installStage;

    /**
     * Construit la vue GUI
     *
     * @param model
     * Le view de l'application
     */
    public GuiView(Model model) {
        super(model);

        this.installStage = new InstallStage(this);
        this.mainStage = new MainStage(this);
    }

    public MainStage getMainStage() {
        return mainStage;
    }

    public InstallStage getInstallStage() {
        return installStage;
    }
}
