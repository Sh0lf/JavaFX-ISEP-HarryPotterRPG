package com.isep.hpah.views.GUI.controller;

import com.isep.hpah.GUIParser;
import com.isep.hpah.views.GUI.StageLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;

public class IntroductionController {

        @FXML
        private Label gameStart;

        @FXML
        private Label gameStart1;

        @FXML
        private Button startButton;

        @FXML
        private Button cancelButton;

        @FXML
        void onStartButtonClick() throws IOException {
                StageLoader.loadFXMLScene("/com/isep/hpah/scenes/setupPlayer.fxml");
        }

        @FXML
        void onCancelButtonClick() {
                Stage stage = (Stage) cancelButton.getScene().getWindow();
                stage.close();
        }

}
