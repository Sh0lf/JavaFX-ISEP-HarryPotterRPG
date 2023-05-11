package com.isep.hpah.controller;

import com.isep.hpah.model.constructors.*;
import com.isep.hpah.model.constructors.character.Wizard;
import com.isep.hpah.model.constructors.spells.AbstractSpell;
import com.isep.hpah.views.GUI.DungeonOutputGUI;
import com.isep.hpah.views.GUI.controller.DungeonCombatController;
import com.isep.hpah.views.console.DungeonOutput;
import com.isep.hpah.views.console.SafeScanner;
import com.isep.hpah.model.constructors.character.Character; //Because of suspected ambiguity

import java.util.*;


//for functions - the controller
public class GameLogic {
    private final AllSpellsFunction spfnc = new AllSpellsFunction();
    private final AllPotionsFunction popofnc = new AllPotionsFunction();
    private final Setup stp = new Setup();
    private final SafeScanner sc = new SafeScanner(System.in);
    private final DungeonOutput dngout = new DungeonOutput();
    private final DungeonCombatController dngoutGUI = new DungeonCombatController();

    public void corruptionCheck(Wizard player){
        if (player.getCorruptionGauge() >= 100){
            player.setHealth(0);
            if (Objects.equals(player.getTypeGame(), "console")){
                dngout.corrupted();
                dngout.loss();
            } else if (Objects.equals(player.getTypeGame(), "GUI")){
                dngoutGUI.corrupted();
                dngoutGUI.loss();
            }
        }
    }

    public void checkUmbridgeWinCon(Wizard player, List<Character> enemies, int round){
        if (enemies.get(0).getName().equals("Dolores Umbridge") && round == 10){
            enemies.get(0).setHealth(0);
            if (Objects.equals(player.getTypeGame(), "console")){
                dngout.umbridgeWinCon();
            }
            else if (Objects.equals(player.getTypeGame(), "GUI")){
                dngoutGUI.umbridgeWinCon();
            }
        }
    }

    public void enemiesDisplay(Wizard player, List<Character> enemies){
        //checking what text to print based on enemy
        if (Objects.equals(player.getTypeGame(), "console")){
            dngout.checkEnemiesText(enemies);
            //list all the enemies with their stats
            dngout.listEnemies(enemies);
        } else if (Objects.equals(player.getTypeGame(), "GUI")){
            dngoutGUI.checkEnemiesText(enemies);
            //list all the enemies with their stats
            dngoutGUI.listEnemies(enemies);
        }
    }

    public void isDefending(Wizard player){
        if (Objects.equals(player.getTypeGame(), "console")){
            dngout.isDefending(player);
        }
        else if (Objects.equals(player.getTypeGame(), "GUI")){
            dngoutGUI.isDefending(player);
            // TODO : GUI part if player creation GUI
        }
    }

    public void reducingCorruption(Wizard player){
        if (player.getCorruptionGauge() != 0){
            player.setCorruptionGauge(player.getCorruptionGauge() - 5);
        }
    }

    public boolean basicChoice3(AbstractSpell spell, Wizard player, List<Character> enemies){
        boolean check = false;
        switch (spell.getType()) {
            case "DMG" -> {
                // DMG spell: simple
                if (processDmgSpell(player, spell, enemies, sc)) {
                    check = true;
                } else {
                    check = false;
                }
            }
            case "DEF" -> {
                // DEF spell: simple
                if (processDefSpell(spell, player, enemies)) {
                    check = true;
                } else {
                    check = false;
                }
            }
        }
        return check;
    }

    public boolean choice4(List<Potion> potions, Wizard player){
        boolean check;
        if (potions.isEmpty()){
            if (Objects.equals(player.getTypeGame(), "console")) {
                dngout.potionEmpty();
            } else if (Objects.equals(player.getTypeGame(), "GUI")){
                dngoutGUI.potionEmpty();
            }
            check = false;
        } else {
            //Use popos, pretty simple
            int potionIndex = selectPotion(player);
            if (potionIndex == potions.size()){
                check = false;
            } else {
                popofnc.usePotion(player, potionIndex);
                check = true;
            }
        }
        return check;
    }

    public void gryffindorSword(Wizard player, List<Character> enemies){
        if (player.getHouse().equals(House.GRYFFINDOR) && enemies.get(0).getName().equals("Basilisk")){
            player.setAtt(player.getAtt() * 2);
            if (Objects.equals(player.getTypeGame(), "console")) {
                dngout.gryffindorSwordTxt();
            } else if (Objects.equals(player.getTypeGame(), "GUI")){
                dngoutGUI.gryffindorSwordTxt();
            }
        }
    }

