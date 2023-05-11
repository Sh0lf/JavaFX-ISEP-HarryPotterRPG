package com.isep.hpah.controller;

import com.isep.hpah.model.constructors.character.Wizard;
import com.isep.hpah.views.GUI.DungeonOutputGUI;
import com.isep.hpah.views.GUI.SpellOutputGUI;
import com.isep.hpah.views.console.DungeonOutput;
import com.isep.hpah.views.console.SafeScanner;
import com.isep.hpah.model.constructors.House;
import com.isep.hpah.model.constructors.character.Character;
import com.isep.hpah.model.constructors.spells.AbstractSpell;
import com.isep.hpah.views.console.SpellOutput;

import java.util.List;
import java.util.Objects;

//All directly related spell functions/methods, mostly casting methods and some checking methods
public class AllSpellsFunction {
    SpellOutput spout = new SpellOutput();
    DungeonOutput dngout = new DungeonOutput();
    SpellOutputGUI spoutGUI = new SpellOutputGUI();
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
            if (Objects.equals(player.getTypeGame(), "console")) {
                dngout.missedAtt(player);
                return;
            }
            else if (Objects.equals(player.getTypeGame(), "GUI")){
                dngoutGUI.missedAtt(player);
            }
        }

        if (player.getHouse().equals(House.SLYTHERIN)){
            damage = damage * 1.2;
            if (Objects.equals(player.getTypeGame(), "console")) {
                spout.slytherinBoost(player);
            }
            else if (Objects.equals(player.getTypeGame(), "GUI")){
                spoutGUI.slytherinBoost(player);
            }
        }

        int remainingHealth = enemy.getHealth() - (int) damage;
        enemy.setHealth(remainingHealth);
        player.setCorruptionGauge(player.getCorruptionGauge() + spell.getCorruption());
        if (Objects.equals(player.getTypeGame(), "console")) {
            spout.dmgSpell(player, enemy, spell, (int) damage, remainingHealth);
        }
        else if (Objects.equals(player.getTypeGame(), "GUI")){
            spoutGUI.dmgSpell(player, enemy, spell, (int) damage, remainingHealth);
        }
    }

    // Method for DEF spell, comparison based on a Wizard variable DefSpell.
    public void castDefSpell(AbstractSpell spell, Wizard player){
        double val = spell.getNum();

        if (player.getHouse().equals(House.SLYTHERIN)){
            val = val * 1.2;
            if (Objects.equals(player.getTypeGame(), "console")) {
                spout.slytherinBoost(player);
            }
            else if (Objects.equals(player.getTypeGame(), "GUI")){
                spoutGUI.slytherinBoost(player);
            }
        }
        player.setDefSpell((int) val);
        player.setDef(player.getDefSpell());
        player.setCorruptionGauge(player.getCorruptionGauge() + spell.getCorruption());
        if (Objects.equals(player.getTypeGame(), "console")) {
            spout.defSpell(player, (int) val);
        }
        else if (Objects.equals(player.getTypeGame(), "GUI")){
            spoutGUI.defSpell(player, (int) val);
        }
    }

    //Tricky part:
    //Checking if spell have cooldown in progress (cooldownrem)
    //Checking which spell have cooldown, in this case accio and expelliarmus as they make temporary debuff on dex or def
    //and that is based on 2 unique enemies: Bellatrix and Voldemort. if true reinstate original values
    //after this check: we reduce spell cooldown rem
    public void checkCooldown(Wizard player, List<AbstractSpell> spells, List<Character> enemies, int targetIndex){
        for (AbstractSpell spell : spells) {
            if (spell.getCooldownRem() > 0) {

                boolean speCondition = (enemies.get(targetIndex).getName().equals("Lord Voldemort") &&
                        enemies.get(targetIndex + 1).getName().equals("Bellatrix Lestrange")) ||
                        enemies.get(targetIndex).getName().equals("Bellatrix Lestrange");

                if (spell.getName().equals("Accio") && spell.getCooldownRem() == 2){
                    if (speCondition){
                        enemies.get(targetIndex).setDef(enemies.get(targetIndex).getDef() + spell.getNum());
                        if (Objects.equals(player.getTypeGame(), "console")) {
                            spout.cooldownSpecial1();
                        }
                        else if (Objects.equals(player.getTypeGame(), "GUI")){
                            spoutGUI.cooldownSpecial1();
                        }
                    }
                } else if (spell.getName().equals("Expelliarmus") && spell.getCooldownRem() == 4){
                    if (speCondition){
                        enemies.get(targetIndex).setDex(enemies.get(targetIndex).getDex() + spell.getNum());
                        if (Objects.equals(player.getTypeGame(), "console")) {
                            spout.cooldownSpecial2();
                        }
                        else if (Objects.equals(player.getTypeGame(), "GUI")){
                            spoutGUI.cooldownSpecial2();
                        }
                    }
                }

                spell.setCooldownRem(spell.getCooldownRem() - 1);
            }
        }
    }


    //Switch case for UTL Spell usage based on enemy. Deeply developed (Should be considered exception)
    public int checkUtlSpellUsage(Wizard player, AbstractSpell spell, List<Character> enemies, SafeScanner sc){
        int targetIndex = 0;
        if (Objects.equals(player.getTypeGame(), "console")) {
            targetIndex = dngout.chooseTarget(enemies, sc);
        }
        else if (Objects.equals(player.getTypeGame(), "GUI")){
            targetIndex = dngoutGUI.chooseTarget(enemies, sc);
        }
        Character target = enemies.get(targetIndex);
        switch (target.getName()) {
            case "Troll" -> trollCase(player, target, spell);
            case "Basilisk" -> basiliskCase(player, target, spell);
            case "Dementor" -> dementorCase(player, spell);
            case "Lord Voldemort" -> voldemortCase(player, enemies, target, spell);
            case "Peter Pettigrew" -> pettigrewCase(player, enemies, target, spell);
            case "Bellatrix Lestrange" -> bellatrixCase(player, spell, target);
            default -> {
            }
        }
        return targetIndex;
    }

    private void trollCase(Wizard player, Character target, AbstractSpell spell){
        if (spell.getName().equals("Wingardium Leviosa")) {
            target.setHealth(target.getHealth() - spell.getNum());
            if (Objects.equals(player.getTypeGame(), "console")) {
                spout.wingardiumSpellCase(target);
            }
            else if (Objects.equals(player.getTypeGame(), "GUI")){
                spoutGUI.wingardiumSpellCase(target);
            }
        }
    }

    private void basiliskCase(Wizard player, Character target, AbstractSpell spell){
        if (spell.getName().equals("Wingardium Leviosa")) {
            target.setHealth(target.getHealth() - spell.getNum());
            if (Objects.equals(player.getTypeGame(), "console")) {
                spout.wingardiumSpellCase(target);
            }
            else if (Objects.equals(player.getTypeGame(), "GUI")){
                spoutGUI.wingardiumSpellCase(target);
            }

        } else if (spell.getName().equals("Accio")) {
            target.setHealth(target.getHealth() / 3);
            if (Objects.equals(player.getTypeGame(), "console")) {
                spout.accioSpellCase(target);
            }
            else if (Objects.equals(player.getTypeGame(), "GUI")){
                spoutGUI.accioSpellCase(target);
            }
        }
    }

    private void dementorCase(Wizard player, AbstractSpell spell){
        if (spell.getName().equals("Wingardium Leviosa") || spell.getName().equals("Accio")) {
            if (Objects.equals(player.getTypeGame(), "console")) {
                spout.exceptionDementor();
            }
            else if (Objects.equals(player.getTypeGame(), "GUI")){
                spoutGUI.exceptionDementor();
            }
        }
    }

    private void voldemortCase(Wizard player, List<Character> enemies, Character target, AbstractSpell spell){
        switch (spell.getName()) {
            case "Accio" -> {
                if (enemies.get(1).getName().equals("Peter Pettigrew")) {
                    for (Character enemy : enemies) {
                        enemy.setHealth(0);
                    }
                    if (Objects.equals(player.getTypeGame(), "console")) {
                        spout.accioException();
                    }
                    else if (Objects.equals(player.getTypeGame(), "GUI")){
                        spoutGUI.accioException();
                    }
                } else {
                    target.setDef(target.getDef() - spell.getNum());
                    if (Objects.equals(player.getTypeGame(), "console")) {
                        spout.accioSpellCase(target);
                    }
                    else if (Objects.equals(player.getTypeGame(), "GUI")){
                        spoutGUI.accioSpellCase(target);
                    }
                }
            }
            case "Wingardium Leviosa" -> {
                target.setHealth(target.getHealth() - spell.getNum());
                if (Objects.equals(player.getTypeGame(), "console")) {
                    spout.wingardiumSpellCase(target);
                }
                else if (Objects.equals(player.getTypeGame(), "GUI")){
                    spoutGUI.wingardiumSpellCase(target);
                }
            }
            case "Expelliarmus" -> {
                if (enemies.get(1).getName().equals("Bellatrix Lestrange")) {
                    target.setDex(target.getDex() - spell.getNum());
                    if (Objects.equals(player.getTypeGame(), "console")) {
                        spout.expelliarmusSpellCase(target);
                    }
                    else if (Objects.equals(player.getTypeGame(), "GUI")){
                        spoutGUI.expelliarmusSpellCase(target);
                    }
                }
            }
        }
    }

    private void pettigrewCase(Wizard player, List<Character> enemies, Character target, AbstractSpell spell){
        if (spell.getName().equals("Accio")) {
            for (Character enemy : enemies) {
                enemy.setHealth(0);
            }
            if (Objects.equals(player.getTypeGame(), "console")) {
                spout.accioException();
            }
            else if (Objects.equals(player.getTypeGame(), "GUI")){
                spoutGUI.accioException();
            }
        } else if (spell.getName().equals("Wingardium Leviosa")) {
            target.setHealth(target.getHealth() - spell.getNum());
            if (Objects.equals(player.getTypeGame(), "console")) {
                spout.wingardiumSpellCase(target);
            }
            else if (Objects.equals(player.getTypeGame(), "GUI")){
                spoutGUI.wingardiumSpellCase(target);
            }
        }
    }

    private void bellatrixCase(Wizard player, AbstractSpell spell, Character target){
        switch (spell.getName()) {
            case "Expelliarmus" -> {
                target.setDex(target.getDex() - spell.getNum());
                if (Objects.equals(player.getTypeGame(), "console")) {
                    spout.expelliarmusSpellCase(target);
                }
                else if (Objects.equals(player.getTypeGame(), "GUI")){
                    spoutGUI.expelliarmusSpellCase(target);
                }
            }
            case "Accio" -> {
                target.setDef(target.getDef() - spell.getNum());
                if (Objects.equals(player.getTypeGame(), "console")) {
                    spout.accioSpellCase(target);
                }
                else if (Objects.equals(player.getTypeGame(), "GUI")){
                    spoutGUI.accioSpellCase(target);
                }
            }
            case "Wingardium Leviosa" -> {
                target.setHealth(target.getHealth() - spell.getNum());
                if (Objects.equals(player.getTypeGame(), "console")) {
                    spout.wingardiumSpellCase(target);
                }
                else if (Objects.equals(player.getTypeGame(), "GUI")){
                    spoutGUI.wingardiumSpellCase(target);
                }
            }
        }
    }
}


