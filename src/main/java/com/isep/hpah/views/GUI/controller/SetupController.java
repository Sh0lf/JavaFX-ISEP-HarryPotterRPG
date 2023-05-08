package com.isep.hpah.views.GUI.controller;

import com.isep.hpah.controller.Setup;
import com.isep.hpah.model.constructors.House;
import com.isep.hpah.model.constructors.Pet;
import com.isep.hpah.model.constructors.SortingHat;
import com.isep.hpah.model.constructors.Wand;
import com.isep.hpah.model.constructors.character.Wizard;
import com.isep.hpah.model.constructors.spells.AbstractSpell;
import com.isep.hpah.views.GUI.StageLoader;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class SetupController {
    Setup stp = new Setup();
    SortingHat sortHat = new SortingHat();
    String name;
    House house;
    Pet pet;
    Wand wand;
    String wandName;
    int wandSize;
    int res1;
    int res2;
    int finalRes;
    Text character;
    int ref = 0;
    protected Wizard player;

    String nameComp;
    int res1Comp;
    int res2Comp;
    String wandNameComp;
    int wandSizeComp;

    @FXML
    private Button cancelButton;

    @FXML
    private TextFlow characterText;

    @FXML
    private Button confirmButton;

    @FXML
    private Label houseLabel;

    @FXML
    private TextField nameInput;

    @FXML
    private Label nameLabel;

    @FXML
    private Label sortingHatLabel;

    @FXML
    private TextFlow sortingHatQ1;

    @FXML
    private TextFlow sortingHatQ2;

    @FXML
    private ChoiceBox<String> sortingHatR1;

    @FXML
    private ChoiceBox<String> sortingHatR2;

    @FXML
    private Label txtGame;

    @FXML
    private Label wandLabel;

    @FXML
    private TextField wandNameInput;

    @FXML
    private Label wandNameLabel;

    @FXML
    private TextField wandSizeInput;

    @FXML
    private Label wandSizeLabel;

    @FXML
    void initialize(){

        Text Q1 = new Text("You come here with preferences and preconceptions - certain expectations");
        Text Q2 = new Text("I see... Hmm, I detect something in you. A certain sens of - hmm - what is it ?");

        Q1.setFont(Font.font("Arial", FontPosture.ITALIC, 14));
        Q2.setFont(Font.font("Arial", FontPosture.ITALIC, 14));

        sortingHatQ1.getChildren().add(Q1);
        sortingHatQ2.getChildren().add(Q2);

        sortingHatR1.getItems().removeAll(sortingHatR1.getItems());
        sortingHatR2.getItems().removeAll(sortingHatR2.getItems());

        sortingHatR1.getItems().addAll("I can't wait to start classes", "I can't wait to explore");
        sortingHatR2.getItems().addAll("Daring", "Ambition");

        wandSizeInput.focusedProperty().addListener((arg0, oldValue, newValue) -> {
            if (!newValue) { //when focus lost
                if(!wandSizeInput.getText().matches("[1-9]|1[0-9]|2[0-9]|30")){
                    // when not matches 1-30
                    // Empty field
                    wandSizeInput.clear();
                }
            }
        });
    }

    @FXML
    void onCancelButtonClick() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    void onConfirmButtonClick() throws IOException {

        if (ref == 1){
            nameComp = nameInput.getText();
            res1Comp = (sortingHatR1.getSelectionModel().getSelectedIndex() + 1) *10;
            res2Comp = sortingHatR2.getSelectionModel().getSelectedIndex() + 1;

            wandNameComp = wandNameInput.getText();
            wandSizeComp = Integer.parseInt(wandSizeInput.getText());

            if (!Objects.equals(nameComp, name) || res1Comp != res1 || res2Comp != res2 || !Objects.equals(wandNameComp, wandName) || wandSizeComp != wandSize){
                ref = 0;
            } else {
                player = stp.playerCreation(name, wand, pet, house);

                sortingHatR1.setVisible(false);
                sortingHatR2.setVisible(false);
                houseLabel.setVisible(false);
                nameLabel.setVisible(false);
                sortingHatLabel.setVisible(false);
                wandLabel.setVisible(false);
                wandNameInput.setVisible(false);
                wandSizeInput.setVisible(false);
                wandNameLabel.setVisible(false);
                wandSizeLabel.setVisible(false);
                nameInput.setVisible(false);

                sortingHatQ1.getChildren().clear();
                sortingHatQ2.getChildren().clear();
                characterText.getChildren().clear();

                sortingHatQ1.setLayoutX(30);
                sortingHatQ1.setLayoutY(90);
                sortingHatQ2.setLayoutX(460);
                sortingHatQ2.setLayoutY(250);

                sortingHatQ1.setPrefWidth(300);
                sortingHatQ1.setPrefHeight(420);
                sortingHatQ2.setPrefWidth(300);
                sortingHatQ2.setPrefHeight(100);

                Text stats = new Text("Now, watch out during your journey, there's forbidden spells. If you ever use them, " +
                        "you would raise your corruption gauge. Do not go beyond 100 !\n\n" +
                        "Your stats are:\nHealth: " + player.getHealth() + "\nAtt: " + player.getAtt() +
                        "\nDef: " + player.getDef() + "\nDex: " + player.getDex() + "\nMana: " + player.getMana()+"\n\n");
                stats.setFont(Font.font("Arial", FontPosture.REGULAR, 16));
                sortingHatQ1.getChildren().add(stats);

                Text spellIntro = new Text("Your spells are:\n\n");
                spellIntro.setFont(Font.font("Arial", FontPosture.REGULAR, 16));
                sortingHatQ2.getChildren().add(spellIntro);

                for (AbstractSpell spell : player.getKnownSpells()) {
                    // print out the name and description of the spell
                    Text tempSpell = new Text(spell.getName() + " : " + spell.getDesc()+"\n");
                    tempSpell.setFont(Font.font("Arial", FontPosture.REGULAR, 16));
                    sortingHatQ2.getChildren().add(tempSpell);
                }

                if (player.getHouse().equals(House.GRYFFINDOR)){
                   Text bonus = new Text("Since you're in Gryffindor, you have a defense bonus ! You start with 20 Def instead of 10 !");
                    bonus.setFont(Font.font("Arial", FontPosture.REGULAR, 16));
                    sortingHatQ1.getChildren().add(bonus);
                } else if (player.getHouse().equals(House.RAVENCLAW)){
                    Text bonus = new Text("Since you're in Ravenclaw, you have a dexterity bonus ! You start with 15 Dex instead of 10 !");
                    bonus.setFont(Font.font("Arial", FontPosture.REGULAR, 16));
                    sortingHatQ1.getChildren().add(bonus);
                } else if (player.getHouse().equals(House.SLYTHERIN)){
                    Text bonus = new Text("Since you're in Slytherin, all your spells are more efficient !");
                    bonus.setFont(Font.font("Arial", FontPosture.REGULAR, 16));
                    sortingHatQ1.getChildren().add(bonus);
                } else if (player.getHouse().equals(House.HUFFLEPUFF)){
                    Text bonus = new Text("Since you're in Hufflepuff, all your potions are more efficient !");
                    bonus.setFont(Font.font("Arial", FontPosture.REGULAR, 16));
                    sortingHatQ1.getChildren().add(bonus);
                }
                ref = 2;
            }
        }

        if (ref == 0){
            characterText.getChildren().clear();

            name = nameInput.getText();
            res1 = (sortingHatR1.getSelectionModel().getSelectedIndex() + 1) *10;
            res2 = sortingHatR2.getSelectionModel().getSelectedIndex() + 1;

            wandName = wandNameInput.getText();
            wandSize = Integer.parseInt(wandSizeInput.getText());

            wand = new Wand(wandName, wandSize, stp.generateRandomCore());

            finalRes = res1 + res2;

            house = sortHat.getResHouse(finalRes);

            pet = stp.generateRandomPet();

            character = new Text("Username: "+ name + "\nYour house: " + house.name() + "\nYour wand: " + wand.getName() + ", size: " + wand.getSize() + "\nYour pet: " + pet.name() + "\n\nConfirm choice ? Re-click confirm");
            character.setFont(Font.font("Arial", FontPosture.REGULAR, 14));

            characterText.getChildren().add(character);

            ref = 1;
        }
        else if (ref == 2){
            StageLoader.loadFXMLScene("/com/isep/hpah/scenes/dungeonCombat.fxml");
        }
    }
}
