package com.isep.hpah.views.console;

import com.isep.hpah.model.constructors.character.Wizard;

public class PotionOutput {

    SimpleOutput out = new SimpleOutput();

    public void hpPopo(Wizard player, double boost){
        out.print(player.getName() + " gained " + (int) boost + " Health !");
    }

    public void defPopo(Wizard player, double boost){
        out.print(player.getName() + " gained " + (int) boost + " Defense for this battle for a total of "
                + player.getPotionDefBoost() + " bonus defense !");
    }

    public void dexPopo(Wizard player, double boost){
        out.print(player.getName() + " gained " + (int) boost + " Dexterity for this battle for a total of "
                + player.getPotionDexBoost() + " bonus dexterity !");
    }

    public void houseBuffPopo(Wizard player){
        out.print("\n Since " + player.getName() + " is from Hufflepuff, the potions are more efficient !");
    }

}
