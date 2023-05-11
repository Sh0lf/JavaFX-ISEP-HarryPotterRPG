package com.isep.hpah.views.GUI;

import com.isep.hpah.model.constructors.House;
import com.isep.hpah.model.constructors.Potion;
import com.isep.hpah.model.constructors.character.Character;
import com.isep.hpah.model.constructors.character.Wizard;
import com.isep.hpah.model.constructors.spells.AbstractSpell;
import com.isep.hpah.views.GUI.controller.DungeonCombatController;
import com.isep.hpah.views.console.SafeScanner;
import com.isep.hpah.views.console.SimpleOutput;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

import java.util.List;

public class DungeonOutputGUI {

    SimpleOutput out = new SimpleOutput();
    DungeonCombatController dngscene = new DungeonCombatController();


    public int presentingTurnTxt(int i, int round, Wizard player, List<Character> enemies, SafeScanner sc, List<String> poss) {
        int n;
        if (i == 1) {
            out.printHeading("Health: " + player.getHealth() + "\nMana: " + player.getMana() + "\nAtt: " + player.getAtt()
                    + "\nDef: " + player.getDef() + "\nCorruption: " + player.getCorruptionGauge() + "\nDo not get more than 100 corruption !");
            i += 1;
            return i;
        } else if (i == 2) {
            out.print("\nRound " + round + " begins.");
            i += 1;
            return i;
        } else if (i == 3) {
            boolean verifInput = false;
            if (player.getHouse().equals(House.SLYTHERIN) && enemies.get(0).getName().equals("Death Eater")) {
                out.print("\nSince you're a Slytherin, you can ally yourself with the Death eaters !\n");
                while (!verifInput) {
                    try {
                        out.print("It's your turn to attack. What do you want to do?");
                        for (n = 0; n < poss.size(); n++) {
                            out.print(n+1 + ". " + poss.get(n));
                        }
                        out.print("5. Ally with the Death Eaters");
                        i = sc.getInt();
                        if (i > poss.size()+1) {
                            out.print("Number out of index and does not exist ! retry");
                        } else {
                            verifInput = true;
                        }
                    } catch (Exception e) {
                        out.print("Please write valid content");
                        verifInput = false;
                    }
                }
            } else {
                while (!verifInput) {
                    try {
                        out.print("It's your turn to attack. What do you want to do?");
                        for (n = 0; n < poss.size(); n++) {
                            out.print(n+1 + ". " + poss.get(n));
                        }
                        i = sc.getInt();
                        if (i > poss.size()) {
                            out.print("Number out of index and does not exist ! retry");
                        } else {
                            verifInput = true;
                        }
                    } catch (Exception e) {
                        out.print("Please write valid content");
                        verifInput = false;
                    }
                }
            }
        } return i;
    }

    public int chooseTarget(List<Character> enemies, SafeScanner sc){
        int targetIndex = 0;
        boolean verifInput = false;
        while (!verifInput) {
            try {
                out.print("Which enemy do you want to attack?");
                for (int i = 0; i < enemies.size(); i++) {
                    out.print((i + 1) + ". " + enemies.get(i).getName() + " : " +
                            enemies.get(i).getHealth() + " health");
                    if (i+1 == enemies.size()){
                        out.print((i+2)+ ". Back");
                    }
                }
                targetIndex = sc.getInt() - 1;
                if (targetIndex > enemies.size()){
                    out.print("Number out of index and does not exist ! retry");
                } else {
                    verifInput = true;
                }
            } catch (Exception e) {
                out.print("Please write valid content");
                verifInput = false;
            }
        } return targetIndex;
    }

    public int chooseSpell(List<AbstractSpell> spells, SafeScanner sc){
        int spellIndex = 0;
        boolean verifInput = false;
        while (!verifInput) {
            try {
                out.print("Select a spell to cast:");
                for (int i = 0; i < spells.size(); i++) {
                    out.print((i + 1) + ". " + spells.get(i).getName() + " (Type: " + spells.get(i).getType()
                            + ", Mana Cost: " + spells.get(i).getMana() + ", Cooldown: " + spells.get(i).getCooldown()
                            + ", Cooldown remaining: " + spells.get(i).getCooldownRem());
                    if (i+1 == spells.size()){
                        out.print((i+2)+ ". Back");
                    }
                }
                spellIndex = sc.getInt() - 1;
                if (spellIndex > spells.size()) {
                    out.print("Number out of index and does not exist ! retry");
                } else {
                    verifInput = true;
                }
            } catch (Exception e) {
                out.print("Please write valid content");
                verifInput = false;
            }
        } return spellIndex;
    }

