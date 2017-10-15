package fr.epickiwi.pmf.view.window;

import fr.epickiwi.pmf.view.View;

public class MainStage extends AppStage {

    public MainStage(View view) {
        super(view, "../gui/main.fxml", 805, 426);

        this.setMinWidth(this.rootScene.getWidth());
        this.setMinHeight(this.rootScene.getHeight());
        this.setTitle("Pimp my fridge");

    }

}
