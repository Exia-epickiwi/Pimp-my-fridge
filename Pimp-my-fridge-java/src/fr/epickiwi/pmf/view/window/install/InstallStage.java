package fr.epickiwi.pmf.view.window.install;

import fr.epickiwi.pmf.view.View;
import fr.epickiwi.pmf.view.window.AppStage;

public class InstallStage extends AppStage {

    public InstallStage(View view) {
        super(view, "../../gui/install.fxml", 459, 326);
        this.setResizable(false);
        this.setTitle("Installation - Pimp my fridge");

        this.setOnCloseRequest(t -> this.view.getController().closeAllWindows());
    }
}