    public void checkVoldemortCore(Wizard player, List<Character> enemies){
        if(enemies.get(0).getName().equals("Lord Voldemort") &&
                player.getWand().getCore().equals(Core.PHOENIX_FEATHER)){
            double rand = Math.random();

            if ((0.2 < rand) && (rand <= 0.5)){
                player.setHealth(player.getHealth() - 20);
                if (Objects.equals(player.getTypeGame(), "console")) {
                    dngout.voldemortCoreTxt(rand);
                } else if (Objects.equals(player.getTypeGame(), "GUI")){
                    dngoutGUI.voldemortCoreTxt(rand);
                }
            }
            else if ((0.5 < rand) && (rand <= 0.8)){
                player.setPotionDefBoost(player.getPotionDefBoost() - 10);
                player.setDef(player.getDef() - 10);
                if (Objects.equals(player.getTypeGame(), "console")) {
                    dngout.voldemortCoreTxt(rand);
                } else if (Objects.equals(player.getTypeGame(), "GUI")){
                    dngoutGUI.voldemortCoreTxt(rand);
                }
            }
            else if ((0.8 < rand) && (rand <= 1)){
                player.setPotionDexBoost(player.getPotionDexBoost() - 5);
                player.setDex(player.getDex() - 5);
                if (Objects.equals(player.getTypeGame(), "console")) {
                    dngout.voldemortCoreTxt(rand);
                } else if (Objects.equals(player.getTypeGame(), "GUI")){
                    dngoutGUI.voldemortCoreTxt(rand);
                }
            }
        }
    }

    public void checkDefBoost(Wizard player){
        if (player.getDefSpell() != 0) {
            player.setDef(player.getDef() - player.getDefSpell());
            player.setDefSpell(0);
        }
    }

    public int presentingTurn(Wizard player, int round, SafeScanner sc, List<Character> enemies, List<String> poss){
        int i = 1;
        if (Objects.equals(player.getTypeGame(), "console")) {
            i = dngout.presentingTurnTxt(i, round, player, enemies, sc, poss);
            player.setDefending(false);
            i = dngout.presentingTurnTxt(i, round, player, enemies, sc, poss);
            // Player's turn
            return dngout.presentingTurnTxt(i, round, player, enemies, sc, poss);
        } else if (Objects.equals(player.getTypeGame(), "GUI")){
            // TODO : GUI part if player creation GUI
            return i;
        }
        return i; // Just to have a return case
    }

    public boolean attacking(List<Character> enemies, Wizard player, SafeScanner sc){
        if (Objects.equals(player.getTypeGame(), "console")) {
            int targetIndex = dngout.chooseTarget(enemies, sc);
            if (targetIndex == enemies.size()){
                return false;
            } else {
                Character target = enemies.get(targetIndex);
                player.normalAttack(target);
                return true;
            }
        } else if (Objects.equals(player.getTypeGame(), "GUI")){
            // TODO : GUI part if player creation GUI
            return true;
        }
        return false;
    }


    public int chooseSpell(Wizard player, List<AbstractSpell> spells, SafeScanner sc) {
        if (Objects.equals(player.getTypeGame(), "console")) {
            return dngout.chooseSpell(spells, sc);
        } else if (Objects.equals(player.getTypeGame(), "GUI")) {
            int spellChoice = 0;
            // TODO : GUI part if player creation GUI
            return spellChoice;
        }
        return 0;
    }

