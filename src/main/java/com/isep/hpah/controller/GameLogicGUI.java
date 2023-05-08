package com.isep.hpah.controller;

import com.isep.hpah.model.constructors.Core;
import com.isep.hpah.model.constructors.House;
import com.isep.hpah.model.constructors.Potion;
import com.isep.hpah.model.constructors.character.Character;
import com.isep.hpah.model.constructors.character.Wizard;
import com.isep.hpah.model.constructors.spells.AbstractSpell;
import com.isep.hpah.views.console.DungeonOutput;
import com.isep.hpah.views.console.SafeScanner;

import java.util.List;

public class GameLogicGUI {
    private final AllSpellsFunction spfnc = new AllSpellsFunction();
    private final AllPotionsFunction popofnc = new AllPotionsFunction();
    private final Setup stp = new Setup();
    private final SafeScanner sc = new SafeScanner(System.in);
    private final DungeonOutput dngout = new DungeonOutput();

    public void corruptionCheck(Wizard player){
        if (player.getCorruptionGauge() >= 100){
            player.setHealth(0);
            dngout.corrupted();
            dngout.loss();
        }
    }

    public void checkUmbridgeWinCon(List<Character> enemies, int round){
        if (enemies.get(0).getName().equals("Dolores Umbridge") && round == 10){
            enemies.get(0).setHealth(0);
            dngout.umbridgeWinCon();
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
            dngout.potionEmpty();
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
            dngout.gryffindorSwordTxt();
        }
    }

