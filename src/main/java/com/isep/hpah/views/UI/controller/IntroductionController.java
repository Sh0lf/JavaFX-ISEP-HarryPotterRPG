package com.isep.hpah.views.UI.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;


public class IntroductionController {
        @FXML
        private Label label;

        @FXML
        protected void onButtonClick() {label.setText("Get ready !!!");
        }
}