    public boolean processDmgSpell(Wizard player, AbstractSpell spell, List<Character> enemies, SafeScanner sc) {
        if (spell.getCooldownRem() > 0) {
            if (Objects.equals(player.getTypeGame(), "console")) {
                dngout.inCooldown(spell);
                return false;
            }
            else if (Objects.equals(player.getTypeGame(), "GUI")) {
                // TODO : GUI part if player creation GUI
            }
        } else if (player.getMana() < spell.getMana()) {
            if (Objects.equals(player.getTypeGame(), "console")) {
                dngout.noMana(spell);
                return false;
            }
            else if (Objects.equals(player.getTypeGame(), "GUI")) {
                // TODO : GUI part if player creation GUI
            }
        } else {
            if (spell.getName().equals("Sectumsempra")){
                if (enemies.get(0).getName().equals("Death Eater")){
                    if (Objects.equals(player.getTypeGame(), "console")) {
                        dngout.exceptionDeathEater();
                    }
                    else if (Objects.equals(player.getTypeGame(), "GUI")) {
                        // TODO : GUI part if player creation GUI
                    }
                    for (Character enemy : enemies) {
                        enemy.setHealth(0);
                    }
                }
            } else {
                int targetIndex = 0;
                if (Objects.equals(player.getTypeGame(), "console")) {
                    targetIndex = dngout.chooseTarget(enemies, sc);
                }
                else if (Objects.equals(player.getTypeGame(), "GUI")) {
                    // TODO : GUI part if player creation GUI
                }
                if (targetIndex == enemies.size()){
                    return false;
                } else {
                    Character target = enemies.get(targetIndex);
                    spfnc.castDmgSpell(spell, player, target);

                    spell.setCooldownRem(spell.getCooldown());

                    spfnc.manaReduce(spell, player);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean processDefSpell(AbstractSpell spell, Wizard player, List<Character> enemies){
        if (spell.getCooldownRem() > 0) {
            if (Objects.equals(player.getTypeGame(), "console")) {
                dngout.inCooldown(spell);
                return false;
            }
            else if (Objects.equals(player.getTypeGame(), "GUI")) {
                // TODO : GUI part if player creation GUI
            }
        } else if (player.getMana() < spell.getMana()) {
            if (Objects.equals(player.getTypeGame(), "console")) {
                dngout.noMana(spell);
                return false;
            }
            else if (Objects.equals(player.getTypeGame(), "GUI")) {
                // TODO : GUI part if player creation GUI
            }
        } else {
            if (spell.getName().equals("Expecto Patronum")) {
                if (enemies.get(0).getName().equals("Dementor")) {
                    if (Objects.equals(player.getTypeGame(), "console")) {
                        dngout.exceptionDementor();
                    }
                    else if (Objects.equals(player.getTypeGame(), "GUI")) {
                        // TODO : GUI part if player creation GUI
                    }
                    for (Character enemy : enemies) {
                        enemy.setHealth(0);
                    }
                }
            } else {
                spfnc.castDefSpell(spell, player);
                spell.setCooldownRem(spell.getCooldown());
                spfnc.manaReduce(spell, player);
                return true;
            }
        }
        return false;
    }

    public int processUtlSpell(Wizard player, AbstractSpell spell, List<Character> enemies, SafeScanner sc){
        if (spell.getCooldownRem() > 0) {
            if (Objects.equals(player.getTypeGame(), "console")) {
                dngout.inCooldown(spell);
                return 100;
            }
            else if (Objects.equals(player.getTypeGame(), "GUI")) {
                // TODO : GUI part if player creation GUI
            }
        } else if (player.getMana() < spell.getMana()) {
            if (Objects.equals(player.getTypeGame(), "console")) {
                dngout.noMana(spell);
                return 100;
            }
            else if (Objects.equals(player.getTypeGame(), "GUI")) {
                // TODO : GUI part if player creation GUI
            }
        } else {
            int targetIndex = spfnc.checkUtlSpellUsage(player, spell, enemies, sc);
            spell.setCooldownRem(spell.getCooldown());
            spfnc.manaReduce(spell, player);
            return targetIndex;
        }
        return 100;
    }

    public int selectPotion(Wizard player){
        List<Potion> potions = player.getPotionsOwned();
        if (Objects.equals(player.getTypeGame(), "console")) {
            return dngout.selectPotionTxt(potions, sc);
        } else if (Objects.equals(player.getTypeGame(), "GUI")) {
            int spellChoice = 0;
            // TODO : GUI part if player creation GUI
            return spellChoice;
        }
        return 0;
    }

    public boolean allyDeathEater(Wizard player, List<Character> enemies, int choice){
        for (Character enemy : enemies) {
            enemy.setHealth(0);
        }
        player.getKnownSpells().add(stp.deathEaterGroup);

        if (Objects.equals(player.getTypeGame(), "console")) {
            dngout.deathEaterSpell(stp.deathEaterGroup);
        } else if (Objects.equals(player.getTypeGame(), "GUI")) {
            // TODO : GUI part if player creation GUI
        }
        return true;
    }

    public void endDungeon(Wizard player){
        // Determine the outcome of the fight
        if (player.getHealth() <= 0) {
            if (Objects.equals(player.getTypeGame(), "console")) {
                dngout.loss();
            } else if (Objects.equals(player.getTypeGame(), "GUI")) {
                // TODO : GUI part if player creation GUI
            }
            System.exit(0);
        } else {
            if (Objects.equals(player.getTypeGame(), "console")) {
                dngout.victory();
            } else if (Objects.equals(player.getTypeGame(), "GUI")) {
                // TODO : GUI part if player creation GUI
            }
        }
    }

    public void removeGryffindorSword(Wizard player, List<Character> enemies){
        if (enemies.get(0).getName().equals("Basilisk") && enemies.get(0).getHealth() == 0) {
            player.setAtt(player.getAtt() / 2);
            if (Objects.equals(player.getTypeGame(), "console")) {
                dngout.gryffindorSwordEnd();
            } else if (Objects.equals(player.getTypeGame(), "GUI")) {
                // TODO : GUI part if player creation GUI
            }
        }
    }

    public void checkLevelUp(Wizard player) {
        while (player.getExp() >= 50) {
            int i = 0;
            player.setLevel(player.getLevel() + 1);
            player.setExp(player.getExp()- 50);
            if (Objects.equals(player.getTypeGame(), "console")) {
                i = dngout.lvlup(i, player);
            } else if (Objects.equals(player.getTypeGame(), "GUI")) {
                // TODO : GUI part if player creation GUI
            }

            player.setMaxHealth(player.getMaxHealth() + 20);
            player.setHealth(player.getMaxHealth());
            player.setMaxMana(player.getMana() + 20);
            player.setMana(player.getMaxMana());

            player.setAtt(player.getAtt() + 5);
            player.setDef(player.getDef() + 5);
            player.setDex(player.getDex() + 2);

            if (Objects.equals(player.getTypeGame(), "console")) {
                dngout.lvlup(i, player);
            }
            else if (Objects.equals(player.getTypeGame(), "GUI")) {
                // TODO : GUI part if player creation GUI
            }
        }
    }

    public void giveNewSpell(Wizard player) {
        List<AbstractSpell> obtainableSpells = stp.allObtainableSpells();

        for (AbstractSpell spell : obtainableSpells){
            if (player.getLevel() == spell.getLevel()){
                player.getKnownSpells().add(spell);
                if (Objects.equals(player.getTypeGame(), "console")) {
                    dngout.newSpell(spell);
                }
                else if (Objects.equals(player.getTypeGame(), "GUI")) {
                    // TODO : GUI part if player creation GUI
                }
            }
        }
    }

    public void giveNewPotions(Wizard player){
        List<Potion> allPotions = stp.allPotions();
        double rand = Math.random();

        for (Potion potion : allPotions){
            if (rand > 0.20){
                player.getPotionsOwned().add(potion);
                if (Objects.equals(player.getTypeGame(), "console")) {
                    dngout.newPotion(potion);
                }
                else if (Objects.equals(player.getTypeGame(), "GUI")) {
                    // TODO : GUI part if player creation GUI
                }
            }
        }
    }

    public void checkPotionBoost(Wizard player) {
        if (player.getPotionDefBoost() != 0){
            player.setDef(player.getDef() - player.getPotionDefBoost());
            player.setPotionDefBoost(0);
            if (Objects.equals(player.getTypeGame(), "console")) {
                dngout.defPotionBonus(player);
            }
            else if (Objects.equals(player.getTypeGame(), "GUI")) {
                // TODO : GUI part if player creation GUI
            }
        }
        if (player.getPotionDexBoost() != 0){
            player.setDex(player.getDex() - player.getPotionDexBoost());
            player.setPotionDexBoost(0);
            if (Objects.equals(player.getTypeGame(), "console")) {
                dngout.dexPotionBonus(player);
            }
            else if (Objects.equals(player.getTypeGame(), "GUI")) {
                // TODO : GUI part if player creation GUI
            }
        }
    }

    public void corruptionHalf(Wizard player){
        if (player.getCorruptionGauge() != 0) {
            player.setCorruptionGauge(player.getCorruptionGauge()/2);
            if (Objects.equals(player.getTypeGame(), "console")) {
                dngout.corruptionHalfTxt(player);
            }
            else if (Objects.equals(player.getTypeGame(), "GUI")) {
                // TODO : GUI part if player creation GUI
            }
        }
    }
}