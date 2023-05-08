package com.isep.hpah.controller;

import com.isep.hpah.model.constructors.House;
import com.isep.hpah.model.constructors.Potion;
import com.isep.hpah.model.constructors.character.Wizard;
import com.isep.hpah.views.GUI.PotionOutputGUI;

public class AllPotionsFunctionGUI {
    PotionOutputGUI popoutGUI = new PotionOutputGUI();
    public void usePotion(Wizard player, int potionIndex){
        Potion potion = player.getPotionsOwned().get(potionIndex);
        double boost;

        switch (potion.getType()) {
            case "HP" -> {
                boost = checkHouseBuff(player, potion);
                player.setHealth(player.getHealth() + (int) boost);
                popoutGUI.hpPopo(player, boost);
                player.getPotionsOwned().remove(potion);
            }
            case "DEF" -> {
                boost = checkHouseBuff(player, potion);
                player.setDef(player.getDef() + (int) boost);
                player.setPotionDefBoost(player.getPotionDefBoost() + (int) boost);
                popoutGUI.defPopo(player, boost);
                player.getPotionsOwned().remove(potion);
            }
            case "DEX" -> {
                boost = checkHouseBuff(player, potion);
                player.setDex(player.getDex() + (int) boost);
                player.setPotionDexBoost(player.getPotionDexBoost() + (int) boost);
                popoutGUI.dexPopo(player, boost);
                player.getPotionsOwned().remove(potion);
            }
            default -> {
            }
        }
    }

    private double checkHouseBuff(Wizard player, Potion potion){
        double boost = potion.getBoost();
        if (player.getHouse().equals(House.HUFFLEPUFF)) {
            boost = boost * 1.2;
            popoutGUI.houseBuffPopo(player);
        }
        return boost;
    }
}
