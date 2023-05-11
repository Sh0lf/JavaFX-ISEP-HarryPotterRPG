package com.isep.hpah.controller;

import com.isep.hpah.model.constructors.Potion;
import com.isep.hpah.model.constructors.character.Character;
import com.isep.hpah.model.constructors.character.*;
import com.isep.hpah.model.constructors.spells.AbstractSpell;
import com.isep.hpah.views.GUI.DungeonOutputGUI;
import com.isep.hpah.views.GUI.controller.DungeonCombatController;
import com.isep.hpah.views.console.DungeonOutput;

import java.util.List;
import java.util.Objects;
import java.util.Random;

public class EnemyGame {

    AllSpellsFunction spfnc = new AllSpellsFunction();
    AllPotionsFunction popofnc = new AllPotionsFunction();
    DungeonOutput dngout = new DungeonOutput();
    DungeonCombatController dngoutGUI = new DungeonCombatController();


    public void enemiesTurn(List<Character> enemies, Wizard player){
        for (Character enemy : enemies) {
            enemy.setDefending(false);
            if (enemy.getHealth() > 0) {
                if (enemy instanceof Boss || enemy instanceof Enemy) {
                    commonEnemySys(enemy, player);
                } else if (enemy instanceof Wizard) {
                    wizardEnemySys((Wizard) enemy, player);
                }
            }
        }

        for (Character enemy : enemies) {
            if (enemy.getHealth() <= 0) {
                player.setExp(player.getExp() + enemy.getExp());
                if (Objects.equals(player.getTypeGame(), "console")) {
                    dngout.gainedExp(enemy);
                }
                else if (Objects.equals(player.getTypeGame(), "GUI")) {
                    dngoutGUI.gainedExp(enemy);
                }
            }
        }
    }

    private void commonEnemySys(Character enemy, Wizard player) {
        double rand = Math.random();
        if (rand <= 0.8) {
            enemy.normalAttack(player);
        } else {
            enemy.setDefending(true);
            if (Objects.equals(player.getTypeGame(), "console")) {
                dngout.isDefending(enemy);
            }
            else if (Objects.equals(player.getTypeGame(), "GUI")) {
                dngoutGUI.isDefending(enemy);
            }
        }
    }

    private void wizardEnemySys(Wizard enemy, Wizard player){
        double rand = Math.random();
        if (rand <= 0.5) {
            enemy.normalAttack(player);
        } else if (0.5 < rand && rand <= 0.7){
            enemy.setDefending(true);
            if (Objects.equals(player.getTypeGame(), "console")) {
                dngout.isDefending(enemy);
            }
            else if (Objects.equals(player.getTypeGame(), "GUI")) {
                dngoutGUI.isDefending(enemy);
            }
        } else if ( 0.7 < rand && rand <= 0.9) {
            List<AbstractSpell> enemySpell = enemy.getKnownSpells();
            // initializing random class
            Random random = new Random();
            // loop for generation random number
            int index = 0;
            for (int i = 0; i < enemySpell.size(); i++) {
                index = random.nextInt(enemySpell.size());
            }
            AbstractSpell spell = enemySpell.get(index);
            if (spell.getType().equals("DMG")) {
                spfnc.castDmgSpell(spell, enemy, player);
                spell.setCooldownRem(spell.getCooldown());
                spfnc.manaReduce(spell, player);
            } else if (spell.getType().equals("DEF")) {
                spfnc.castDefSpell(spell, player);
                spell.setCooldownRem(spell.getCooldown());
                spfnc.manaReduce(spell, player);
            }
        } else if (0.9 < rand){
            List<Potion> allPotion = enemy.getPotionsOwned();
            // initializing random class
            Random random = new Random();
            // loop for generation random number
            int index = 0;
            for (int i = 0; i < allPotion.size(); i++) {
                    index = random.nextInt(allPotion.size());
            }
            popofnc.usePotion(enemy, index);
        }
    }
}
