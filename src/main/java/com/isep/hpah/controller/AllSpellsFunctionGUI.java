package com.isep.hpah.controller;

import com.isep.hpah.model.constructors.House;
import com.isep.hpah.model.constructors.character.Character;
import com.isep.hpah.model.constructors.character.Wizard;
import com.isep.hpah.model.constructors.spells.AbstractSpell;
import com.isep.hpah.views.GUI.DungeonOutputGUI;
import com.isep.hpah.views.GUI.SpellOutputGUI;
import com.isep.hpah.views.console.SafeScanner;

import java.util.List;

public class AllSpellsFunctionGUI {
    SpellOutputGUI spoutGUI= new SpellOutputGUI();
    DungeonOutputGUI dngoutGUI = new DungeonOutputGUI();
    //self-explanatory
    public void manaReduce(AbstractSpell spell, Wizard player) {
        player.setMana(player.getMana() - spell.getMana());
    }

    // Method to cast a dmg spell, deal dmg
    public void castDmgSpell(AbstractSpell spell, Wizard player, Character enemy) {
        // Cast the attacking spell on the enemy
        double damage = spell.getNum();
        double hitChance = 0.7 + player.getDex();
        double rand = Math.random();
        if (rand > hitChance) {
            dngoutGUI.missedAtt(player);
            return;
        }

        if (player.getHouse().equals(House.SLYTHERIN)){
            damage = damage * 1.2;
            spoutGUI.slytherinBoost(player);
        }

        int remainingHealth = enemy.getHealth() - (int) damage;
        enemy.setHealth(remainingHealth);
        player.setCorruptionGauge(player.getCorruptionGauge() + spell.getCorruption());
        spoutGUI.dmgSpell(player, enemy, spell, (int) damage, remainingHealth);
    }

    // Method for DEF spell, comparison based on a Wizard variable DefSpell.
    public void castDefSpell(AbstractSpell spell, Wizard player){
        double val = spell.getNum();

        if (player.getHouse().equals(House.SLYTHERIN)){
            val = val * 1.2;
            spoutGUI.slytherinBoost(player);
        }
        player.setDefSpell((int) val);
        player.setDef(player.getDefSpell());
        player.setCorruptionGauge(player.getCorruptionGauge() + spell.getCorruption());
        spoutGUI.defSpell(player, (int) val);
    }

    //Tricky part:
    //Checking if spell have cooldown in progress (cooldownrem)
    //Checking which spell have cooldown, in this case accio and expelliarmus as they make temporary debuff on dex or def
    //and that is based on 2 unique enemies: Bellatrix and Voldemort. if true reinstate original values
    //after this check: we reduce spell cooldown rem
    public void checkCooldown(List<AbstractSpell> spells, List<Character> enemies, int targetIndex){
        for (AbstractSpell spell : spells) {
            if (spell.getCooldownRem() > 0) {

                boolean speCondition = (enemies.get(targetIndex).getName().equals("Lord Voldemort") &&
                        enemies.get(targetIndex + 1).getName().equals("Bellatrix Lestrange")) ||
                        enemies.get(targetIndex).getName().equals("Bellatrix Lestrange");

                if (spell.getName().equals("Accio") && spell.getCooldownRem() == 2){
                    if (speCondition){
                        enemies.get(targetIndex).setDef(enemies.get(targetIndex).getDef() + spell.getNum());
                        spoutGUI.cooldownSpecial1();
                    }
                } else if (spell.getName().equals("Expelliarmus") && spell.getCooldownRem() == 4){
                    if (speCondition){
                        enemies.get(targetIndex).setDex(enemies.get(targetIndex).getDex() + spell.getNum());
                        spoutGUI.cooldownSpecial2();
                    }
                }

                spell.setCooldownRem(spell.getCooldownRem() - 1);
            }
        }
    }


    //Switch case for UTL Spell usage based on enemy. Deeply developed (Should be considered exception)
    public int checkUtlSpellUsage(AbstractSpell spell, List<Character> enemies, SafeScanner sc){
        int targetIndex = dngoutGUI.chooseTarget(enemies, sc);
        Character target = enemies.get(targetIndex);
        switch (target.getName()) {
            case "Troll" -> trollCase(target, spell);
            case "Basilisk" -> basiliskCase(target, spell);
            case "Dementor" -> dementorCase(spell);
            case "Lord Voldemort" -> voldemortCase(enemies, target, spell);
            case "Peter Pettigrew" -> pettigrewCase(enemies, target, spell);
            case "Bellatrix Lestrange" -> bellatrixCase(spell, target);
            default -> {
            }
        }
        return targetIndex;
    }

    private void trollCase(Character target, AbstractSpell spell){
        if (spell.getName().equals("Wingardium Leviosa")) {
            target.setHealth(target.getHealth() - spell.getNum());
            spoutGUI.wingardiumSpellCase(target);
        }
    }

    private void basiliskCase(Character target, AbstractSpell spell){
        if (spell.getName().equals("Wingardium Leviosa")) {
            target.setHealth(target.getHealth() - spell.getNum());
            spoutGUI.wingardiumSpellCase(target);

        } else if (spell.getName().equals("Accio")) {
            target.setHealth(target.getHealth() / 3);
            spoutGUI.accioSpellCase(target);
        }
    }

    private void dementorCase(AbstractSpell spell){
        if (spell.getName().equals("Wingardium Leviosa") || spell.getName().equals("Accio")) {
            spoutGUI.exceptionDementor();
        }
    }

    private void voldemortCase(List<Character> enemies, Character target, AbstractSpell spell){
        switch (spell.getName()) {
            case "Accio" -> {
                if (enemies.get(1).getName().equals("Peter Pettigrew")) {
                    for (Character enemy : enemies) {
                        enemy.setHealth(0);
                    }
                    spoutGUI.accioException();
                } else {
                    target.setDef(target.getDef() - spell.getNum());
                    spoutGUI.accioSpellCase(target);
                }
            }
            case "Wingardium Leviosa" -> {
                target.setHealth(target.getHealth() - spell.getNum());
                spoutGUI.wingardiumSpellCase(target);
            }
            case "Expelliarmus" -> {
                if (enemies.get(1).getName().equals("Bellatrix Lestrange")) {
                    target.setDex(target.getDex() - spell.getNum());
                    spoutGUI.expelliarmusSpellCase(target);
                }
            }
        }
    }

    private void pettigrewCase(List<Character> enemies, Character target, AbstractSpell spell){
        if (spell.getName().equals("Accio")) {
            for (Character enemy : enemies) {
                enemy.setHealth(0);
            }
            spoutGUI.accioException();
        } else if (spell.getName().equals("Wingardium Leviosa")) {
            target.setHealth(target.getHealth() - spell.getNum());
            spoutGUI.wingardiumSpellCase(target);
        }
    }

    private void bellatrixCase(AbstractSpell spell, Character target){
        switch (spell.getName()) {
            case "Expelliarmus" -> {
                target.setDex(target.getDex() - spell.getNum());
                spoutGUI.expelliarmusSpellCase(target);
            }
            case "Accio" -> {
                target.setDef(target.getDef() - spell.getNum());
                spoutGUI.accioSpellCase(target);
            }
            case "Wingardium Leviosa" -> {
                target.setHealth(target.getHealth() - spell.getNum());
                spoutGUI.wingardiumSpellCase(target);
            }
        }
    }
}
