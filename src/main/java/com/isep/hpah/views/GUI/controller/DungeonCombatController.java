package com.isep.hpah.views.GUI.controller;

import com.isep.hpah.controller.GameEngine;
import com.isep.hpah.controller.GameLogic;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextFlow;

public class DungeonCombatController {
    GameEngine gameeng = new GameEngine();
    GameLogic gamelgc = new GameLogic();
    DungeonPresentationController dngs = new DungeonPresentationController();
    SetupController stpcon = new SetupController();



    @FXML
    private Button attButton;

    @FXML
    private Button cancelButton;

    @FXML
    private Button defButton;

    @FXML
    private TextFlow enemyDesc;

    @FXML
    private ImageView enemyImg;

    @FXML
    private Label enemyName;

    @FXML
    private VBox enemyVBox;

    @FXML
    private TextFlow gameText;

    @FXML
    private HBox menuBottom;

    @FXML
    private TextFlow playerDesc;

    @FXML
    private ImageView playerImg;

    @FXML
    private Label playerName;

    @FXML
    private VBox playerVBox;

    @FXML
    private Button popoButton;

    @FXML
    private TextFlow roundHeader;

    @FXML
    private BorderPane skeletonDivider;

    @FXML
    private Button spellsButton;

}