    public void checkVoldemortCore(Wizard player, List<Character> enemies){
        if(enemies.get(0).getName().equals("Lord Voldemort") &&
                player.getWand().getCore().equals(Core.PHOENIX_FEATHER)){
            double rand = Math.random();

            if ((0.2 < rand) && (rand <= 0.5)){
                player.setHealth(player.getHealth() - 20);
                dngout.voldemortCoreTxt(rand);
            }
            else if ((0.5 < rand) && (rand <= 0.8)){
                player.setPotionDefBoost(player.getPotionDefBoost() - 10);
                player.setDef(player.getDef() - 10);
                dngout.voldemortCoreTxt(rand);
            }
            else if ((0.8 < rand) && (rand <= 1)){
                player.setPotionDexBoost(player.getPotionDexBoost() - 5);
                player.setDex(player.getDex() - 5);
                dngout.voldemortCoreTxt(rand);
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
        i = dngout.presentingTurnTxt(i, round, player, enemies, sc, poss);
        player.setDefending(false);
        i = dngout.presentingTurnTxt(i, round, player, enemies, sc, poss);
        // Player's turn
        return dngout.presentingTurnTxt(i, round, player, enemies, sc, poss);
    }

    public boolean attacking(List<Character> enemies, Wizard player, SafeScanner sc){
        int targetIndex = dngout.chooseTarget(enemies, sc);
        if (targetIndex == enemies.size()){
            return false;
        } else {
            Character target = enemies.get(targetIndex);
            player.normalAttack(target);
            return true;
        }
    }

    public int chooseSpell(List<AbstractSpell> spells, SafeScanner sc) {
        return dngout.chooseSpell(spells, sc);
    }

    public boolean processDmgSpell(Wizard player, AbstractSpell spell, List<Character> enemies, SafeScanner sc) {
        if (spell.getCooldownRem() > 0) {
            dngout.inCooldown(spell);
            return false;
        } else if (player.getMana() < spell.getMana()) {
            dngout.noMana(spell);
            return false;
        } else {
            if (spell.getName().equals("Sectumsempra")){
                if (enemies.get(0).getName().equals("Death Eater")){
                    dngout.exceptionDeathEater();
                    for (Character enemy : enemies) {
                        enemy.setHealth(0);
                    }
                }
            } else {
                int targetIndex = dngout.chooseTarget(enemies, sc);
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
            dngout.inCooldown(spell);
            return false;
        } else if (player.getMana() < spell.getMana()) {
            dngout.noMana(spell);
            return false;
        } else {
            if (spell.getName().equals("Expecto Patronum")){
                if (enemies.get(0).getName().equals("Dementor")){
                    dngout.exceptionDementor();
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
        } return false;
    }

    public int processUtlSpell(Wizard player, AbstractSpell spell, List<Character> enemies, SafeScanner sc){
        if (spell.getCooldownRem() > 0) {
            dngout.inCooldown(spell);
            return 100;
        } else if (player.getMana() < spell.getMana()) {
            dngout.noMana(spell);
            return 100;
        } else {

            int targetIndex = spfnc.checkUtlSpellUsage(spell, enemies, sc);

            spell.setCooldownRem(spell.getCooldown());

            spfnc.manaReduce(spell, player);

            return targetIndex;
        }
    }

    public int selectPotion(Wizard player){
        List<Potion> potions = player.getPotionsOwned();
        return dngout.selectPotionTxt(potions, sc);
    }

    public boolean allyDeathEater(Wizard player, List<Character> enemies, int choice){
        for (Character enemy : enemies) {
            enemy.setHealth(0);
        }
        player.getKnownSpells().add(stp.deathEaterGroup);

        dngout.deathEaterSpell(stp.deathEaterGroup);

        return true;
    }

    public void endDungeon(Wizard player){
        // Determine the outcome of the fight
        if (player.getHealth() <= 0) {
            dngout.loss();
            System.exit(0);
        } else {
            dngout.victory();
        }
    }

    public void removeGryffindorSword(Wizard player, List<Character> enemies){
        if (enemies.get(0).getName().equals("Basilisk") && enemies.get(0).getHealth() == 0) {
            player.setAtt(player.getAtt() / 2);
            dngout.gryffindorSwordEnd();
        }
    }

    public void checkLevelUp(Wizard player) {
        while (player.getExp() >= 50) {
            int i = 0;
            player.setLevel(player.getLevel() + 1);
            player.setExp(player.getExp()- 50);
            i = dngout.lvlup(i, player);

            player.setMaxHealth(player.getMaxHealth() + 20);
            player.setHealth(player.getMaxHealth());
            player.setMaxMana(player.getMana() + 20);
            player.setMana(player.getMaxMana());

            player.setAtt(player.getAtt() + 5);
            player.setDef(player.getDef() + 5);
            player.setDex(player.getDex() + 2);

            i = dngout.lvlup(i, player);
        }
    }

    public void giveNewSpell(Wizard player) {
        List<AbstractSpell> obtainableSpells = stp.allObtainableSpells();

        for (AbstractSpell spell : obtainableSpells){
            if (player.getLevel() == spell.getLevel()){
                player.getKnownSpells().add(spell);
                dngout.newSpell(spell);
            }
        }
    }

    public void giveNewPotions(Wizard player){
        List<Potion> allPotions = stp.allPotions();
        double rand = Math.random();

        for (Potion potion : allPotions){
            if (rand > 0.20){
                player.getPotionsOwned().add(potion);
                dngout.newPotion(potion);
            }
        }
    }

    public void checkPotionBoost(Wizard player) {
        if (player.getPotionDefBoost() != 0){
            player.setDef(player.getDef() - player.getPotionDefBoost());
            player.setPotionDefBoost(0);
            dngout.defPotionBonus(player);
        }
        if (player.getPotionDexBoost() != 0){
            player.setDex(player.getDex() - player.getPotionDexBoost());
            player.setPotionDexBoost(0);
            dngout.dexPotionBonus(player);
        }
    }

    public void corruptionHalf(Wizard player){
        if (player.getCorruptionGauge() != 0) {
            player.setCorruptionGauge(player.getCorruptionGauge()/2);
            dngout.corruptionHalfTxt(player);
        }
    }

}
