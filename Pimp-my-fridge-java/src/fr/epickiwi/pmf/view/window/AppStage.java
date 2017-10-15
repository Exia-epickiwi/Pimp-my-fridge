package fr.epickiwi.pmf.view.window;

import fr.epickiwi.pmf.view.View;
import fr.epickiwi.pmf.view.gui.ViewController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AppStage extends Stage {

    protected Scene rootScene;
    protected ViewController controller;
    protected View view;

    public AppStage(View view, String fxmlLocation, double width, double height) {

        this.view = view;

        Parent rootNode = null;

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(fxmlLocation));
            rootNode = loader.load();
            this.controller = loader.getController();
            this.controller.setView(this.view);
        } catch(IOException e){
            e.printStackTrace();
            return;
        }

        this.rootScene = new Scene(rootNode,width, height);

        this.setScene(this.rootScene);
    }
}
