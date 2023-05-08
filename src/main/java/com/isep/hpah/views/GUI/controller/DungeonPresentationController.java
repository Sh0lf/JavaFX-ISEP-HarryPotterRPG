package com.isep.hpah.views.GUI.controller;

import com.isep.hpah.controller.Setup;
import com.isep.hpah.model.constructors.Dungeon;
import com.isep.hpah.views.GUI.StageLoader;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.*;

import java.io.IOException;
import java.util.List;

public class DungeonPresentationController {
        Setup stp = new Setup();
        protected List<Dungeon> dungeons = stp.allDungeon();
        String dungeonNameTxt = dungeons.get(0).getName();
        String dungeonDescTxt = dungeons.get(0).getDesc();

        @FXML
        private Button confirmButton;

        @FXML
        private TextFlow dungeonDesc;

        @FXML
        private TextFlow dungeonName;

        void initialize() {
                Text dungeonNameText = new Text(dungeonNameTxt);
                Text dungeonDescText = new Text(dungeonDescTxt);

                dungeonNameText.setFont(Font.font("Arial", FontWeight.BOLD, 26));
                dungeonDescText.setFont(Font.font("Arial", FontPosture.REGULAR, 14));

                dungeonName.getChildren().add(dungeonNameText);
                dungeonDesc.getChildren().add(dungeonDescText);
        }

        @FXML
        void onConfirmButtonClick() throws IOException {
                StageLoader.loadFXMLScene("/com/isep/hpah/scenes/dungeonCombat.fxml");
        }

}


