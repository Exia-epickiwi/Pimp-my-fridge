package fr.epickiwi.pmf;

import fr.epickiwi.pmf.controller.Controller;
import fr.epickiwi.pmf.model.Model;
import fr.epickiwi.pmf.view.GuiView;
import fr.epickiwi.pmf.view.SerialView;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Class principale de l'application
 */
public class Main extends Application {

    private Controller controller;

    /**
     * Démarre l'application JAVAFx
     * @param primaryStage
     * La fenetre principale de l'application
     * @throws Exception
     * N'importe quel exception de l'application
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Model model = new Model();

        GuiView view = new GuiView(model);
        SerialView serialView = new SerialView(model);

        Controller controller = new Controller(model);
        controller.setGuiView(view);
        controller.setSerialView(serialView);

        this.controller = controller;
        controller.start();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        controller.stopApplication();
    }

    /**
     * Le point d'entrée de l'application
     * @param args
     * Les arguments donnés a l'application
     */
    public static void main(String[] args){ launch(args); }
}
