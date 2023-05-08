package com.isep.hpah.controller;

import com.isep.hpah.model.constructors.House;
import com.isep.hpah.model.constructors.Potion;
import com.isep.hpah.model.constructors.character.Character;
import com.isep.hpah.model.constructors.character.Wizard;
import com.isep.hpah.model.constructors.spells.AbstractSpell;
import com.isep.hpah.views.console.DungeonOutput;
import com.isep.hpah.views.console.SafeScanner;

import java.util.List;

public class GameEngineGUI {
    private final GameLogicGUI gamelcgGUI = new GameLogicGUI();
    private final AllSpellsFunction spfnc = new AllSpellsFunction();
    private final EnemyGame engame = new EnemyGame();
    private final Setup stp = new Setup();
    private final DungeonOutput dngout = new DungeonOutput();
    private final SafeScanner sc = new SafeScanner(System.in);

    public void dungeonCombat(List<Character> enemies, Wizard player) {
        int round = 1;
        List<AbstractSpell> spells = player.getKnownSpells();
        List<Potion> potions = player.getPotionsOwned();
        List<String> poss = stp.poss();

        //If basilisk dungeon2 give gryffindor sword that doubles att
        gamelcgGUI.gryffindorSword(player, enemies);

        // Loop until all enemies are defeated or the player dies
        while (!enemies.isEmpty() && player.getHealth() > 0) {
            beginningTurn(player, enemies);
            // presenting turn and returns choice
            boolean check = false;
            int targetIndex = 0;
            while (!check) {
                int choice = gamelcgGUI.presentingTurn(player, round, sc, enemies, poss);
                gamelcgGUI.reducingCorruption(player);
                if (choice == 1) {
                    // normal attack
                    if (gamelcgGUI.attacking(enemies, player, sc)){
                        check = true;
                    }
                } else if (choice == 2) {
                    // defend, temporary double the def
                    dngout.isDefending(player);
                    player.setDefending(true);
                    check = true;
                } else if (choice == 3) {
                    // use spells: check what spell to choose and check what type of spell then do effect
                    int spellIndex = gamelcgGUI.chooseSpell(spells, sc);
                    if (spellIndex < spells.size()){
                        AbstractSpell spell = spells.get(spellIndex);
                        if (spell.getType().equals("UTL")) {
                            // DEX spell: most complicated, based on spell UTL + enemy
                            targetIndex = gamelcgGUI.processUtlSpell(player, spell, enemies, sc);
                            if (targetIndex == 100) {
                                check = false;
                            } else {
                                check = true;
                            }
                        } else {
                            gamelcgGUI.basicChoice3(spell, player, enemies);
                        }
                    }
                } else if (choice == 4) {
                    gamelcgGUI.choice4(potions, player);
                } if (player.getHouse().equals(House.SLYTHERIN) && enemies.get(0).getName().equals("Death Eater")){
                    if (choice == 5) {
                        check = gamelcgGUI.allyDeathEater(player, enemies, choice);
                    }
                }
            }

            enemyTurnAndChecks(enemies, spells, player, round, targetIndex);

            round++;

            // Remove defeated enemies
            enemies.removeIf(enemy -> enemy.getHealth() <= 0);
        }
        endProcessDungeon(player);
    }

    private void beginningTurn(Wizard player, List<Character> enemies){
        //Corruption check
        gamelcgGUI.corruptionCheck(player);
        //dungeon7: voldemort core if same does something
        gamelcgGUI.checkVoldemortCore(player, enemies);
        //if def spell used etc.
        gamelcgGUI.checkDefBoost(player);
        //checking what text to print based on enemy
        dngout.checkEnemiesText(enemies);
        //list all the enemies with their stats
        dngout.listEnemies(enemies);
    }

    private void enemyTurnAndChecks(List<Character> enemies, List<AbstractSpell> spells, Wizard player, int round, int targetIndex){
        // Enemies' turn
        engame.enemiesTurn(enemies, player);
        // Dungeon2: checking if dead and then remove att boost
        gamelcgGUI.removeGryffindorSword(player, enemies);
        //dungeon5: Umbridge check, win con based on the round number
        gamelcgGUI.checkUmbridgeWinCon(enemies, round);
        // checking player exp
        gamelcgGUI.checkLevelUp(player);
        // reduce cooldown to spells
        spfnc.checkCooldown(spells, enemies, targetIndex);
    }

    private void endProcessDungeon(Wizard player){
        // Determine the outcome of the fight
        gamelcgGUI.endDungeon(player);
        // Check through list of all the spells and gives spell if level corresponds
        gamelcgGUI.giveNewSpell(player);
        // Gives random chance of dropping potions 20%
        gamelcgGUI.giveNewPotions(player);
        // Remove all potion boosts from this combat
        gamelcgGUI.checkPotionBoost(player);
        // Reduce corruption by half
        gamelcgGUI.corruptionHalf(player);
    }
}
