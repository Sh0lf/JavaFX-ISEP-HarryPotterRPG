package com.isep.hpah.views.console;

import com.isep.hpah.model.constructors.House;
import com.isep.hpah.model.constructors.Potion;
import com.isep.hpah.model.constructors.character.Character;
import com.isep.hpah.model.constructors.character.*;
import com.isep.hpah.model.constructors.spells.AbstractSpell;

import java.util.List;

public class DungeonOutput {

    SimpleOutput out = new SimpleOutput();

    public void listEnemies(List<Character> enemies) {
        for (Character enemy : enemies) {
            out.print("\nEnemy: " + enemy.getName() + "\nHealth: " + enemy.getHealth() + "\n" + enemy.getType()+ "\n");
        }
    }

    public void checkEnemiesText(List<Character> enemies) {
        label:
        for (Character enemy : enemies) {
            switch (enemy.getName()) {
                case "Troll":
                    out.print("\nYou see a boulder above the troll's head, what can you do with it ?\n");
                    break;
                case "Basilisk":
                    out.print("\nIt's a poisonous powerful snake, try to remove his fangs in a way or another !\n");
                    break;
                case "Dementor":
                    out.print("\nThere are too many dementors ! Scare them out with one of your spells ! They are scared of divine creatures !\n");
                    break label;
                case "Peter Pettigrew":
                    out.print("\nYou cannot fight them ! Find a way to get closer to the Portkey as fast as you can !\n");
                    break;
                case "Dolores Umbridge":
                    out.print("\nTry to delay and waste time as much as you can !\n");
                    break;
                case "Death Eater":
                    out.print("\nThere are too many death eaters! Scare them out with one of your spells ! They do not like to suffer !\n");
                    break label;
                case "Lord Voldemort", "Bellatrix Lestrange":
                    out.print("\nHe can use Avada Kedavra ! Consider this possibility and protect yourself !\n");
                    break label;
            }
        }
    }

    public void gryffindorSwordTxt(){
        out.print("Since you're a gryffindor, you have the sword of Gryffindor against the basilisk !\n" +
                "You deal double damage with your basic attacks !");
    }

    public void voldemortCoreTxt(double rand){
        if (rand <= 0.5){
            out.print("Having the same wand core as Voldemort, you suffered some damage and took 20 health !");
        }
        else if ((0.5 < rand) && (rand <= 0.8)){
            out.print("Having the same wand core as Voldemort, you feel weak and loss 10 def for this battle !");
        }
        else if ((0.8 < rand) && (rand <= 1)){
            out.print("Having the same wand core as Voldemort, you feel slowed by a force and loss 5 dexterity for this battle !");
        }
    }

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

    public void isDefending(Character player){
        out.print(player.getName() + " has decided to defend");
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
