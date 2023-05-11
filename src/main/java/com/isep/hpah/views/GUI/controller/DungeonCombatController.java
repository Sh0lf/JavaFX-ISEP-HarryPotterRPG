package com.isep.hpah.views.GUI.controller;

import com.isep.hpah.controller.Setup;
import com.isep.hpah.model.constructors.House;
import com.isep.hpah.model.constructors.character.Character;
import com.isep.hpah.model.constructors.character.Wizard;
import com.isep.hpah.views.console.SafeScanner;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class DungeonCombatController {
    DungeonPresentationController dngs = new DungeonPresentationController();
    SetupController stpcon = new SetupController();

    Wizard player = stpcon.player;

    @FXML
    protected Button attButton;

    @FXML
    protected Button cancelButton;

    @FXML
    protected Button defButton;

    @FXML
    protected TextFlow enemyDesc;

    @FXML
    protected ImageView enemyImg;

    @FXML
    protected Label enemyName;

    @FXML
    protected VBox enemyVBox;

    @FXML
    protected TextFlow gameText;

    @FXML
    protected HBox menuBottom;

    @FXML
    protected TextFlow playerDesc;

    @FXML
    protected ImageView playerImg;

    @FXML
    protected Label playerName;

    @FXML
    protected VBox playerVBox;

    @FXML
    protected Button popoButton;

    @FXML
    protected TextFlow roundHeader;

    @FXML
    protected BorderPane skeletonDivider;

    @FXML
    protected Button spellsButton;

    @FXML
    protected Button addChoice;

    @FXML
    int onAttButtonClick() {
        return 1;
    }

    @FXML
    int onDefButtonClick() {
        return 2;
    }

    @FXML
    int onSpellButtonClick() {
        return 3;
    }

    @FXML
    int onPopoButtonClick() {
        return 4;
    }

    @FXML
    int onBonusButtonClick() {
        return 5;
    }

    @FXML
    void onCancelButtonClick() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }


    @FXML
    public void initialize(Wizard player, List<Character> enemies){
        addChoice.setVisible(false);
        playerName.setText(player.getName());

        Text playerDescTxt = new Text("Health: " + player.getHealth() + "\nAtt: " + player.getAtt() +
        "\nDef: " + player.getDef() + "\nDex: " + player.getDex() + "\nMana: " + player.getMana()+"\n\n");
        playerDescTxt.setFont(Font.font("Arial", FontPosture.REGULAR, 16));
        playerDesc.getChildren().add(playerDescTxt);

        for (Character enemy : enemies) {
            enemyName.setText(enemy.getName());
        }
        listEnemies(enemies);
    }

    @FXML
    public void listEnemies(List<Character> enemies) {
        enemyDesc.getChildren().clear();
        for (Character enemy : enemies){
            Text enemyDescTxt = new Text("\nHealth: " + enemy.getHealth() + "\n" + enemy.getType()+ "\n");
            enemyDescTxt.setFont(Font.font("Arial", FontPosture.REGULAR, 16));
            enemyDesc.getChildren().add(enemyDescTxt);
        }
    }

    @FXML
    public void checkEnemiesText(List<Character> enemies) {
        gameText.getChildren().clear();
        Text enemyTip = null;
        label:
        for (Character enemy : enemies) {
            switch (enemy.getName()) {
                case "Troll":
                    enemyTip = new Text("\nYou see a boulder above the troll's head, what can you do with it ?\n");
                    break;
                case "Basilisk":
                    enemyTip = new Text("\nIt's a poisonous powerful snake, try to remove his fangs in a way or another !\n");
                    break;
                case "Dementor":
                    enemyTip = new Text("\nThere are too many dementors ! Scare them out with one of your spells ! They are scared of divine creatures !\n");
                    break label;
                case "Peter Pettigrew":
                    enemyTip = new Text("\nYou cannot fight them ! Find a way to get closer to the Portkey as fast as you can !\n");
                    break;
                case "Dolores Umbridge":
                    enemyTip = new Text("\nTry to delay and waste time as much as you can !\n");
                    break;
                case "Death Eater":
                    enemyTip = new Text("\nThere are too many death eaters! Scare them out with one of your spells ! They do not like to suffer !\n");
                    break label;
                case "Lord Voldemort", "Bellatrix Lestrange":
                    enemyTip = new Text("\nHe can use Avada Kedavra ! Consider this possibility and protect yourself !\n");
                    break label;
            }
        }
        enemyTip.setFont(Font.font("Arial", FontPosture.REGULAR, 16));
        gameText.getChildren().add(enemyTip);
    }

    @FXML
    public void isDefending(Character player){
        gameText.getChildren().clear();
        Text defTxt = new Text(player.getName() + " has decided to defend");

        defTxt.setFont(Font.font("Arial", FontPosture.REGULAR, 16));
        gameText.getChildren().add(defTxt);
    }

    @FXML
    public void gryffindorSwordTxt(){
        gameText.getChildren().clear();
        Text griff = new Text("Since you're a gryffindor, you have the sword of Gryffindor against the basilisk !\n" +
                "You deal double damage with your basic attacks !");

        griff.setFont(Font.font("Arial", FontPosture.REGULAR, 16));
        gameText.getChildren().add(griff);
    }

    @FXML
    public void voldemortCoreTxt(double rand){
        gameText.getChildren().clear();
        Text vol = null;
        if (rand <= 0.5){
            vol = new Text("Having the same wand core as Voldemort, you suffered some damage and took 20 health !");
        }
        else if ((0.5 < rand) && (rand <= 0.8)){
            vol = new Text("Having the same wand core as Voldemort, you feel weak and loss 10 def for this battle !");
        }
        else if ((0.8 < rand) && (rand <= 1)){
            vol = new Text("Having the same wand core as Voldemort, you feel slowed by a force and loss 5 dexterity for this battle !");
        }

        vol.setFont(Font.font("Arial", FontPosture.REGULAR, 16));
        gameText.getChildren().add(vol);
    }

    @FXML
    public int presentingTurnTxt(int i, int round, Wizard player, List<Character> enemies, List<String> poss) {
        int n;
        if (i == 1) {
            playerDesc.getChildren().clear();

            Text playerDescTxt = new Text("Health: " + player.getHealth() + "\nMana: " + player.getMana() + "\nAtt: " + player.getAtt()
                    + "\nDef: " + player.getDef() + "\nCorruption: " + player.getCorruptionGauge() + "\nDo not get more than 100 corruption !");

            playerDescTxt.setFont(Font.font("Arial", FontPosture.REGULAR, 16));
            playerDesc.getChildren().add(playerDescTxt);
            i += 1;
            return i;
        } else if (i == 2) {
            roundHeader.getChildren().clear();
            Text roundTxt = new Text("\nRound " + round + " begins.");

            roundTxt.setFont(Font.font("Arial", FontPosture.REGULAR, 16));
            roundHeader.getChildren().add(roundTxt);
            i += 1;
            return i;
        } else if (i == 3) {
            if (player.getHouse().equals(House.SLYTHERIN) && enemies.get(0).getName().equals("Death Eater")) {
                gameText.getChildren().clear();
                Text deathEatersDng = new Text("Since you're a Slytherin, you can ally yourself with the Death eaters !\n");

                deathEatersDng.setFont(Font.font("Arial", FontPosture.REGULAR, 16));
                gameText.getChildren().add(deathEatersDng);

                addChoice.setVisible(true);
                addChoice.setText("Ally with the death eaters");

                Text turn = new Text("It's your turn to attack. What do you want to do?");
                turn.setFont(Font.font("Arial", FontPosture.REGULAR, 16));
                gameText.getChildren().add(turn);
            } else {
                gameText.getChildren().clear();
                Text turn = new Text("It's your turn to attack. What do you want to do?");
                turn.setFont(Font.font("Arial", FontPosture.REGULAR, 16));
                gameText.getChildren().add(turn);
            }
        } return i;
    }
}