    public void inCooldown(AbstractSpell spell){
        out.print("Spell is on cooldown for " + spell.getCooldownRem() + " more rounds.");
    }

    public void noMana(AbstractSpell spell){
        out.print("Not enough mana to cast " + spell.getName() + ".");
    }

    public void exceptionDeathEater(){
        out.print("You mercilessly killed one of the Death Eater ! They all got scared and they all left !");
    }

    public void exceptionDementor(){
        out.print("They've seen what divine beast you're capable of summoning, they got scared and they all left !");
    }

    public int selectPotionTxt(List<Potion> potions, SafeScanner sc){
        int potionIndex = 0;
        boolean verifInput = false;
        while (!verifInput) {
            try {
                out.print("Select a potion to use:");
                for (int i = 0; i < potions.size(); i++) {
                    out.print((i + 1) + ". " + potions.get(i).getName() + ", boost: "
                            + potions.get(i).getBoost() +", type: " + potions.get(i).getType());
                    if (i+1 == potions.size()){
                        out.print((i+2)+". Back");
                    }
                }
                potionIndex = sc.getInt() - 1;
                if (potionIndex > potions.size()){
                    out.print("Number out of index and does not exist ! retry");
                } else {
                    verifInput = true;
                }
            } catch (Exception e) {
                out.print("Please write valid content");
                verifInput = false;
            }
        } return potionIndex;
    }

    public void deathEaterSpell(AbstractSpell deathEaterGroup){
        out.print("You've decided to ally with the death eaters !\nAnd you obtained a new spell:\n" +
                deathEaterGroup.getName() + ", type: " + deathEaterGroup.getType() + ", dmg: "
                + deathEaterGroup.getNum() + ", corruption: " + deathEaterGroup.getCorruption());
    }

    public void corrupted() { out.print("\n You've casted too many forbidden spells and you're now consumed by the darkness"); }

    public void loss(){
        out.print("\nYou have been defeated.");
        out.pressEnterToContinue();
    }

    public void victory(){
        out.print("\nYou are victorious!");
    }

    public void gryffindorSwordEnd(){
        out.print("You've defeated the basilisk, and can no longer use the gryffindor sword. " +
                "You attack normally.");
    }

    public int lvlup(int i, Wizard player){
        if (i == 0){
            out.printHeading("\nCongratulations! You have leveled up to level " + player.getLevel() + ".");
            i+=1;
        }
        else if (i == 1){
            out.printHeading("Your new stats: \nAtt: " + player.getAtt() + "\nDef: " +
                    player.getDef() + "\nDex: " + player.getDex());
        }
        return i;
    }

    public void potionEmpty(){
        out.print("You do not have any potions !");
    }

    public void newSpell(AbstractSpell spell){
        out.printHeading("\nYou know a new spell: \n" + spell.getName() + ", " + spell.getDesc() +
                "\nType:" + spell.getType() + ", Mana: " + spell.getMana() + " and Cooldown: " + spell.getCooldown());
    }

    public void newPotion(Potion potion){
        out.printHeading("\nYou own a new potion: \n" + potion.getName() + ", " + potion.getDesc() +
                ", gives: " + potion.getBoost() + potion.getType());
    }

    public void defPotionBonus(Wizard player){
        out.print("\nYour defense went back to normal: it is now" + player.getDef());
    }

    public void dexPotionBonus(Wizard player){
        out.print("\nYour dexterity went back to normal: it is now" + player.getDex());
    }

    public void umbridgeWinCon(){
        out.print("You survived long enough against Umbridge and you succeeded in tempoying her long enough to win !");
    }
    public void gainedExp(Character enemy){
        out.print("You gained " + enemy.getExp() + " experience points.");
    }

    public void corruptionHalfTxt(Wizard player){
        out.print("You're corruption is reduced to " + player.getCorruptionGauge());
    }

    //Common fighting text here
    public void missedAtt(Character cast){
        out.print(cast.getName() + " missed the attack !");
    }
}
