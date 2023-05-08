package com.isep.hpah;

import com.isep.hpah.views.GUI.*;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class GUIParser extends Application{
    public void start(Stage stage) throws IOException {
        StageLoader.setStage(stage);
        StageLoader.loadFXMLScene("/com/isep/hpah/scenes/introduction.fxml");
    }

    public void launchInterface()
    {
        launch();
    }
}
