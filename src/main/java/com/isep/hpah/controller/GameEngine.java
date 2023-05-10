package com.isep.hpah.controller;

import com.isep.hpah.model.constructors.House;
import com.isep.hpah.model.constructors.Potion;
import com.isep.hpah.model.constructors.character.Character;
import com.isep.hpah.model.constructors.character.Wizard;
import com.isep.hpah.model.constructors.spells.AbstractSpell;
import com.isep.hpah.views.console.DungeonOutput;
import com.isep.hpah.views.console.SafeScanner;

import java.util.List;

public class GameEngine {
    private final GameLogic gamelgc = new GameLogic();
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
        gamelgc.gryffindorSword(player, enemies);

        // Loop until all enemies are defeated or the player dies
        while (!enemies.isEmpty() && player.getHealth() > 0) {
            beginningTurn(player, enemies);
            // presenting turn and returns choice
            boolean check = false;
            int targetIndex = 0;
            while (!check) {
                int choice = gamelgc.presentingTurn(player, round, sc, enemies, poss);
                gamelgc.reducingCorruption(player);
                if (choice == 1) {
                    // normal attack
                    if (gamelgc.attacking(enemies, player, sc)){
                        check = true;
                    }
                } else if (choice == 2) {
                    // defend, temporary double the def
                    gamelgc.isDefending(player);
                    player.setDefending(true);
                    check = true;
                } else if (choice == 3) {
                    // use spells: check what spell to choose and check what type of spell then do effect
                    int spellIndex = gamelgc.chooseSpell(player, spells, sc);
                    if (spellIndex < spells.size()){
                        AbstractSpell spell = spells.get(spellIndex);
                        if (spell.getType().equals("UTL")) {
                            // DEX spell: most complicated, based on spell UTL + enemy
                            targetIndex = gamelgc.processUtlSpell(player, spell, enemies, sc);
                            if (targetIndex == 100) {
                                check = false;
                            } else {
                                check = true;
                            }
                        } else {
                            gamelgc.basicChoice3(spell, player, enemies);
                        }
                    }
                } else if (choice == 4) {
                    gamelgc.choice4(potions, player);
                } if (player.getHouse().equals(House.SLYTHERIN) && enemies.get(0).getName().equals("Death Eater")){
                    if (choice == 5) {
                        check = gamelgc.allyDeathEater(player, enemies, choice);
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
        gamelgc.corruptionCheck(player);
        //dungeon7: voldemort core if same does something
        gamelgc.checkVoldemortCore(player, enemies);
        //if def spell used etc.
        gamelgc.checkDefBoost(player);
        // Check how to show the enemy
        gamelgc.enemiesDisplay(player, enemies);
    }

    private void enemyTurnAndChecks(List<Character> enemies, List<AbstractSpell> spells, Wizard player, int round, int targetIndex){
        // Enemies' turn
        engame.enemiesTurn(enemies, player);
        // Dungeon2: checking if dead and then remove att boost
        gamelgc.removeGryffindorSword(player, enemies);
        //dungeon5: Umbridge check, win con based on the round number
        gamelgc.checkUmbridgeWinCon(player, enemies, round);
        // checking player exp
        gamelgc.checkLevelUp(player);
        // reduce cooldown to spells
        spfnc.checkCooldown(player, spells, enemies, targetIndex);
    }

    private void endProcessDungeon(Wizard player){
        // Determine the outcome of the fight
        gamelgc.endDungeon(player);
        // Check through list of all the spells and gives spell if level corresponds
        gamelgc.giveNewSpell(player);
        // Gives random chance of dropping potions 20%
        gamelgc.giveNewPotions(player);
        // Remove all potion boosts from this combat
        gamelgc.checkPotionBoost(player);
        // Reduce corruption by half
        gamelgc.corruptionHalf(player);
    }
}
